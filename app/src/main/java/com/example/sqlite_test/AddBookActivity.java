package com.example.sqlite_test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_test.Models.libro;
import com.example.sqlite_test.database.DbHelper;

import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextFechaPublicacion;
    private EditText editTextTitulo;
    private Spinner spinnerTipo;
    private EditText editTextAutor;
    private Spinner spinnerEstadoLectura;
    private Button buttonGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextAutor = findViewById(R.id.editTextAutor);
        editTextFechaPublicacion = findViewById(R.id.editTextFechaPublicacion);

        // Configurar Spinner de Tipo
        spinnerTipo = findViewById(R.id.spinnerTipo);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(this,
                R.array.tipos_libro, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterTipo);

        // Configurar Spinner de Estado de Lectura
        spinnerEstadoLectura = findViewById(R.id.spinnerEstadoLectura);
        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this,
                R.array.estados_lectura, android.R.layout.simple_spinner_item);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstadoLectura.setAdapter(adapterEstado);

        // Configurar DatePickerDialog para la fecha de publicación
        editTextFechaPublicacion = findViewById(R.id.editTextFechaPublicacion);
        editTextFechaPublicacion.setOnClickListener(v -> showDatePickerDialog());

        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonGuardar.setOnClickListener(v -> guardarLibro());
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    // El mes es 0-indexado, por eso se suma 1
                    String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editTextFechaPublicacion.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void guardarLibro() {
        String titulo = editTextTitulo.getText().toString();
        String autor = editTextAutor.getText().toString();
        String fechaPublicacion = editTextFechaPublicacion.getText().toString();
        String tipo = spinnerTipo.getSelectedItem().toString();

        if(titulo.isEmpty()){
            editTextTitulo.setError("El título es requerido");
            editTextTitulo.requestFocus();
            return;
        }

        if(autor.isEmpty()){
            editTextAutor.setError("El autor es requerido");
            editTextAutor.requestFocus();
            return;
        }

        if(fechaPublicacion.isEmpty()){
            editTextFechaPublicacion.setError("La fecha de publicación es requerida");
            editTextFechaPublicacion.requestFocus();
            return;
        }

        int posicionTipo = spinnerTipo.getSelectedItemPosition();
        int posicionEstado = spinnerEstadoLectura.getSelectedItemPosition();

        int idTipo = posicionTipo + 1;
        int idEstado = posicionEstado + 1;

        libro newlibro = new libro();
        newlibro.setTitulo(titulo);
        newlibro.setAutor(autor);
        newlibro.setEstado(idEstado);
        newlibro.setIdTipo(idTipo);
        newlibro.setFechaPublicacion(fechaPublicacion);

        DbHelper dbHelper = new DbHelper(this);
        long newRowId = dbHelper.insertLibro(newlibro);

        if(newRowId == -1){
            Toast.makeText(this, "Error al guardar el libro", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Libro guardado con éxito", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AddBookActivity.this, BookListActivity.class);
            startActivity(intent);
            finish();

        }
    }
}
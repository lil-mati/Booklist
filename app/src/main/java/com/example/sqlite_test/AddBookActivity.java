package com.example.sqlite_test;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddBookActivity extends AppCompatActivity {

    private EditText editTextFechaPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Configurar Spinner de Tipo
        Spinner spinnerTipo = findViewById(R.id.spinnerTipo);
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(this,
                R.array.tipos_libro, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterTipo);

        // Configurar Spinner de Estado de Lectura
        Spinner spinnerEstadoLectura = findViewById(R.id.spinnerEstadoLectura);
        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this,
                R.array.estados_lectura, android.R.layout.simple_spinner_item);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstadoLectura.setAdapter(adapterEstado);

        // Configurar DatePickerDialog para la fecha de publicación
        editTextFechaPublicacion = findViewById(R.id.editTextFechaPublicacion);
        editTextFechaPublicacion.setOnClickListener(v -> showDatePickerDialog());

        // Lógica para el botón Guardar (se implementará más adelante)
        findViewById(R.id.buttonGuardar).setOnClickListener(v -> {
            // Aquí irá la lógica para guardar el libro en la base de datos
        });
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
}
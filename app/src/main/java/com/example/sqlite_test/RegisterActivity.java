package com.example.sqlite_test;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_test.Models.usuario;
import com.example.sqlite_test.database.DbHelper;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextUsuario;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button registerButton = findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(v -> {
            // Obtener los datos del formulario
            String nombre = editTextNombre.getText().toString();
            String apellido = editTextApellido.getText().toString();
            String usuario = editTextUsuario.getText().toString();
            String password = editTextPassword.getText().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            usuario nuevoUsuario = new usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setUsuario(usuario);
            nuevoUsuario.setContraseña(password);

            DbHelper dbHelper = new DbHelper(this);
            long newRowId = dbHelper.insertUsuario(nuevoUsuario);

            if (newRowId == -1) {
                Toast.makeText(RegisterActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
            finish(); // Cierra esta actividad y vuelve a LoginActivity
        });
    }
}

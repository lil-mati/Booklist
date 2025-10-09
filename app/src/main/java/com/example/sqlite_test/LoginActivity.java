package com.example.sqlite_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_test.database.DbHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        buttonLogin.setOnClickListener(v -> {
            // Lógica de inicio de sesión
            String usuario = editTextUsername.getText().toString();
            String contraseña = editTextPassword.getText().toString();

            if(usuario.isEmpty() || contraseña.isEmpty()){
                Toast.makeText(this, "Por favor, ingrese un usuario y contraseña", Toast.LENGTH_SHORT).show();
            }

            DbHelper dbHelper = new DbHelper(this);
            boolean acceso = dbHelper.validarUsuario(usuario, contraseña);
            dbHelper.close();

            if (acceso) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                editTextPassword.setText("");
                editTextUsername.requestFocus();
            }
        });

        buttonRegister.setOnClickListener(v -> {
            // Lógica de registro
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

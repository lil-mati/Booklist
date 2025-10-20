package com.example.sqlite_test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
            String usuario = editTextUsername.getText().toString();
            String contraseña = editTextPassword.getText().toString();

            if(usuario.isEmpty() || contraseña.isEmpty()){
                Toast.makeText(this, "Por favor, ingrese un usuario y contraseña", Toast.LENGTH_SHORT).show();
                return; 
            }

            DbHelper dbHelper = new DbHelper(this);
            long userId = dbHelper.validarUsuario(usuario, contraseña);
            dbHelper.close();

            if (userId != -1) {
                // Guardar el ID de usuario en SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong("user_id", userId);
                editor.apply();

                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finaliza LoginActivity para que el usuario no pueda volver con el botón de atrás
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                editTextPassword.setText("");
                editTextUsername.requestFocus();
            }
        });

        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

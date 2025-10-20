package com.example.sqlite_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_test.Models.libro;
import com.example.sqlite_test.database.DbHelper;
import com.example.sqlite_test.database.contracts.collectionContract;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        ListView listViewBooks = findViewById(R.id.listViewBooks);
        Button backButton = findViewById(R.id.buttonBack);

        backButton.setOnClickListener(v -> finish());

        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        long userId = sharedPref.getLong("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Error: sesión no iniciada.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        DbHelper dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.getLibros(userId);

        ArrayList<libro> listaLibros = new ArrayList<>();

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    try {
                        libro libro = new libro();
                        libro.setId(cursor.getInt(cursor.getColumnIndexOrThrow(collectionContract.libroEntry.COLUMN_ID)));
                        libro.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(collectionContract.libroEntry.COLUMN_TITULO)));
                        libro.setAutor(cursor.getString(cursor.getColumnIndexOrThrow(collectionContract.libroEntry.COLUMN_AUTOR)));
                        libro.setEstado(cursor.getInt(cursor.getColumnIndexOrThrow(collectionContract.libroEntry.COLUMN_ESTADO)));
                        libro.setFechaPublicacion(cursor.getString(cursor.getColumnIndexOrThrow(collectionContract.libroEntry.COLUMN_FECHA_PUBLICACION)));
                        libro.setIdTipo(cursor.getInt(cursor.getColumnIndexOrThrow(collectionContract.libroEntry.COLUMN_ID_TIPO)));
                        libro.setComment(cursor.getString(cursor.getColumnIndexOrThrow(collectionContract.libroEntry.COLUMN_COMENTARIO)));


                        listaLibros.add(libro);
                        Log.d("DEBUG", "Libro añadido: " + libro.getTitulo());

                    } catch (Exception e) {
                        Log.e("DEBUG", "Error al leer libro: " + e.getMessage());
                    }
                } while (cursor.moveToNext());
                cursor.close();
            } else {
                Toast.makeText(this, "No tienes libros en tu lista", Toast.LENGTH_SHORT).show();
            }

            BookAdapter adapter = new BookAdapter(this, listaLibros);

            listViewBooks.setAdapter(adapter);
    }
}
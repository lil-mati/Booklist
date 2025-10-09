package com.example.sqlite_test;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

        // Find the ListView
        ListView listViewBooks = findViewById(R.id.listViewBooks);

        // Create an instance of the DbHelper
        DbHelper dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.getLibros();

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
                Toast.makeText(this, "No hay libros en la base de datos", Toast.LENGTH_SHORT).show();
            }


            // Create the adapter to convert the array to views
            BookAdapter adapter = new BookAdapter(this, listaLibros);

            // Attach the adapter to a ListView
            listViewBooks.setAdapter(adapter);


    }
}
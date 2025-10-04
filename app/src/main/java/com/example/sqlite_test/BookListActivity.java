package com.example.sqlite_test;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Find the ListView
        ListView listViewBooks = findViewById(R.id.listViewBooks);

        // Create dummy data
        ArrayList<Book> arrayOfBooks = new ArrayList<>();
        arrayOfBooks.add(new Book(1, "El Señor de los Anillos", "J.R.R. Tolkien"));
        arrayOfBooks.add(new Book(2, "Cien Años de Soledad", "Gabriel García Márquez"));

        // Create the adapter to convert the array to views
        BookAdapter adapter = new BookAdapter(this, arrayOfBooks);

        // Attach the adapter to a ListView
        listViewBooks.setAdapter(adapter);
    }
}
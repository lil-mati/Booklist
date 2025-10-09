package com.example.sqlite_test;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.sqlite_test.Models.libro;
import com.example.sqlite_test.database.DbHelper;

import java.util.List;

public class BookAdapter extends ArrayAdapter<libro> {

    public BookAdapter(@NonNull Context context, @NonNull List<libro> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        libro libroActual = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);
        }

        // Lookup view for data population
        TextView tvTitle = convertView.findViewById(R.id.textViewTitle);
        TextView tvAuthor = convertView.findViewById(R.id.textViewAuthor);
        TextView tvComment = convertView.findViewById(R.id.textViewComment);
        Button btnEdit = convertView.findViewById(R.id.buttonEdit);
        Button btnDelete = convertView.findViewById(R.id.buttonDelete);

        // Populate the data into the template view using the data object
        assert libroActual != null;
        tvTitle.setText(libroActual.getTitulo());
        tvAuthor.setText(libroActual.getAutor());

        // Show or hide the comment
        if (libroActual.getComment() != null && !libroActual.getComment().isEmpty()) {
            tvComment.setText("Comentario: " + libroActual.getComment());
            tvComment.setVisibility(View.VISIBLE);
        } else {
            tvComment.setVisibility(View.GONE);
        }

        // Cache the row position in the button's tag
        btnEdit.setTag(position);
        btnDelete.setTag(position);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic for editing (adding a comment)
                showEditDialog(libroActual);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libro delLibro = getItem(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmar eliminación")
                        .setMessage("¿Eliminar \"" + delLibro.getTitulo() + "\"?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                eliminarLibro(delLibro.getId(), position);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }

        });

        return convertView;
    }

    private void showEditDialog(final libro book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Añadir Comentario");

        // Set up the input
        final EditText input = new EditText(getContext());
        input.setHint("Escribe tu comentario aquí");
        input.setText(book.getComment()); // Pre-fill with existing comment
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comment = input.getText().toString();
                
                // --- INICIO DE LA LÓGICA DE GUARDADO EN DB ---
                DbHelper dbHelper = new DbHelper(getContext());
                int rowsAffected = dbHelper.actualizarComentarioLibro(book.getId(), comment);
                dbHelper.close();
                // --- FIN DE LA LÓGICA DE GUARDADO EN DB ---

                if (rowsAffected > 0) {
                    // Actualiza el objeto en la lista y notifica al adaptador
                    book.setComment(comment);
                    notifyDataSetChanged(); // Refresh the list to show the new comment
                    Toast.makeText(getContext(), "Comentario guardado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al guardar el comentario", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void eliminarLibro(long id, int position) {
        DbHelper dbHelper = new DbHelper(getContext());
        boolean eliminado = dbHelper.eliminarLibro(id);

        if (eliminado) {
            remove(getItem(position));
            notifyDataSetChanged();
            Toast.makeText(getContext(), "Libro eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
        dbHelper.close();
    }
}

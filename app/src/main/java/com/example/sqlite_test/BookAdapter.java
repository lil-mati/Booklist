package com.example.sqlite_test;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
        TextView tvStatus = convertView.findViewById(R.id.textViewStatus);
        Button btnEdit = convertView.findViewById(R.id.buttonEdit);
        Button btnDelete = convertView.findViewById(R.id.buttonDelete);

        // Populate the data into the template view using the data object
        assert libroActual != null;
        tvTitle.setText(libroActual.getTitulo());
        tvAuthor.setText(libroActual.getAutor());

        // Set status text
        tvStatus.setText("Estado: " + getStatusString(libroActual.getEstado()));

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

    private String getStatusString(int statusId) {
        switch (statusId) {
            case 1:
                return "Pendiente";
            case 2:
                return "Leyendo";
            case 3:
                return "Leido";
            default:
                return "Desconocido";
        }
    }

    private void showEditDialog(final libro book) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Editar Libro");

        // Inflate the custom layout
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edit_book, null);
        builder.setView(dialogView);

        final EditText inputComment = dialogView.findViewById(R.id.editTextComment);
        final Spinner spinnerStatus = dialogView.findViewById(R.id.spinnerStatus);

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.estados_lectura, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        // Pre-fill data
        inputComment.setText(book.getComment());
        spinnerStatus.setSelection(book.getEstado() - 1); // -1 because position is 0-indexed


        // Set up the buttons
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String comment = inputComment.getText().toString();
                int status = spinnerStatus.getSelectedItemPosition() + 1;

                DbHelper dbHelper = new DbHelper(getContext());
                int rowsAffected = dbHelper.actualizarLibro(book.getId(), comment, status);
                dbHelper.close();

                if (rowsAffected > 0) {
                    book.setComment(comment);
                    book.setEstado(status);
                    notifyDataSetChanged();
                    Toast.makeText(getContext(), "Libro actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
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

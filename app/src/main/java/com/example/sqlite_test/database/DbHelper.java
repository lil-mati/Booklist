package com.example.sqlite_test.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite_test.Models.libro;
import com.example.sqlite_test.Models.usuario;
import com.example.sqlite_test.database.contracts.collectionContract;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "coleccion_libros.db";

    // esto actualiza la version (1 -> 2) de la db para insertar
    // la nueva columna "comentario" en la tabla "libro"
    // los datos previos localmente SE ELIMINARAN
    // (no estoy seguro si solo elimina datos de la tabla "libro" o de la base entera)
    public static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_COLLECTION_ENTRIES = 
            "CREATE TABLE " + collectionContract.collectionEntry.TABLE_NAME + " (" +
                    collectionContract.collectionEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    collectionContract.collectionEntry.COLUMN_ID_USUARIO + " INTEGER, " +
                    collectionContract.collectionEntry.COLUMN_ID_LIBRO + " INTEGER, " +
                    collectionContract.collectionEntry.COLUMN_FECHA_AGREGADO + " TEXT, " +
                    collectionContract.collectionEntry.COLUMN_FECHA_TERMINACION + " TEXT, " +
                    "FOREIGN KEY(" + collectionContract.collectionEntry.COLUMN_ID_USUARIO + ") REFERENCES " +
                    collectionContract.usuarioEntry.TABLE_NAME + "(" + collectionContract.usuarioEntry.COLUMN_ID + "))";

    private static final String SQL_CREATE_LIBRO_ENTRIES =
            "CREATE TABLE " + collectionContract.libroEntry.TABLE_NAME + " (" +
                    collectionContract.libroEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    collectionContract.libroEntry.COLUMN_TITULO + " TEXT, " +
                    collectionContract.libroEntry.COLUMN_AUTOR + " TEXT, " +
                    collectionContract.libroEntry.COLUMN_ESTADO + " INTEGER, " +
                    collectionContract.libroEntry.COLUMN_FECHA_PUBLICACION + " TEXT, " +
                    collectionContract.libroEntry.COLUMN_ID_TIPO + " INTEGER, " +
                    collectionContract.libroEntry.COLUMN_COMENTARIO + " TEXT, " +
                    "FOREIGN KEY(" + collectionContract.libroEntry.COLUMN_ESTADO + ") REFERENCES " +
                    collectionContract.estadoLecturaEntry.TABLE_NAME + "(" + collectionContract.estadoLecturaEntry.COLUMN_ID + "))";

    private static final String SQL_CREATE_USUARIO_ENTRIES =
            "CREATE TABLE " + collectionContract.usuarioEntry.TABLE_NAME + " (" +
                    collectionContract.usuarioEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    collectionContract.usuarioEntry.COLUMN_APELLIDO + " TEXT, " +
                    collectionContract.usuarioEntry.COLUMN_NOMBRE + " TEXT, " +
                    collectionContract.usuarioEntry.COLUMN_USUARIO + " TEXT, " +
                    collectionContract.usuarioEntry.COLUMN_CONTRASEÑA + " TEXT)";

    private static final String SQL_CREATE_TIPO_ENTRIES =
            "CREATE TABLE " + collectionContract.tipoLibroEntry.TABLE_NAME + " (" +
                    collectionContract.tipoLibroEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    collectionContract.tipoLibroEntry.COLUMN_NOMBRE + " TEXT, " +
                    collectionContract.tipoLibroEntry.COLUMN_DESCRIPCION + " TEXT)";

    private static final String SQL_CREATE_ESTADO_ENTRIES =
            "CREATE TABLE " + collectionContract.estadoLecturaEntry.TABLE_NAME + " (" +
                    collectionContract.estadoLecturaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    collectionContract.estadoLecturaEntry.COLUMN_NOMBRE + " TEXT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TIPO_ENTRIES);
        db.execSQL(SQL_CREATE_ESTADO_ENTRIES);
        db.execSQL(SQL_CREATE_LIBRO_ENTRIES);
        db.execSQL(SQL_CREATE_USUARIO_ENTRIES);
        db.execSQL(SQL_CREATE_COLLECTION_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Esta implementación borra los datos. En una app real, se usaría ALTER TABLE.
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.tipoLibroEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.estadoLecturaEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.libroEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.usuarioEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.collectionEntry.TABLE_NAME);
        onCreate(db);
    }

    public long insertLibro (libro newlibro){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(collectionContract.libroEntry.COLUMN_TITULO, newlibro.getTitulo());
        values.put(collectionContract.libroEntry.COLUMN_AUTOR, newlibro.getAutor());
        values.put(collectionContract.libroEntry.COLUMN_ESTADO, newlibro.getEstado());
        values.put(collectionContract.libroEntry.COLUMN_FECHA_PUBLICACION, newlibro.getFechaPublicacion());
        values.put(collectionContract.libroEntry.COLUMN_COMENTARIO, newlibro.getComment()); // Guardar también el comentario inicial

        long newRowId;
        newRowId = db.insert(collectionContract.libroEntry.TABLE_NAME, null, values);

        db.close();
        return newRowId;
    }

    // ACTUALIZAR EL COMENTARIO Y ESTADO DE UN LIBRO
    public int actualizarLibro(long libroId, String comentario, int estado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(collectionContract.libroEntry.COLUMN_COMENTARIO, comentario);
        values.put(collectionContract.libroEntry.COLUMN_ESTADO, estado);

        String selection = collectionContract.libroEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { String.valueOf(libroId) };

        int count = db.update(
                collectionContract.libroEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        
        // No cerramos la db aquí para poder reutilizar el helper
        return count;
    }

    public void insertDatosIniciales() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " +
                collectionContract.libroEntry.TABLE_NAME, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        if (count == 0) {
            ContentValues valuesTipo = new ContentValues();

            valuesTipo.put(collectionContract.tipoLibroEntry.COLUMN_NOMBRE, "Fantasia");
            valuesTipo.put(collectionContract.tipoLibroEntry.COLUMN_DESCRIPCION, "Libros de fantasía");
            db.insert(collectionContract.tipoLibroEntry.TABLE_NAME, null, valuesTipo);

            valuesTipo.put(collectionContract.tipoLibroEntry.COLUMN_NOMBRE, "Misterio");
            valuesTipo.put(collectionContract.tipoLibroEntry.COLUMN_DESCRIPCION, "Libros de misterio");
            db.insert(collectionContract.tipoLibroEntry.TABLE_NAME, null, valuesTipo);

            valuesTipo.put(collectionContract.tipoLibroEntry.COLUMN_NOMBRE, "Romance");
            valuesTipo.put(collectionContract.tipoLibroEntry.COLUMN_DESCRIPCION, "Libros de romance");
            db.insert(collectionContract.tipoLibroEntry.TABLE_NAME, null, valuesTipo);

            ContentValues valuesEstado = new ContentValues();

            valuesEstado.put(collectionContract.estadoLecturaEntry.COLUMN_NOMBRE, "Pendiente");
            db.insert(collectionContract.estadoLecturaEntry.TABLE_NAME, null, valuesEstado);

            valuesEstado.put(collectionContract.estadoLecturaEntry.COLUMN_NOMBRE, "Leyendo");
            db.insert(collectionContract.estadoLecturaEntry.TABLE_NAME, null, valuesEstado);

            valuesEstado.put(collectionContract.estadoLecturaEntry.COLUMN_NOMBRE, "Leido");
            db.insert(collectionContract.estadoLecturaEntry.TABLE_NAME, null, valuesEstado);
        }

        db.close();
    }

    public void inicializarBaseDeDatos() {
        insertDatosIniciales();
    }

    public Cursor getLibros() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(
                collectionContract.libroEntry.TABLE_NAME,
                null, // null para obtener todas las columnas
                null,
                null,
                null,
                null,
                collectionContract.libroEntry.COLUMN_TITULO + " DESC"
        );
    }

    public boolean eliminarLibro(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(
                collectionContract.libroEntry.TABLE_NAME,
                collectionContract.libroEntry.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
        db.close();
        return rowsDeleted > 0;
    }

    public long insertUsuario(usuario newusuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(collectionContract.usuarioEntry.COLUMN_APELLIDO, newusuario.getApellido());
        values.put(collectionContract.usuarioEntry.COLUMN_NOMBRE, newusuario.getNombre());
        values.put(collectionContract.usuarioEntry.COLUMN_USUARIO, newusuario.getUsuario());
        values.put(collectionContract.usuarioEntry.COLUMN_CONTRASEÑA, newusuario.getContraseña());

        long newRowId;
        newRowId = db.insert(collectionContract.usuarioEntry.TABLE_NAME, null, values);

        db.close();
        return newRowId;
    }

    public boolean validarUsuario(String usuario, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();
        //columnas a verificar
        String[] projection = {
                collectionContract.usuarioEntry.COLUMN_ID
        };

        //query o consulta
        String selection = collectionContract.usuarioEntry.COLUMN_USUARIO + " = ? COLLATE NOCASE AND " +
                collectionContract.usuarioEntry.COLUMN_CONTRASEÑA + " = ?";
        String[] selecciones = {usuario, contraseña};

        //recorre tablas y la consulta realizada
        Cursor cursor = db.query(
                collectionContract.usuarioEntry.TABLE_NAME,
                projection,
                selection,
                selecciones,
                null,
                null,
                null
        );

        boolean valido = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return valido;
    }
}

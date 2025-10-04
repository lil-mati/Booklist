package com.example.sqlite_test.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlite_test.database.contracts.collectionContract;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "coleccion_libros.db";
    public static final int DATABASE_VERSION = 1;

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
        db.execSQL(SQL_CREATE_COLLECTION_ENTRIES);
        db.execSQL(SQL_CREATE_LIBRO_ENTRIES);
        db.execSQL(SQL_CREATE_USUARIO_ENTRIES);
        db.execSQL(SQL_CREATE_TIPO_ENTRIES);
        db.execSQL(SQL_CREATE_ESTADO_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.collectionEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.libroEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.usuarioEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.tipoLibroEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + collectionContract.estadoLecturaEntry.TABLE_NAME);
        onCreate(db);
    }

}

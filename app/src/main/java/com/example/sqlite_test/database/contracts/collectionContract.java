package com.example.sqlite_test.database.contracts;

import android.provider.BaseColumns;

public class collectionContract {

    public static class libroEntry  {
        public static final String TABLE_NAME = "libros";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITULO = "titulo";
        public static final String COLUMN_AUTOR = "autor";
        public static final String COLUMN_ESTADO = "estado";
        public static final String COLUMN_FECHA_PUBLICACION = "fecha";
        public static final String COLUMN_ID_TIPO = "id_tipo";


    }
    public static class usuarioEntry {
        public static final String TABLE_NAME = "usuario";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_APELLIDO = "apellido";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_CONTRASEÑA = "contraseña";
        public static final String COLUMN_USUARIO = "usuario";

    }

    public static class tipoLibroEntry {
        public static final String TABLE_NAME = "tipo";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOMBRE = "nombre";
        public static final String COLUMN_DESCRIPCION = "descripcion";

    }

    public static class estadoLecturaEntry {
        public static final String TABLE_NAME = "estado";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NOMBRE = "nombre";

    }

    public static class collectionEntry {
        public static final String TABLE_NAME = "collection";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_ID_USUARIO = "id_usuario";
        public static final String COLUMN_ID_LIBRO = "id_libro";
        public static final String COLUMN_FECHA_AGREGADO = "fecha_agregado";
        public static final String COLUMN_FECHA_TERMINACION = "fecha_terminacion";

    }

}

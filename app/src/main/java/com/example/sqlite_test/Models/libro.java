package com.example.sqlite_test.Models;

public class libro {
    private int id;
    private String titulo;
    private String autor;
    private int estado;
    private String fechaPublicacion;
    private int idTipo;

    public libro() {}

    public libro(int id, String titulo, String autor, int estado, String fechaPublicacion, int idTipo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
        this.fechaPublicacion = fechaPublicacion;
        this.idTipo = idTipo;

    }

    public long getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

}

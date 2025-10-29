package com.example.sqlite_test.Models;

public class libro {
    private int id;
    private String titulo;
    private String autor;
    private int estado;
    private String fechaPublicacion;
    private int idTipo;
    private String comment; // Campo añadido

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
    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }
    public String getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(String fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }
    public int getTipo() { return this.idTipo; }
    public void setTipo(int tipo) { this.idTipo = tipo; }

    // Métodos añadidos
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}

package com.example.sqlite_test.Models;

public class usuario {
    private int id;
    private String apellido;
    private String nombre;
    private String contraseña;
    private String usuario;

    public usuario() {}

    public usuario(int id, String apellido, String nombre, String contraseña, String usuario) {
        this.id = id;
        this.apellido = apellido;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.usuario = usuario;
    }

    public long getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

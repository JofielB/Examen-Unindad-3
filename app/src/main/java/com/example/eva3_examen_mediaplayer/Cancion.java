package com.example.eva3_examen_mediaplayer;

public class Cancion {
    private int imagen;
    private String Titulo;
    private String Descripcion;

    public Cancion(int imagen, String titulo, String descripcion) {
        this.imagen = imagen;
        Titulo = titulo;
        Descripcion = descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}

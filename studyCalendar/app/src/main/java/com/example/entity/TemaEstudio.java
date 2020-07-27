package com.example.entity;

import java.io.Serializable;

public class TemaEstudio implements Serializable {


    private  long id;
    private String titulo;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TemaEstudio{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

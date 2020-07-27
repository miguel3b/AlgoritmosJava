package com.example.dto;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class ActividadDiariaDTO implements Serializable {

    private String titulo;
    private String description;
    private Long idEstudio;
    private Date fechaEstudio;
    private Integer numeroRepaso;
    private Integer terminado;

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

    public Long getIdEstudio() {
        return idEstudio;
    }

    public void setIdEstudio(Long idEstudio) {
        this.idEstudio = idEstudio;
    }

    public Date getFechaEstudio() {
        return fechaEstudio;
    }

    public void setFechaEstudio(Date fechaEstudio) {
        this.fechaEstudio = fechaEstudio;
    }

    public Integer getNumeroRepaso() {
        return numeroRepaso;
    }

    public void setNumeroRepaso(Integer numeroRepaso) {
        this.numeroRepaso = numeroRepaso;
    }

    public Integer getTerminado() {
        return terminado;
    }

    public void setTerminado(Integer terminado) {
        this.terminado = terminado;
    }

    @Override
    public String toString() {
        return "ActividadDiariaDTO{" +
                "titulo='" + titulo + '\'' +
                ", description='" + description + '\'' +
                ", idEstudio=" + idEstudio +
                ", fechaEstudio=" + fechaEstudio +
                ", numeroRepaso=" + numeroRepaso +
                ", terminado=" + terminado +
                '}';
    }
}

package com.example.entity;

import java.io.Serializable;
import java.util.Date;

public class EstudioDetalle implements Serializable {

    private Integer id;
    private Long idTemaEstudio;
    private Date fechaEstudio;
    private Integer numeroRepaso;
    private Integer terminado;

    public Integer getId() {
        return id;
    }

    public Date getFechaEstudio() {
        return fechaEstudio;
    }

    public void setFechaEstudio(Date fechaEstudio) {
        this.fechaEstudio = fechaEstudio;
    }

    public Long getIdTemaEstudio() {
        return idTemaEstudio;
    }

    public void setIdTemaEstudio(Long idTemaEstudio) {
        this.idTemaEstudio = idTemaEstudio;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "EstudioDetalle{" +
                "id=" + id +
                ", idTemaEstudio=" + idTemaEstudio +
                ", fechaEstudio=" + fechaEstudio +
                ", numeroRepaso=" + numeroRepaso +
                ", terminado=" + terminado +
                '}';
    }
}

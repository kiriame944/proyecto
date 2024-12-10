package com.example.base;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Tarea {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String materia;
    private String descripcion;
    private Date fechaHoraLimite;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaHoraLimite() {
        return fechaHoraLimite;
    }

    public void setFechaHoraLimite(Date fechaHoraLimite) {
        this.fechaHoraLimite = fechaHoraLimite;
    }
}
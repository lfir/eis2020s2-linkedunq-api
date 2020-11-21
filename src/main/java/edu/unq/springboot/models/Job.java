package edu.unq.springboot.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private User owner;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicioTrabajo;
    private LocalDate fechaFinTrabajo;
    private String enlace;
    private String urlImagen;
    @Min(1)
    @Max(3)
    private Integer prioridad;

    public Job() {}

    public Job(User usuario, String titulo, String descripcion,
               LocalDate fechaInicioTrabajo, LocalDate fechaFinTrabajo, String enlace,
               String urlImagen, Integer prioridad) {
        this.owner = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicioTrabajo = fechaInicioTrabajo;
        this.fechaFinTrabajo = fechaFinTrabajo;
        this.enlace = enlace;
        this.urlImagen = urlImagen;
        this.prioridad = prioridad;
    }
    
    public String getUrlImagen() {
    	return this.urlImagen;
    }
    
    public void setUrlImagen(String urlImagen) {
    	this.urlImagen = urlImagen;
    }

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicioTrabajo() {
        return fechaInicioTrabajo;
    }

    public void setFechaInicioTrabajo(LocalDate fechaInicioTrabajo) {
        this.fechaInicioTrabajo = fechaInicioTrabajo;
    }

    public LocalDate getFechaFinTrabajo() {
        return fechaFinTrabajo;
    }

    public void setFechaFinTrabajo(LocalDate fechaFinTrabajo) {
        this.fechaFinTrabajo = fechaFinTrabajo;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
}

package edu.unq.springboot.models;

public class CreateJobRequestBody {
	private String username;
	private String titulo;
	private String descripcion;
	private String fechaInicioTrabajo;
	private String fechaFinTrabajo;
	
	public String getUsername() {
		return username;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public String getFechaInicioTrabajo() {
		return fechaInicioTrabajo;
	}
	
	public String getFechaFinTrabajo() {
		return fechaFinTrabajo;
	}
}

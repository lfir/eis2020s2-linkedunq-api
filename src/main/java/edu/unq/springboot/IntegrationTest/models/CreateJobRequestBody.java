package edu.unq.springboot.IntegrationTest.models;

public class CreateJobRequestBody {
	private String username;
	private String titulo;
	private String descripcion;
	private String fechaInicioTrabajo;
	private String fechaFinTrabajo;
	
	public CreateJobRequestBody(String username, String titulo, String descripcion, String fechaInicioTrabajo,
			String fechaFinTrabajo) {
		this.username = username;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicioTrabajo = fechaInicioTrabajo;
		this.fechaFinTrabajo = fechaFinTrabajo;
	}

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

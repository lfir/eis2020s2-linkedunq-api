package edu.unq.springboot.models;

public class CreateJobRequestBody {
	private String username;
	private String titulo;
	private String descripcion;
	private String fechaInicioTrabajo;
	private String fechaFinTrabajo;
	private String enlace;
	private String urlImagen;
	
	public CreateJobRequestBody(String username, String titulo, String descripcion, String fechaInicioTrabajo,
			String fechaFinTrabajo, String enlace, String urlImagen) {
		this.username = username;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.fechaInicioTrabajo = fechaInicioTrabajo;
		this.fechaFinTrabajo = fechaFinTrabajo;
		this.enlace = enlace;
		this.urlImagen = urlImagen;
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

	public String getEnlace() {
		return enlace;
	}

	public String getUrlImagen() {
		return this.urlImagen;
	}

}

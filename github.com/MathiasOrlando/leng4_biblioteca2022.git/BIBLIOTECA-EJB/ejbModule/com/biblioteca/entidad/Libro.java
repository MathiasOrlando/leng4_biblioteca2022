package com.biblioteca.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "libros")

public class Libro {
	
	@Column(name = "lib_codigo")
	private Integer codigo;
	
	@Column(name = "lib_descripcion")
	private String descripcion;
	
	@Column(name = "lib_cantidad")
	private Integer cantidad;
	
	@Column(name = "lib_obs")
	private String obs;

	public Libro() {
		super();
		
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}
	

}

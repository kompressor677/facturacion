package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="pruebas")
public class Prueba extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9102516262371031853L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long pruebaid;
	private String descripcion;
	
	

	public Long getPruebaid() {
		return pruebaid;
	}

	public void setPruebaid(Long pruebaid) {
		this.pruebaid = pruebaid;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public Object[] getArrayObjectDatos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStringDatos() {
		// TODO Auto-generated method stub
		return null;
	}

}

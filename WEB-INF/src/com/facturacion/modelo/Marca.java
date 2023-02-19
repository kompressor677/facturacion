package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="marcas")
public class Marca extends Modelo implements Serializable{

	private static final long serialVersionUID = -670537212617149973L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long marcaid;
	
	private String marca;
	
	public Long getMarcaid() {
		return marcaid;
	}

	public void setMarcaid(Long marcaid) {
		this.marcaid = marcaid;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
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

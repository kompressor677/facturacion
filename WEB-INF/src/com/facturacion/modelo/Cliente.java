package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="clientes")
public class Cliente extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7708050628668031572L;

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

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long clienteid;
	
	private String nombre;
	private String apellido;
	private String RUC;
	private Long CI;

	public Long getClienteid() {
		return clienteid;
	}

	public void setClienteid(Long clienteid) {
		this.clienteid = clienteid;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getRUC() {
		return RUC;
	}

	public void setRUC(String rUC) {
		RUC = rUC;
	}

	public Long getCI() {
		return CI;
	}

	public void setCI(Long cI) {
		CI = cI;
	}


}

package com.facturacion.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name = "ajustes")
public class Ajuste extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8671319504845213149L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ajusteid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date apertura;

	private String observacion;

	public Long getAjusteid() {
		return ajusteid;
	}

	public void setAjusteid(Long ajusteid) {
		this.ajusteid = ajusteid;
	}

	public Date getApertura() {
		return apertura;
	}

	public void setApertura(Date apertura) {
		this.apertura = apertura;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

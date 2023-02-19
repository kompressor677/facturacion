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
@Table(name="timbrados")
public class Timbrado extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 644796633095582385L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long timbradoid;
	
	private String sucursal;
	private String expedicion;
	
	@Temporal(TemporalType.DATE)
	private Date vencimiento;
	private Long nactual;
	
	@Temporal(TemporalType.DATE)
	private Date fechaIniFact;
	private Long timbrado;
	
	public Long getTimbradoid() {
		return timbradoid;
	}

	public void setTimbradoid(Long timbradoid) {
		this.timbradoid = timbradoid;
	}

	public String getSucursal() {
		return sucursal;
	}

	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

	public String getExpedicion() {
		return expedicion;
	}

	public void setExpedicion(String expedicion) {
		this.expedicion = expedicion;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public Long getNactual() {
		return nactual;
	}

	public void setNactual(Long nactual) {
		this.nactual = nactual;
	}

	public Date getFechaIniFact() {
		return fechaIniFact;
	}

	public void setFechaIniFact(Date fechaIniFact) {
		this.fechaIniFact = fechaIniFact;
	}

	public Long getTimbrado() {
		return timbrado;
	}

	public void setTimbrado(Long timbrado) {
		this.timbrado = timbrado;
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

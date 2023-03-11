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
@Table(name = "compras")
public class Compra extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3937443681049497687L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long compraid;

	@Temporal(TemporalType.TIMESTAMP)
	private Date apertura;

	private String nfactura;
	private String ctimbrado;

	public Long getCompraid() {
		return compraid;
	}

	public void setCompraid(Long compraid) {
		this.compraid = compraid;
	}

	public Date getApertura() {
		return apertura;
	}

	public void setApertura(Date apertura) {
		this.apertura = apertura;
	}

	public String getNfactura() {
		return nfactura;
	}

	public void setNfactura(String nfactura) {
		this.nfactura = nfactura;
	}

	public String getCtimbrado() {
		return ctimbrado;
	}

	public void setCtimbrado(String ctimbrado) {
		this.ctimbrado = ctimbrado;
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

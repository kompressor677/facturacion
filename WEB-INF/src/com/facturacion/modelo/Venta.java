package com.facturacion.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.doxacore.modelo.Modelo;


@Entity
@Table(name = "ventas")
public class Venta extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3823222724680882820L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ventaid;
	

	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="ventaid")
	private List<VentaDetalle> detalles = new ArrayList<VentaDetalle>();
	
	

	public List<VentaDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<VentaDetalle> detalles) {
		this.detalles = detalles;
	}

	private String nfactura;
	private String vtimbrado;

	@Temporal(TemporalType.TIMESTAMP)
	private Date apertura;

	public Long getVentaid() {
		return ventaid;
	}

	public void setVentaid(Long ventaid) {
		this.ventaid = ventaid;
	}

	public String getNfactura() {
		return nfactura;
	}

	public void setNfactura(String nfactura) {
		this.nfactura = nfactura;
	}

	public String getVtimbrado() {
		return vtimbrado;
	}

	public void setVtimbrado(String vtimbrado) {
		this.vtimbrado = vtimbrado;
	}

	public Date getApertura() {
		return apertura;
	}

	public void setApertura(Date apertura) {
		this.apertura = apertura;
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

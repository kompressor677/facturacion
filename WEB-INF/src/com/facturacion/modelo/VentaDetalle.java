package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="ventasDetalles")
public class VentaDetalle extends Modelo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1868887335000313311L;

	@Id
	@ManyToOne
	@JoinColumns({@JoinColumn(name="productoid"),
		          @JoinColumn(name="productodeposito")})
	private ProductoDeposito productodeposito;
	
	private double cantidad;
	private double precioVenta;
	

	
	//@JoinColumn(name = "ventaid")
  //  private Long ventaid;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ventaid")
	private Venta venta;
	
	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

	public ProductoDeposito getProductodeposito() {
		return productodeposito;
	}

	public void setProductodeposito(ProductoDeposito productodeposito) {
		this.productodeposito = productodeposito;
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

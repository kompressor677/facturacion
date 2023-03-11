package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="ProductosDepositos")
public class ProductoDeposito extends Modelo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9127142953044490345L;

	
	@EmbeddedId
	private ProductoDepositoPK productodepositopk;
	
	private double cantidad;
	
	
	public ProductoDepositoPK getProductodepositopk() {
		return productodepositopk;
	}

	public void setProductodepositopk(ProductoDepositoPK productodepositopk) {
		this.productodepositopk = productodepositopk;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
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

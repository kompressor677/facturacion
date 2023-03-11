package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ProductoDepositoPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2600132939831317020L;
	
	@ManyToOne
	@JoinColumn(name="productoid")
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(name="depositoid")
	private Deposito deposito;

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}
	
	
	
}

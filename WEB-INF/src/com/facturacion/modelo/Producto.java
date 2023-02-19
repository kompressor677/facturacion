package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="productos")
public class Producto extends Modelo implements Serializable {

	private static final long serialVersionUID = 7358215024115292250L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productoid;
	
	private String producto;
	private Long precio;
	private String codigobarra;
	
	@ManyToOne
	@JoinColumn(name = "marcaid")
	private Marca marcaFK;
	
	public Long getProductoid() {
		return productoid;
	}

	public void setProductoid(Long productoid) {
		this.productoid = productoid;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public Long getPrecio() {
		return precio;
	}

	public void setPrecio(Long precio) {
		this.precio = precio;
	}

	public Marca getMarcaFK() {
		return marcaFK;
	}

	public void setMarcaFK(Marca marcaFK) {
		this.marcaFK = marcaFK;
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

	public String getCodigobarra() {
		return codigobarra;
	}

	public void setCodigobarra(String codigobarra) {
		this.codigobarra = codigobarra;
	}

}

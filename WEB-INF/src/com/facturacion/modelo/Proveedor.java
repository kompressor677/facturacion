package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name="proveedores")
public class Proveedor extends Modelo implements Serializable{

	
	private static final long serialVersionUID = -5733622031253650898L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long proveedorid;
	
	private String proveedor;
	private String ruc;
	

	public Long getProveedorid() {
		return proveedorid;
	}

	public void setProveedorid(Long proveedorid) {
		this.proveedorid = proveedorid;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

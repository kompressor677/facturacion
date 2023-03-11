package com.facturacion.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.doxacore.modelo.Modelo;

@Entity
@Table(name = "depositos")
public class Deposito extends Modelo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5056573214563841830L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long depositoid;

	private String deposito;

	public Long getDepositoid() {
		return depositoid;
	}

	public void setDepositoid(Long depositoid) {
		this.depositoid = depositoid;
	}

	public String getDeposito() {
		return deposito;
	}

	public void setDeposito(String deposito) {
		this.deposito = deposito;
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

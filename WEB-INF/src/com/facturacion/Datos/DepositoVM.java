package com.facturacion.Datos;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.util.Notification;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.facturacion.modelo.Deposito;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class DepositoVM extends TemplateViewModelLocal {

	private List<Object[]> lDepositos;
	private List<Object[]> lDepositosOri;
	private Deposito depositoSelected;

	private boolean opCrearDeposito;
	private boolean opEditarDeposito;
	private boolean opBorrarDeposito;

	@Init(superclass = true)
	public void initDepositoVM() {

		cargarDepositos();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeDepositoVM() {

	}

	@Override
	protected void inicializarOperaciones() {

		this.opCrearDeposito = this.operacionHabilitada(ParamsLocal.OP_CREAR_DEPOSITO);
		this.opEditarDeposito = this.operacionHabilitada(ParamsLocal.OP_EDITAR_DEPOSITO);
		this.opBorrarDeposito = this.operacionHabilitada(ParamsLocal.OP_BORRAR_DEPOSITO);

	}

	private void cargarDepositos() {

		String sql = this.um.getSql("listaDepositos.sql");
		this.lDepositos = this.reg.sqlNativo(sql);
		this.lDepositosOri = this.lDepositos;

	}

	// seccion filtro

	private String filtroColumns[];

	private void inicializarFiltros() {

		this.filtroColumns = new String[2]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el
											// modelo

		for (int i = 0; i < this.filtroColumns.length; i++) {

			this.filtroColumns[i] = "";

		}

	}

	@Command
	@NotifyChange("lDepositos")
	public void filtrarDeposito() {

		this.lDepositos = this.filtrarListaObject(this.filtroColumns, this.lDepositosOri);

	}

	// fin seccion

	// seccion modal

	private Window modal;
	private boolean editar = false;

	@Command
	public void modalDepositosAgregar() {

		if (!this.opCrearDeposito)
			return;

		this.editar = false;
		modalDepositos(-1);

	}

	@Command
	public void modalDepositos(@BindingParam("depositoid") long depositoid) {

		if (depositoid != -1) {

			if (!this.opEditarDeposito)
				return;

			this.depositoSelected = this.reg.getObjectById(Deposito.class.getName(), depositoid);
			this.editar = true;

		} else {

			depositoSelected = new Deposito();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/depositosModal.zul", this.mainComponent, null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lDepositos")
	public void guardar() {

		this.save(depositoSelected);

		this.depositoSelected = null;

		this.cargarDepositos();

		this.modal.detach();

		if (editar) {

			Notification.show("El deposito fue actualizado.");
			this.editar = false;
		} else {

			Notification.show("EL deposito fue agregado.");
		}

	}

	// fin modal

	@Command
	public void borrarDepositoConfirmacion(@BindingParam("depositoid") Long depositoid) {

		if (!this.opBorrarDeposito)
			return;

		Deposito depositos = this.reg.getObjectById(Deposito.class.getName(), depositoid);

		EventListener event = new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {

				if (evt.getName().equals(Messagebox.ON_YES)) {

					borrarDeposito(depositos);

				}

			}

		};

		this.mensajeEliminar("El deposito será eliminado. \n Continuar?", event);
	}

	private void borrarDeposito(Deposito deposito) {

		this.reg.deleteObject(deposito);

		this.cargarDepositos();

		BindUtils.postNotifyChange(null, null, this, "lDepositos");

	}

	public List<Object[]> getlDepositos() {
		return lDepositos;
	}

	public void setlDepositos(List<Object[]> lDepositos) {
		this.lDepositos = lDepositos;
	}

	public Deposito getDepositoSelected() {
		return depositoSelected;
	}

	public void setDepositoSelected(Deposito depositoSelected) {
		this.depositoSelected = depositoSelected;
	}

	public boolean isOpCrearDeposito() {
		return opCrearDeposito;
	}

	public void setOpCrearDeposito(boolean opCrearDeposito) {
		this.opCrearDeposito = opCrearDeposito;
	}

	public boolean isOpEditarDeposito() {
		return opEditarDeposito;
	}

	public void setOpEditarDeposito(boolean opEditarDeposito) {
		this.opEditarDeposito = opEditarDeposito;
	}

	public boolean isOpBorrarDeposito() {
		return opBorrarDeposito;
	}

	public void setOpBorrarDeposito(boolean opBorrarDeposito) {
		this.opBorrarDeposito = opBorrarDeposito;
	}

	public String[] getFiltroColumns() {
		return filtroColumns;
	}

	public void setFiltroColumns(String[] filtroColumns) {
		this.filtroColumns = filtroColumns;
	}

	public boolean isEditar() {
		return editar;
	}

	public void setEditar(boolean editar) {
		this.editar = editar;
	}

}

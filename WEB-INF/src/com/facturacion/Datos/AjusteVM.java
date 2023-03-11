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

import com.facturacion.modelo.Ajuste;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class AjusteVM extends TemplateViewModelLocal {

	private List<Object[]> lAjustes;
	private List<Object[]> lAjustesOri;
	private Ajuste ajusteSelected;

	private boolean opCrearAjuste;
	private boolean opEditarAjuste;
	private boolean opBorrarAjuste;

	@Init(superclass = true)
	public void initAjusteVM() {

		cargarAjustes();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeAjusteVM() {

	}

	@Override
	protected void inicializarOperaciones() {

		this.opCrearAjuste = this.operacionHabilitada(ParamsLocal.OP_CREAR_AJUSTE);
		this.opEditarAjuste = this.operacionHabilitada(ParamsLocal.OP_EDITAR_AJUSTE);
		this.opBorrarAjuste = this.operacionHabilitada(ParamsLocal.OP_BORRAR_AJUSTE);

	}

	private void cargarAjustes() {

		String sql = this.um.getSql("listaAjustes.sql");
		this.lAjustes = this.reg.sqlNativo(sql);
		this.lAjustesOri = this.lAjustes;

	}

	// seccion filtro

	private String filtroColumns[];

	private void inicializarFiltros() {

		this.filtroColumns = new String[3]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el
											// modelo

		for (int i = 0; i < this.filtroColumns.length; i++) {

			this.filtroColumns[i] = "";

		}

	}

	@Command
	@NotifyChange("lAjustes")
	public void filtrarAjuste() {

		this.lAjustes = this.filtrarListaObject(this.filtroColumns, this.lAjustesOri);

	}

	// fin seccion

	// seccion modal

	private Window modal;
	private boolean editar = false;

	@Command
	public void modalAjustesAgregar() {

		if (!this.opCrearAjuste)
			return;

		this.editar = false;
		modalAjustes(-1);

	}

	@Command
	public void modalAjustes(@BindingParam("ajusteid") long ajusteid) {

		if (ajusteid != -1) {

			if (!this.opEditarAjuste)
				return;

			this.ajusteSelected = this.reg.getObjectById(Ajuste.class.getName(), ajusteid);
			this.editar = true;

		} else {

			ajusteSelected = new Ajuste();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/ajustesModal.zul", this.mainComponent, null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lAjustes")
	public void guardar() {

		this.save(ajusteSelected);

		this.ajusteSelected = null;

		this.cargarAjustes();

		this.modal.detach();

		if (editar) {

			Notification.show("El ajuste fue actualizado.");
			this.editar = false;
		} else {

			Notification.show("El ajuste fue agregado.");
		}

	}

	// fin modal

	@Command
	public void borrarAjusteConfirmacion(@BindingParam("ajusteid") Long ajusteid) {

		if (!this.opBorrarAjuste)
			return;

		Ajuste ajustes = this.reg.getObjectById(Ajuste.class.getName(), ajusteid);

		EventListener event = new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {

				if (evt.getName().equals(Messagebox.ON_YES)) {

					borrarAjuste(ajustes);

				}

			}

		};

		this.mensajeEliminar("El ajuste sera eliminado. \n Continuar?", event);
	}

	private void borrarAjuste(Ajuste ajuste) {

		this.reg.deleteObject(ajuste);

		this.cargarAjustes();

		BindUtils.postNotifyChange(null, null, this, "lAjustes");

	}

	public List<Object[]> getlAjustes() {
		return lAjustes;
	}

	public void setlAjustes(List<Object[]> lAjustes) {
		this.lAjustes = lAjustes;
	}

	public Ajuste getAjusteSelected() {
		return ajusteSelected;
	}

	public void setAjusteSelected(Ajuste ajusteSelected) {
		this.ajusteSelected = ajusteSelected;
	}

	public boolean isOpCrearAjuste() {
		return opCrearAjuste;
	}

	public void setOpCrearAjuste(boolean opCrearAjuste) {
		this.opCrearAjuste = opCrearAjuste;
	}

	public boolean isOpEditarAjuste() {
		return opEditarAjuste;
	}

	public void setOpEditarAjuste(boolean opEditarAjuste) {
		this.opEditarAjuste = opEditarAjuste;
	}

	public boolean isOpBorrarAjuste() {
		return opBorrarAjuste;
	}

	public void setOpBorrarAjuste(boolean opBorrarAjuste) {
		this.opBorrarAjuste = opBorrarAjuste;
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

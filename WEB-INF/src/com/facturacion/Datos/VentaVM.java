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

import com.facturacion.modelo.Venta;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class VentaVM extends TemplateViewModelLocal {

	private List<Object[]> lVentas;
	private List<Object[]> lVentasOri;
	private Venta ventaSelected;

	private boolean opCrearVenta;
	private boolean opEditarVenta;
	private boolean opBorrarVenta;

	@Init(superclass = true)
	public void initVentaVM() {

		cargarVentas();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeVentaVM() {

	}

	@Override
	protected void inicializarOperaciones() {

		this.opCrearVenta = this.operacionHabilitada(ParamsLocal.OP_CREAR_VENTA);
		this.opEditarVenta = this.operacionHabilitada(ParamsLocal.OP_EDITAR_VENTA);
		this.opBorrarVenta = this.operacionHabilitada(ParamsLocal.OP_BORRAR_VENTA);

	}

	private void cargarVentas() {

		String sql = this.um.getSql("listaVentas.sql");
		this.lVentas = this.reg.sqlNativo(sql);
		this.lVentasOri = this.lVentas;

	}

	// seccion filtro

	private String filtroColumns[];

	private void inicializarFiltros() {

		this.filtroColumns = new String[4]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el
											// modelo

		for (int i = 0; i < this.filtroColumns.length; i++) {

			this.filtroColumns[i] = "";

		}

	}

	@Command
	@NotifyChange("lVentas")
	public void filtrarVenta() {

		this.lVentas = this.filtrarListaObject(this.filtroColumns, this.lVentasOri);

	}

	// fin seccion

	// seccion modal

	private Window modal;
	private boolean editar = false;

	@Command
	public void modalVentasAgregar() {

		if (!this.opCrearVenta)
			return;

		this.editar = false;
		modalVentas(-1);

	}

	@Command
	public void modalVentas(@BindingParam("ventaid") long ventaid) {

		if (ventaid != -1) {

			if (!this.opEditarVenta)
				return;

			this.ventaSelected = this.reg.getObjectById(Venta.class.getName(), ventaid);
			this.editar = true;

		} else {

			ventaSelected = new Venta();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/ventasModal.zul", this.mainComponent, null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lVentas")
	public void guardar() {

		this.save(ventaSelected);

		this.ventaSelected = null;

		this.cargarVentas();

		this.modal.detach();

		if (editar) {

			Notification.show("La venta fue actualizada.");
			this.editar = false;
		} else {

			Notification.show("La venta fue agregada.");
		}

	}

	// fin modal

	@Command
	public void borrarVentaConfirmacion(@BindingParam("ventaid") Long ventaid) {

		if (!this.opBorrarVenta)
			return;

		Venta ventas = this.reg.getObjectById(Venta.class.getName(), ventaid);

		EventListener event = new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {

				if (evt.getName().equals(Messagebox.ON_YES)) {

					borrarVenta(ventas);

				}

			}

		};

		this.mensajeEliminar("La venta sera eliminada. \n Continuar?", event);
	}

	private void borrarVenta(Venta venta) {

		this.reg.deleteObject(venta);

		this.cargarVentas();

		BindUtils.postNotifyChange(null, null, this, "lVentas");

	}

	public List<Object[]> getlVentas() {
		return lVentas;
	}

	public void setlVentas(List<Object[]> lVentas) {
		this.lVentas = lVentas;
	}

	public Venta getVentaSelected() {
		return ventaSelected;
	}

	public void setVentaSelected(Venta ventaSelected) {
		this.ventaSelected = ventaSelected;
	}

	public boolean isOpCrearVenta() {
		return opCrearVenta;
	}

	public void setOpCrearVenta(boolean opCrearVenta) {
		this.opCrearVenta = opCrearVenta;
	}

	public boolean isOpEditarVenta() {
		return opEditarVenta;
	}

	public void setOpEditarVenta(boolean opEditarVenta) {
		this.opEditarVenta = opEditarVenta;
	}

	public boolean isOpBorrarVenta() {
		return opBorrarVenta;
	}

	public void setOpBorrarVenta(boolean opBorrarVenta) {
		this.opBorrarVenta = opBorrarVenta;
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

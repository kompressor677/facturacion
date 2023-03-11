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

import com.facturacion.modelo.Compra;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class CompraVM extends TemplateViewModelLocal {

	private List<Object[]> lCompras;
	private List<Object[]> lComprasOri;
	private Compra compraSelected;

	private boolean opCrearCompra;
	private boolean opEditarCompra;
	private boolean opBorrarCompra;

	@Init(superclass = true)
	public void initCompraVM() {

		cargarCompras();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeCompraVM() {

	}

	@Override
	protected void inicializarOperaciones() {

		this.opCrearCompra = this.operacionHabilitada(ParamsLocal.OP_CREAR_COMPRA);
		this.opEditarCompra = this.operacionHabilitada(ParamsLocal.OP_EDITAR_COMPRA);
		this.opBorrarCompra = this.operacionHabilitada(ParamsLocal.OP_BORRAR_COMPRA);

	}

	private void cargarCompras() {

		String sql = this.um.getSql("listaCompras.sql");
		this.lCompras = this.reg.sqlNativo(sql);
		this.lComprasOri = this.lCompras;

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
	@NotifyChange("lCompras")
	public void filtrarCompra() {

		this.lCompras = this.filtrarListaObject(this.filtroColumns, this.lComprasOri);

	}

	// fin seccion

	// seccion modal

	private Window modal;
	private boolean editar = false;

	@Command
	public void modalComprasAgregar() {

		if (!this.opCrearCompra)
			return;

		this.editar = false;
		modalCompras(-1);

	}

	@Command
	public void modalCompras(@BindingParam("compraid") long compraid) {

		if (compraid != -1) {

			if (!this.opEditarCompra)
				return;

			this.compraSelected = this.reg.getObjectById(Compra.class.getName(), compraid);
			this.editar = true;

		} else {

			compraSelected = new Compra();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/comprasModal.zul", this.mainComponent, null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lCompras")
	public void guardar() {

		this.save(compraSelected);

		this.compraSelected = null;

		this.cargarCompras();

		this.modal.detach();

		if (editar) {

			Notification.show("La compra fue actualizada.");
			this.editar = false;
		} else {

			Notification.show("La compra fue agregada.");
		}

	}

	// fin modal

	@Command
	public void borrarCompraConfirmacion(@BindingParam("compraid") Long compraid) {

		if (!this.opBorrarCompra)
			return;

		Compra compras = this.reg.getObjectById(Compra.class.getName(), compraid);

		EventListener event = new EventListener() {

			@Override
			public void onEvent(Event evt) throws Exception {

				if (evt.getName().equals(Messagebox.ON_YES)) {

					borrarCompra(compras);

				}

			}

		};

		this.mensajeEliminar("La compra será eliminada. \n Continuar?", event);
	}

	private void borrarCompra(Compra compra) {

		this.reg.deleteObject(compra);

		this.cargarCompras();

		BindUtils.postNotifyChange(null, null, this, "lCompras");

	}

	public List<Object[]> getlCompras() {
		return lCompras;
	}

	public void setlCompras(List<Object[]> lCompras) {
		this.lCompras = lCompras;
	}

	public Compra getCompraSelected() {
		return compraSelected;
	}

	public void setCompraSelected(Compra compraSelected) {
		this.compraSelected = compraSelected;
	}

	public boolean isOpCrearCompra() {
		return opCrearCompra;
	}

	public void setOpCrearCompra(boolean opCrearCompra) {
		this.opCrearCompra = opCrearCompra;
	}

	public boolean isOpEditarCompra() {
		return opEditarCompra;
	}

	public void setOpEditarCompra(boolean opEditarCompra) {
		this.opEditarCompra = opEditarCompra;
	}

	public boolean isOpBorrarCompra() {
		return opBorrarCompra;
	}

	public void setOpBorrarCompra(boolean opBorrarCompra) {
		this.opBorrarCompra = opBorrarCompra;
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

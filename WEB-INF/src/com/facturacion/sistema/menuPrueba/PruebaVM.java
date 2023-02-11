package com.facturacion.sistema.menuPrueba;

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


import com.facturacion.modelo.Prueba;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class PruebaVM extends TemplateViewModelLocal {
	
	private List<Object[]> lPruebas; 
	private List<Object[]> lPruebasOri;
	private Prueba pruebaSelected;
	
	private boolean opCrearPrueba;
	private boolean opEditarPrueba;
	private boolean opBorrarPrueba;
	
	
	@Init(superclass = true)
	public void initPruebaVM() {

		cargarPruebas();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposePruebaVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearPrueba = this.operacionHabilitada(ParamsLocal.OP_CREAR_PRUEBA);
		this.opEditarPrueba = this.operacionHabilitada(ParamsLocal.OP_EDITAR_PRUEBA);
		this.opBorrarPrueba = this.operacionHabilitada(ParamsLocal.OP_BORRAR_PRUEBA);
	
		
	}
	

	private void cargarPruebas() {

		String sql = this.um.getSql("listaPruebaS.sql");
		this.lPruebas = this.reg.sqlNativo(sql);
		this.lPruebasOri = this.lPruebas;
		
	}
	
	//seccion filtro 
	
	private String filtroColumns[];
	
	private void inicializarFiltros(){
		
		this.filtroColumns = new String[2]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el modelo
		
		for (int i = 0 ; i<this.filtroColumns.length; i++) {
			
			this.filtroColumns[i] = "";
			
		}
		
	}
	
	@Command
	@NotifyChange("lPruebas")
	public void filtrarPrueba() {

		this.lPruebas = this.filtrarListaObject(this.filtroColumns, this.lPruebasOri);

	}
	
	//fin seccion 
	
	//seccion modal
	
	private Window modal;
	private boolean editar = false;

	@Command
	public void modalPruebaAgregar() {

		if(!this.opCrearPrueba)
			return;

		this.editar = false;
		modalPrueba(-1);

	}

	@Command
	public void modalPrueba(@BindingParam("pruebaid") long pruebaid) {

		if (pruebaid != -1) {

			if(!this.opEditarPrueba)
				return;
			
			this.pruebaSelected = this.reg.getObjectById(Prueba.class.getName(), pruebaid);
			this.editar = true;

		} else {
			
			pruebaSelected = new Prueba();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/menuPrueba/pruebaModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lPruebas")
	public void guardar() {

		this.save(pruebaSelected);

		this.pruebaSelected = null;

		this.cargarPruebas();

		this.modal.detach();
		
		if (editar) {
			
			Notification.show("El Prueba fue Actualizado.");
			this.editar = false;
		}else {
			
			Notification.show("El Prueba fue agregado.");
		}
		
		

	}

	
	//fin modal
	
	@Command
	public void borrarPruebaConfirmacion(@BindingParam("prueba") Long pruebaid) {
		
		if (!this.opBorrarPrueba)
			return;
		
		Prueba prueba = this.reg.getObjectById(Prueba.class.getName(), pruebaid);
		
		EventListener event = new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				
				if (evt.getName().equals(Messagebox.ON_YES)) {
					
					borrarPrueba(prueba);
					
				}
				
			}

		};
		
		this.mensajeEliminar("El prueba sera eliminado. \n Continuar?", event);
	}
	
	
	private void borrarPrueba (Prueba prueba) {
		
		this.reg.deleteObject(prueba);
		
		this.cargarPruebas();
		
		BindUtils.postNotifyChange(null,null,this,"lPruebas");
		
	}

	public List<Object[]> getlPruebas() {
		return lPruebas;
	}

	public void setlPruebas(List<Object[]> lPruebas) {
		this.lPruebas = lPruebas;
	}

	public Prueba getPruebaSelected() {
		return pruebaSelected;
	}

	public void setPruebaSelected(Prueba pruebaSelected) {
		this.pruebaSelected = pruebaSelected;
	}

	public boolean isOpCrearPrueba() {
		return opCrearPrueba;
	}

	public void setOpCrearPrueba(boolean opCrearPrueba) {
		this.opCrearPrueba = opCrearPrueba;
	}

	public boolean isOpEditarPrueba() {
		return opEditarPrueba;
	}

	public void setOpEditarPrueba(boolean opEditarPrueba) {
		this.opEditarPrueba = opEditarPrueba;
	}

	public boolean isOpBorrarPrueba() {
		return opBorrarPrueba;
	}

	public void setOpBorrarPrueba(boolean opBorrarPrueba) {
		this.opBorrarPrueba = opBorrarPrueba;
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

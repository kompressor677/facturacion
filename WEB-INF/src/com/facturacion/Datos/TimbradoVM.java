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


import com.facturacion.modelo.Timbrado;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class TimbradoVM extends TemplateViewModelLocal {
	
	private List<Object[]> lTimbrados; 
	private List<Object[]> lTimbradosOri;
	private Timbrado timbradoSelected;
	
	private boolean opCrearTimbrado;
	private boolean opEditarTimbrado;
	private boolean opBorrarTimbrado;
	
	
	@Init(superclass = true)
	public void initTimbradoVM() {

		cargarTimbrados();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeTimbradoVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearTimbrado = this.operacionHabilitada(ParamsLocal.OP_CREAR_TIMBRADO);
		this.opEditarTimbrado = this.operacionHabilitada(ParamsLocal.OP_EDITAR_TIMBRADO);
		this.opBorrarTimbrado = this.operacionHabilitada(ParamsLocal.OP_BORRAR_TIMBRADO);
	
		
	}
	

	private void cargarTimbrados() {

		String sql = this.um.getSql("listaTimbrados.sql");
		this.lTimbrados = this.reg.sqlNativo(sql);
		this.lTimbradosOri = this.lTimbrados;
		
	}
	
	//seccion filtro 
	
	private String filtroColumns[];
	
	private void inicializarFiltros(){
		
		this.filtroColumns = new String[7]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el modelo
		
		for (int i = 0 ; i<this.filtroColumns.length; i++) {
			
			this.filtroColumns[i] = "";
			
		}
		
	}
	
	@Command
	@NotifyChange("lTimbrados")
	public void filtrarTimbrado() {

		this.lTimbrados = this.filtrarListaObject(this.filtroColumns, this.lTimbradosOri);

	}
	
	//fin seccion 
	
	//seccion modal
	
	private Window modal;
	private boolean editar = false;

	@Command
	public void modalTimbradosAgregar() {

		if(!this.opCrearTimbrado)
			return;

		this.editar = false;
		modalTimbrados(-1);

	}

	@Command
	public void modalTimbrados(@BindingParam("timbradoid") long timbradoid) {

		if (timbradoid != -1) {

			if(!this.opEditarTimbrado)
				return;
			
			this.timbradoSelected = this.reg.getObjectById(Timbrado.class.getName(), timbradoid);
			this.editar = true;

		} else {
			
			timbradoSelected = new Timbrado();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/timbradosModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lTimbrados")
	public void guardar() {

		this.save(timbradoSelected);

		this.timbradoSelected = null;

		this.cargarTimbrados();

		this.modal.detach();
		
		if (editar) {
			
			Notification.show("El Timbrado fue Actualizado.");
			this.editar = false;
		}else {
			
			Notification.show("El Timbrado fue agregado.");
		}
		
		

	}

	
	//fin modal
	
	@Command
	public void borrarTimbradoConfirmacion(@BindingParam("timbradoid") Long timbradoid) {
		
		if (!this.opBorrarTimbrado)
			return;
		
		Timbrado timbrados = this.reg.getObjectById(Timbrado.class.getName(), timbradoid);
		
		EventListener event = new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				
				if (evt.getName().equals(Messagebox.ON_YES)) {
					
					borrarTimbrado(timbrados);
					
				}
				
			}

		};
		
		this.mensajeEliminar("El timbrado sera eliminado. \n Continuar?", event);
	}
	
	
	private void borrarTimbrado (Timbrado timbrado) {
		
		this.reg.deleteObject(timbrado);
		
		this.cargarTimbrados();
		
		BindUtils.postNotifyChange(null,null,this,"lTimbrados");
		
	}

	public List<Object[]> getlTimbrados() {
		return lTimbrados;
	}

	public void setlTimbrados(List<Object[]> lTimbrados) {
		this.lTimbrados = lTimbrados;
	}

	public Timbrado getTimbradoSelected() {
		return timbradoSelected;
	}

	public void setTimbradoSelected(Timbrado timbradoSelected) {
		this.timbradoSelected = timbradoSelected;
	}

	public boolean isOpCrearTimbrado() {
		return opCrearTimbrado;
	}

	public void setOpCrearTimbrado(boolean opCrearTimbrado) {
		this.opCrearTimbrado = opCrearTimbrado;
	}

	public boolean isOpEditarTimbrado() {
		return opEditarTimbrado;
	}

	public void setOpEditarTimbrado(boolean opEditarTimbrado) {
		this.opEditarTimbrado = opEditarTimbrado;
	}

	public boolean isOpBorrarTimbrado() {
		return opBorrarTimbrado;
	}

	public void setOpBorrarTimbrado(boolean opBorrarTimbrado) {
		this.opBorrarTimbrado = opBorrarTimbrado;
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

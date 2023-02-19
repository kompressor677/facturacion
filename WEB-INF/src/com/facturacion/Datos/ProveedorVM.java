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


import com.facturacion.modelo.Proveedor;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class ProveedorVM extends TemplateViewModelLocal {
	
	private List<Object[]> lProveedores; 
	private List<Object[]> lProveedoresOri;
	private Proveedor proveedorSelected;
	
	private boolean opCrearProveedor;
	private boolean opEditarProveedor;
	private boolean opBorrarProveedor;
	
	
	@Init(superclass = true)
	public void initProveedorVM() {

		cargarProveedores();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeProveedorVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearProveedor = this.operacionHabilitada(ParamsLocal.OP_CREAR_PROVEEDOR);
		this.opEditarProveedor = this.operacionHabilitada(ParamsLocal.OP_EDITAR_PROVEEDOR);
		this.opBorrarProveedor = this.operacionHabilitada(ParamsLocal.OP_BORRAR_PROVEEDOR);
	
		
	}
	

	private void cargarProveedores() {

		String sql = this.um.getSql("listaProveedores.sql");
		this.lProveedores = this.reg.sqlNativo(sql);
		this.lProveedoresOri = this.lProveedores;
		
	}
	
	//seccion filtro 
	
	private String filtroColumns[];
	
	private void inicializarFiltros(){
		
		this.filtroColumns = new String[3]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el modelo
		
		for (int i = 0 ; i<this.filtroColumns.length; i++) {
			
			this.filtroColumns[i] = "";
			
		}
		
	}
	
	@Command
	@NotifyChange("lProveedores")
	public void filtrarProveedor() {

		this.lProveedores = this.filtrarListaObject(this.filtroColumns, this.lProveedoresOri);

	}
	
	//fin seccion 
	
	//seccion modal
	
	private Window modal;
	private boolean editar = false;

	@Command
	public void modalProveedoresAgregar() {

		if(!this.opCrearProveedor)
			return;

		this.editar = false;
		modalProveedores(-1);

	}

	@Command
	public void modalProveedores(@BindingParam("proveedorid") long proveedorid) {

		if (proveedorid != -1) {

			if(!this.opEditarProveedor)
				return;
			
			this.proveedorSelected = this.reg.getObjectById(Proveedor.class.getName(), proveedorid);
			this.editar = true;

		} else {
			
			proveedorSelected = new Proveedor();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/proveedoresModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lProveedores")
	public void guardar() {

		this.save(proveedorSelected);

		this.proveedorSelected = null;

		this.cargarProveedores();

		this.modal.detach();
		
		if (editar) {
			
			Notification.show("El Proveedor fue Actualizado.");
			this.editar = false;
		}else {
			
			Notification.show("El Proveedor fue agregado.");
		}
		
		

	}

	
	//fin modal
	
	@Command
	public void borrarProveedorConfirmacion(@BindingParam("proveedorid") Long proveedorid) {
		
		if (!this.opBorrarProveedor)
			return;
		
		Proveedor proveedores = this.reg.getObjectById(Proveedor.class.getName(), proveedorid);
		
		EventListener event = new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				
				if (evt.getName().equals(Messagebox.ON_YES)) {
					
					borrarProveedor(proveedores);
					
				}
				
			}

		};
		
		this.mensajeEliminar("El proveedor sera eliminado. \n Continuar?", event);
	}
	
	
	private void borrarProveedor (Proveedor proveedor) {
		
		this.reg.deleteObject(proveedor);
		
		this.cargarProveedores();
		
		BindUtils.postNotifyChange(null,null,this,"lProveedores");
		
	}

	public List<Object[]> getlProveedores() {
		return lProveedores;
	}

	public void setlProveedores(List<Object[]> lProveedores) {
		this.lProveedores = lProveedores;
	}

	public Proveedor getProveedorSelected() {
		return proveedorSelected;
	}

	public void setProveedorSelected(Proveedor proveedorSelected) {
		this.proveedorSelected = proveedorSelected;
	}

	public boolean isOpCrearProveedor() {
		return opCrearProveedor;
	}

	public void setOpCrearProveedor(boolean opCrearProveedor) {
		this.opCrearProveedor = opCrearProveedor;
	}

	public boolean isOpEditarProveedor() {
		return opEditarProveedor;
	}

	public void setOpEditarProveedor(boolean opEditarProveedor) {
		this.opEditarProveedor = opEditarProveedor;
	}

	public boolean isOpBorrarProveedor() {
		return opBorrarProveedor;
	}

	public void setOpBorrarProveedor(boolean opBorrarProveedor) {
		this.opBorrarProveedor = opBorrarProveedor;
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

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

import com.facturacion.modelo.Marca;
import com.facturacion.modelo.Marca;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class MarcaVM extends TemplateViewModelLocal {
	
	private List<Object[]> lMarcas; 
	private List<Object[]> lMarcasOri;
	private Marca marcaSelected;
	
	private boolean opCrearMarca;
	private boolean opEditarMarca;
	private boolean opBorrarMarca;
	
	
	@Init(superclass = true)
	public void initMarcaVM() {

		cargarMarcas();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeMarcaVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearMarca = this.operacionHabilitada(ParamsLocal.OP_CREAR_MARCA);
		this.opEditarMarca = this.operacionHabilitada(ParamsLocal.OP_EDITAR_MARCA);
		this.opBorrarMarca = this.operacionHabilitada(ParamsLocal.OP_BORRAR_MARCA);
	
		
	}
	

	private void cargarMarcas() {

		String sql = this.um.getSql("listaMarcas.sql");
		this.lMarcas = this.reg.sqlNativo(sql);
		this.lMarcasOri = this.lMarcas;
		
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
	@NotifyChange("lMarcas")
	public void filtrarMarca() {

		this.lMarcas = this.filtrarListaObject(this.filtroColumns, this.lMarcasOri);

	}
	
	//fin seccion 
	
	//seccion modal
	
	private Window modal;
	private boolean editar = false;

	@Command
	public void modalMarcasAgregar() {

		if(!this.opCrearMarca)
			return;

		this.editar = false;
		modalMarcas(-1);

	}

	@Command
	public void modalMarcas(@BindingParam("marcaid") long marcaid) {

		if (marcaid != -1) {

			if(!this.opEditarMarca)
				return;
			
			this.marcaSelected = this.reg.getObjectById(Marca.class.getName(), marcaid);
			this.editar = true;

		} else {
			
			marcaSelected = new Marca();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/marcasModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lMarcas")
	public void guardar() {

		this.save(marcaSelected);

		this.marcaSelected = null;

		this.cargarMarcas();

		this.modal.detach();
		
		if (editar) {
			
			Notification.show("La marca fue actualizada.");
			this.editar = false;
		}else {
			
			Notification.show("La marca fue agregada.");
		}
		
		

	}

	
	//fin modal
	
	@Command
	public void borrarMarcaConfirmacion(@BindingParam("marcaid") Long marcaid) {
		
		if (!this.opBorrarMarca)
			return;
		
		Marca marcas = this.reg.getObjectById(Marca.class.getName(), marcaid);
		
		EventListener event = new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				
				if (evt.getName().equals(Messagebox.ON_YES)) {
					
					borrarMarca(marcas);
					
				}
				
			}

		};
		
		this.mensajeEliminar("La marca sera eliminada. \n Continuar?", event);
	}
	
	
	private void borrarMarca(Marca marca) {
		
		this.reg.deleteObject(marca);
		
		this.cargarMarcas();
		
		BindUtils.postNotifyChange(null,null,this,"lMarcas");
		
	}

	public List<Object[]> getlMarcas() {
		return lMarcas;
	}

	public void setlMarcas(List<Object[]> lMarcas) {
		this.lMarcas = lMarcas;
	}

	public Marca getMarcaSelected() {
		return marcaSelected;
	}

	public void setMarcaSelected(Marca marcaSelected) {
		this.marcaSelected = marcaSelected;
	}

	public boolean isOpCrearMarca() {
		return opCrearMarca;
	}

	public void setOpCrearMarca(boolean opCrearMarca) {
		this.opCrearMarca = opCrearMarca;
	}

	public boolean isOpEditarMarca() {
		return opEditarMarca;
	}

	public void setOpEditarMarca(boolean opEditarMarca) {
		this.opEditarMarca = opEditarMarca;
	}

	public boolean isOpBorrarMarca() {
		return opBorrarMarca;
	}

	public void setOpBorrarMarca(boolean opBorrarMarca) {
		this.opBorrarMarca = opBorrarMarca;
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

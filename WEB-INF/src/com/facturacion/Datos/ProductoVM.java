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

import com.facturacion.modelo.Producto;
import com.facturacion.modelo.Producto;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class ProductoVM extends TemplateViewModelLocal {
	
	private List<Object[]> lProductos; 
	private List<Object[]> lProductosOri;
	private Producto productoSelected;
	
	private boolean opCrearProducto;
	private boolean opEditarProducto;
	private boolean opBorrarProducto;
	
	
	@Init(superclass = true)
	public void initProductoVM() {

		cargarProductos();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeProductoVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearProducto = this.operacionHabilitada(ParamsLocal.OP_CREAR_PRODUCTO);
		this.opEditarProducto = this.operacionHabilitada(ParamsLocal.OP_EDITAR_PRODUCTO);
		this.opBorrarProducto = this.operacionHabilitada(ParamsLocal.OP_BORRAR_PRODUCTO);
	
		
	}
	

	private void cargarProductos() {

		String sql = this.um.getSql("listaProductos.sql");
		this.lProductos = this.reg.sqlNativo(sql);
		this.lProductosOri = this.lProductos;
		
	}
	
	//seccion filtro 
	
	private String filtroColumns[];
	
	private void inicializarFiltros(){
		
		this.filtroColumns = new String[5]; // se debe de iniciar el filtro deacuerdo a la cantidad declarada en el modelo
		
		for (int i = 0 ; i<this.filtroColumns.length; i++) {
			
			this.filtroColumns[i] = "";
			
		}
		
	}
	
	@Command
	@NotifyChange("lProductos")
	public void filtrarProductos() {

		this.lProductos = this.filtrarListaObject(this.filtroColumns, this.lProductosOri);

	}
	
	//fin seccion 
	
	//seccion modal
	
	private Window modal;
	private boolean editar = false;

	@Command
	public void modalProductosAgregar() {

		if(!this.opCrearProducto)
			return;

		this.editar = false;
		modalProductos(-1);

	}

	@Command
	public void modalProductos(@BindingParam("productoid") long productoid) {

		if (productoid != -1) {

			if(!this.opEditarProducto)
				return;
			
			this.productoSelected = this.reg.getObjectById(Producto.class.getName(), productoid);
			this.editar = true;

		} else {
			
			productoSelected = new Producto();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/productosModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lProductos")
	public void guardar() {

		this.save(productoSelected);

		this.productoSelected = null;

		this.cargarProductos();

		this.modal.detach();
		
		if (editar) {
			
			Notification.show("El producto fue actualizado.");
			this.editar = false;
		}else {
			
			Notification.show("El producto fue agregado.");
		}
		
		

	}

	
	//fin modal
	
	@Command
	public void borrarProductoConfirmacion(@BindingParam("productoid") Long productoid) {
		
		if (!this.opBorrarProducto)
			return;
		
		Producto productos = this.reg.getObjectById(Producto.class.getName(), productoid);
		
		EventListener event = new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				
				if (evt.getName().equals(Messagebox.ON_YES)) {
					
					borrarProducto(productos);
					
				}
				
			}

		};
		
		this.mensajeEliminar("El producto sera eliminado. \n Continuar?", event);
	}
	
	
	private void borrarProducto(Producto producto) {
		
		this.reg.deleteObject(producto);
		
		this.cargarProductos();
		
		BindUtils.postNotifyChange(null,null,this,"lProductos");
		
	}

	public List<Object[]> getlProductos() {
		return lProductos;
	}

	public void setlProductos(List<Object[]> lProductos) {
		this.lProductos = lProductos;
	}

	public Producto getProductoSelected() {
		return productoSelected;
	}

	public void setProductoSelected(Producto productoSelected) {
		this.productoSelected = productoSelected;
	}

	public boolean isOpCrearProducto() {
		return opCrearProducto;
	}

	public void setOpCrearProducto(boolean opCrearProducto) {
		this.opCrearProducto = opCrearProducto;
	}

	public boolean isOpEditarProducto() {
		return opEditarProducto;
	}

	public void setOpEditarProducto(boolean opEditarProducto) {
		this.opEditarProducto = opEditarProducto;
	}

	public boolean isOpBorrarProducto() {
		return opBorrarProducto;
	}

	public void setOpBorrarProducto(boolean opBorrarProducto) {
		this.opBorrarProducto = opBorrarProducto;
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

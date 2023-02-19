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


import com.facturacion.modelo.Cliente;
import com.facturacion.util.ParamsLocal;
import com.facturacion.util.TemplateViewModelLocal;

public class ClienteVM extends TemplateViewModelLocal {
	
	private List<Object[]> lClientes; 
	private List<Object[]> lClientesOri;
	private Cliente clienteSelected;
	
	private boolean opCrearCliente;
	private boolean opEditarCliente;
	private boolean opBorrarCliente;
	
	
	@Init(superclass = true)
	public void initClienteVM() {

		cargarClientes();
		inicializarFiltros();

	}

	@AfterCompose(superclass = true)
	public void afterComposeClienteVM() {

	}
	
	@Override
	protected void inicializarOperaciones() {
		
		this.opCrearCliente = this.operacionHabilitada(ParamsLocal.OP_CREAR_CLIENTE);
		this.opEditarCliente = this.operacionHabilitada(ParamsLocal.OP_EDITAR_CLIENTE);
		this.opBorrarCliente = this.operacionHabilitada(ParamsLocal.OP_BORRAR_CLIENTE);
	
		
	}
	

	private void cargarClientes() {

		String sql = this.um.getSql("listaClienteS.sql");
		this.lClientes = this.reg.sqlNativo(sql);
		this.lClientesOri = this.lClientes;
		
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
	@NotifyChange("lClientes")
	public void filtrarCliente() {

		this.lClientes = this.filtrarListaObject(this.filtroColumns, this.lClientesOri);

	}
	
	//fin seccion 
	
	//seccion modal
	
	private Window modal;
	private boolean editar = false;

	@Command
	public void modalClientesAgregar() {

		if(!this.opCrearCliente)
			return;

		this.editar = false;
		modalClientes(-1);

	}

	@Command
	public void modalClientes(@BindingParam("clienteid") long clienteid) {

		if (clienteid != -1) {

			if(!this.opEditarCliente)
				return;
			
			this.clienteSelected = this.reg.getObjectById(Cliente.class.getName(), clienteid);
			this.editar = true;

		} else {
			
			clienteSelected = new Cliente();

		}

		modal = (Window) Executions.createComponents("/sistema/zul/Datos/clientesModal.zul", this.mainComponent,
				null);
		Selectors.wireComponents(modal, this, false);
		modal.doModal();

	}

	@Command
	@NotifyChange("lClientes")
	public void guardar() {

		this.save(clienteSelected);

		this.clienteSelected = null;

		this.cargarClientes();

		this.modal.detach();
		
		if (editar) {
			
			Notification.show("El Cliente fue Actualizado.");
			this.editar = false;
		}else {
			
			Notification.show("El Cliente fue agregado.");
		}
		
		

	}

	
	//fin modal
	
	@Command
	public void borrarClienteConfirmacion(@BindingParam("clienteid") Long clienteid) {
		
		if (!this.opBorrarCliente)
			return;
		
		Cliente clientes = this.reg.getObjectById(Cliente.class.getName(), clienteid);
		
		EventListener event = new EventListener () {

			@Override
			public void onEvent(Event evt) throws Exception {
				
				if (evt.getName().equals(Messagebox.ON_YES)) {
					
					borrarCliente(clientes);
					
				}
				
			}

		};
		
		this.mensajeEliminar("El clientes sera eliminado. \n Continuar?", event);
	}
	
	
	private void borrarCliente (Cliente cliente) {
		
		this.reg.deleteObject(cliente);
		
		this.cargarClientes();
		
		BindUtils.postNotifyChange(null,null,this,"lClientes");
		
	}

	public List<Object[]> getlClientes() {
		return lClientes;
	}

	public void setlClientes(List<Object[]> lClientes) {
		this.lClientes = lClientes;
	}

	public Cliente getClienteSelected() {
		return clienteSelected;
	}

	public void setClienteSelected(Cliente clienteSelected) {
		this.clienteSelected = clienteSelected;
	}

	public boolean isOpCrearCliente() {
		return opCrearCliente;
	}

	public void setOpCrearCliente(boolean opCrearCliente) {
		this.opCrearCliente = opCrearCliente;
	}

	public boolean isOpEditarCliente() {
		return opEditarCliente;
	}

	public void setOpEditarCliente(boolean opEditarCliente) {
		this.opEditarCliente = opEditarCliente;
	}

	public boolean isOpBorrarCliente() {
		return opBorrarCliente;
	}

	public void setOpBorrarCliente(boolean opBorrarCliente) {
		this.opBorrarCliente = opBorrarCliente;
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

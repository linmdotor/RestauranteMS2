/**
 * 
 * Despachador de vistas
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.imp;

import presentacion.controlador.Dispatcher;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;
import presentacion.ventanas.VentanaCorrecto;
import presentacion.ventanas.VentanaError;
import presentacion.ventanas.VentanaPrincipal;
import presentacion.ventanas.pedido.VentanaAltaPedido;
import presentacion.ventanas.pedido.VentanaGestionPedidos;
import presentacion.ventanas.producto.VentanaProducto;
import presentacion.ventanas.productosdeproveedor.VentanaGestionProductosProveedor;
import presentacion.ventanas.proveedor.VentanaProveedor;

public class DispatcherImp extends Dispatcher {

	/*
	 * Las respuestas del comando en ningún caso tienen que ser acciones compuestas.
	 * Generalmente serán una llamada al actualizar de la ventana correspondiente.
	 * 
	 * Si se necesita alguna acción más compleja, se hacrá desde una nueva llamada a comando en la ventana.
	 */
	public void despachaRespuesta(RespuestaCMD respuestaCMD) {

		switch (respuestaCMD.getEvento()) {
	
			case INICIAR_VISTA_PRINCIPAL:
				VentanaPrincipal.obtenerInstancia().setVisible(true);
				break;
		
			// PRODUCTO
		
			case INICIAR_VISTA_PRODUCTO:
				VentanaProducto.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;
	
			case MODIFICAR_FORMULARIO_PRODUCTO:
				
				VentanaProducto.obtenerInstancia().modificarFormulario(respuestaCMD.getObjeto());
				break;
	
			case OBTENER_PRODUCTOS:
				VentanaProducto.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;				
	
			case CORRECTO_PRODUCTO:
				new VentanaCorrecto((String) respuestaCMD.getObjeto());
				VentanaProducto.obtenerInstancia().limpiarFormulario();
				break;
				
			// PROVEEDOR
				
			case INICIAR_VISTA_PROVEEDOR:
				VentanaProveedor.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;
	
			case MODIFICAR_FORMULARIO_PROVEEDOR:
				VentanaProveedor.obtenerInstancia().modificarFormulario(respuestaCMD.getObjeto());
				break;
			
			case OBTENER_PROVEEDORES:
				VentanaProveedor.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;				
	
			case CORRECTO_PROVEEDOR:
				new VentanaCorrecto((String) respuestaCMD.getObjeto());
				VentanaProveedor.obtenerInstancia().limpiarFormulario();
				break;
				
			// PRODUCTOS DE PROVEEDOR
				
			case INICIAR_VISTA_PRODUCTOS_DE_PROVEEDOR:
				VentanaGestionProductosProveedor.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;
				
			case MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR:
				VentanaGestionProductosProveedor.obtenerInstancia().cambiarPrecio(respuestaCMD.getObjeto());
				break;
			
			case OBTENER_PRODUCTOS_PROVEEDOR:
				VentanaGestionProductosProveedor.obtenerInstancia().rellenarTablaProductosActuales(respuestaCMD.getObjeto());
				break;
				
			case OBTENER_PRODUCTOS_TOTALES:
				VentanaGestionProductosProveedor.obtenerInstancia().rellenarTablaProductosTotales(respuestaCMD.getObjeto());
				break;
				
			case CORRECTO_PRODUCTO_PROVEEDOR:
				new VentanaCorrecto((String) respuestaCMD.getObjeto());
				VentanaGestionProductosProveedor.obtenerInstancia().limpiarFormulario();
				break;
				
			// GESTIÓN DE PEDIDOS
				
			case INICIAR_VISTA_PEDIDO:
				VentanaGestionPedidos.obtenerInstancia().actualizar();
				break;	
				
			case OBTENER_PROVEEDORES_DISPONIBLES:
				VentanaGestionPedidos.obtenerInstancia().actualizarListaProveedores(respuestaCMD.getObjeto());
				break; 
				
			case OBTENER_PEDIDOS:
				VentanaGestionPedidos.obtenerInstancia().rellenarTablaPedidos(respuestaCMD.getObjeto());
				break;
				
			case OBTENER_PRODUCTOS_PEDIDO:
				VentanaGestionPedidos.obtenerInstancia().actualizarTablaProductos(respuestaCMD.getObjeto());
				break;
				
			// NUEVO PEDIDO	
				
			case INICIAR_VISTA_ALTA_PEDIDO:
				VentanaAltaPedido.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;
				
			case OBTENER_PRODUCTOS_PROVEEDOR_PEDIDO:
				VentanaAltaPedido.obtenerInstancia().rellenarTablaProductosProveedor(respuestaCMD.getObjeto());
				break;
				
			case ANADIR_PRODUCTO_A_PEDIDO:
				VentanaAltaPedido.anadirProducto(respuestaCMD.getObjeto());
				break;
				
			case QUITAR_PRODUCTO_DE_PEDIDO: 
				VentanaAltaPedido.quitarProducto(respuestaCMD.getObjeto());
				break;
			
			case MODIFICAR_CANTIDAD_PRODUCTO_DE_PEDIDO: 
				VentanaAltaPedido.modificarCantidadProducto(respuestaCMD.getObjeto());
				break;
				
			case TERMINAR_PEDIDO:
				new VentanaCorrecto((String) respuestaCMD.getObjeto());
				VentanaGestionPedidos.obtenerInstancia().actualizar();			
				break;
			
			
				
			// GENERICOS
			
			case CORRECTO:
				new VentanaCorrecto((String) respuestaCMD.getObjeto());
				break;
	
			case ERROR:
				new VentanaError((String) respuestaCMD.getObjeto());
				break;
				
			default:
				new VentanaError("Lo sentimos, hay un error . Vuelva a comprobar la validez de los datos.");

		}

	}

}

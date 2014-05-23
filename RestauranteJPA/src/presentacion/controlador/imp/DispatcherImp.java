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
import presentacion.ventanas.productosDeProveedor.VentanaGestionProductosProveedor;
import presentacion.ventanas.proveedor.VentanaProveedor;

public class DispatcherImp extends Dispatcher {

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
				
			// PEDIDO
				
			case INICIAR_VISTA_PEDIDO:
				VentanaGestionPedidos.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;	
				
			case INICIAR_VISTA_ALTA_PEDIDO:
				VentanaAltaPedido.obtenerInstancia().actualizar(respuestaCMD.getObjeto());
				break;
				
			case OBTENER_PROVEEDOR_PRODUCTO:
				VentanaAltaPedido.obtenerInstancia().actualizarProveedor(respuestaCMD.getObjeto());
				break; 
				
			case MODIFICAR_FORMULARIO_PEDIDO:
				VentanaGestionPedidos.obtenerInstancia().modificarFormulario(respuestaCMD.getObjeto());
				break;
				
			case RELLENAR_TB_PRODUCTOS_PEDIDO:
				VentanaGestionPedidos.obtenerInstancia().actualizarTablaProductos(respuestaCMD.getObjeto());
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

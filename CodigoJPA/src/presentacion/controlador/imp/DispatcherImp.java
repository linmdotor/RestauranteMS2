/**
 * 
 * Despachador de vistas
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.imp;

import presentacion.controlador.Dispatcher;
import presentacion.controlador.RespuestaCMD;
import presentacion.ventanas.VentanaCorrecto;
import presentacion.ventanas.VentanaError;
import presentacion.ventanas.pedido.VentanaGestionPedidos;
import presentacion.ventanas.producto.VentanaProducto;
import presentacion.ventanas.productosDeProveedor.VentanaGestionProductosProveedor;
import presentacion.ventanas.proveedor.VentanaProveedor;

public class DispatcherImp extends Dispatcher {

	private VentanaGestionPedidos vistaPedido;
	private VentanaProducto vistaProducto;
	private VentanaProveedor vistaProveedor;
	private VentanaGestionProductosProveedor vistaProductosDeProveedor;

	public DispatcherImp() {

		vistaProducto = new VentanaProducto(null);
		vistaProveedor = new VentanaProveedor(null);		
		vistaProductosDeProveedor = new VentanaGestionProductosProveedor(null);
		vistaPedido = new VentanaGestionPedidos(null);
	}

	public void despachaRespuesta(RespuestaCMD respuestaCMD) {

		switch (respuestaCMD.getEvento()) {
	
			// PRODUCTO
		
			case INICIAR_VISTA_PRODUCTO:
				vistaProducto.actualizar(respuestaCMD.getObjeto());
				vistaProducto.setVisible(true);
				break;
	
			case MODIFICAR_FORMULARIO_PRODUCTO:
				
				vistaProducto.modificarFormulario(respuestaCMD.getObjeto());
				break;
	
			case OBTENER_PRODUCTOS:
				vistaProducto.actualizar(respuestaCMD.getObjeto());
				break;				
	
			case CORRECTO_PRODUCTO:
				new VentanaCorrecto((String) respuestaCMD.getObjeto());
				vistaProducto.limpiarFormulario();
				break;
				
			// PROVEEDOR
				
			case INICIAR_VISTA_PROVEEDOR:
				vistaProveedor.actualizar(respuestaCMD.getObjeto());
				vistaProveedor.setVisible(true);
				break;
	
			case MODIFICAR_FORMULARIO_PROVEEDOR:
				vistaProveedor.modificarFormulario(respuestaCMD.getObjeto());
				break;
	
			
			case OBTENER_PROVEEDORES:
				vistaProveedor.actualizar(respuestaCMD.getObjeto());
				break;				
	
			case CORRECTO_PROVEEDOR:
				new VentanaCorrecto((String) respuestaCMD.getObjeto());
				vistaProveedor.limpiarFormulario();
				break;
				
			// PRODUCTOS DE PROVEEDOR
				
			case INICIAR_VISTA_PRODUCTOS_DE_PROVEEDOR:
				vistaProveedor.actualizar(respuestaCMD.getObjeto());
				vistaProveedor.setVisible(true);
				break;	
			
			case INICIAR_VISTA_PEDIDO:
				vistaPedido.actualizar(respuestaCMD.getObjeto());
				vistaPedido.setVisible(true);
				break;	
			case MODIFICAR_FORMULARIO_PEDIDO:
				vistaPedido.modificarFormulario(respuestaCMD.getObjeto());
				break;
			case RELLENAR_TB_PRODUCTOS_PEDIDO:
				vistaPedido.actualizarTablaProductos(respuestaCMD.getObjeto());
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

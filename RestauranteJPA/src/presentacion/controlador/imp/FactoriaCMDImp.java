/**
 * 
 * Factoria de Comandos
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.imp;

import java.util.HashMap;
import java.util.Map;

import presentacion.controlador.ApplicationController;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.FactoriaCMD;
import presentacion.controlador.comando.CMDIniciarVistaPrincipal;
import presentacion.controlador.comando.pedido.CMDIniciarVistaPedido;
import presentacion.controlador.comando.pedido.CMDModificarFormularioPedido;
import presentacion.controlador.comando.pedido.CMDRellenarTBProductosPedido;
import presentacion.controlador.comando.producto.CMDAltaProducto;
import presentacion.controlador.comando.producto.CMDBajaProducto;
import presentacion.controlador.comando.producto.CMDIniciarVistaProducto;
import presentacion.controlador.comando.producto.CMDModificarFormularioProducto;
import presentacion.controlador.comando.producto.CMDModificarProducto;
import presentacion.controlador.comando.producto.CMDObtenerProductos;
import presentacion.controlador.comando.productosDeProveedor.CMDAnadirProductoProveedor;
import presentacion.controlador.comando.productosDeProveedor.CMDEliminarProductoProveedor;
import presentacion.controlador.comando.productosDeProveedor.CMDIniciarVistaProductosDeProveedor;
import presentacion.controlador.comando.productosDeProveedor.CMDModificarFormularioProductoProveedor;
import presentacion.controlador.comando.productosDeProveedor.CMDModificarProductoProveedor;
import presentacion.controlador.comando.productosDeProveedor.CMDObtenerProductosProveedor;
import presentacion.controlador.comando.productosDeProveedor.CMDObtenerProductosTotales;
import presentacion.controlador.comando.proveedor.CMDAltaProveedor;
import presentacion.controlador.comando.proveedor.CMDBajaProveedor;
import presentacion.controlador.comando.proveedor.CMDIniciarVistaProveedor;
import presentacion.controlador.comando.proveedor.CMDModificarFormularioProveedor;
import presentacion.controlador.comando.proveedor.CMDModificarProveedor;
import presentacion.controlador.comando.proveedor.CMDObtenerProveedor;

public class FactoriaCMDImp extends FactoriaCMD {

	private Map<EnumComandos, CMD> map_cmd;

	public FactoriaCMDImp() {

		map_cmd = new HashMap<EnumComandos, CMD>();

		map_cmd.put(EnumComandos.INICIAR_VISTA_PRINCIPAL, new CMDIniciarVistaPrincipal());
		
		// COMANDOS PRODUCTO
		
		map_cmd.put(EnumComandos.ALTA_PRODUCTO, new CMDAltaProducto());
		map_cmd.put(EnumComandos.BAJA_PRODUCTO, new CMDBajaProducto());
		map_cmd.put(EnumComandos.MODIFICAR_PRODUCTO, new CMDModificarProducto());
		map_cmd.put(EnumComandos.OBTENER_PRODUCTOS, new CMDObtenerProductos());
		map_cmd.put(EnumComandos.INICIAR_VISTA_PRODUCTO, new CMDIniciarVistaProducto());
		map_cmd.put(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO, new CMDModificarFormularioProducto());
		
		// COMANDOS PROVEEDOR
		
		map_cmd.put(EnumComandos.ALTA_PROVEEDOR, new CMDAltaProveedor());
		map_cmd.put(EnumComandos.BAJA_PROVEEDOR, new CMDBajaProveedor());
		map_cmd.put(EnumComandos.MODIFICAR_PROVEEDOR, new CMDModificarProveedor());
		map_cmd.put(EnumComandos.OBTENER_PROVEEDORES, new CMDObtenerProveedor());
		map_cmd.put(EnumComandos.INICIAR_VISTA_PROVEEDOR, new CMDIniciarVistaProveedor());
		map_cmd.put(EnumComandos.MODIFICAR_FORMULARIO_PROVEEDOR, new CMDModificarFormularioProveedor());
		
		// PRODUCTOS DE PROVEEDOR
		
		map_cmd.put(EnumComandos.ANADIR_PRODUCTO_PROVEEDOR, new CMDAnadirProductoProveedor());
		map_cmd.put(EnumComandos.ELIMINAR_PRODUCTO_PROVEEDOR, new CMDEliminarProductoProveedor());
		map_cmd.put(EnumComandos.MODIFICAR_PRODUCTO_PROVEEDOR, new CMDModificarProductoProveedor());
		map_cmd.put(EnumComandos.INICIAR_VISTA_PRODUCTOS_DE_PROVEEDOR, new CMDIniciarVistaProductosDeProveedor());
		map_cmd.put(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, new CMDObtenerProductosProveedor());
		map_cmd.put(EnumComandos.OBTENER_PRODUCTOS_TOTALES, new CMDObtenerProductosTotales());
		map_cmd.put(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR, new CMDModificarFormularioProductoProveedor());
		
		//COMANDOS PEDIDO
		
		map_cmd.put(EnumComandos.INICIAR_VISTA_PEDIDO, new CMDIniciarVistaPedido());
		map_cmd.put(EnumComandos.MODIFICAR_FORMULARIO_PEDIDO, new CMDModificarFormularioPedido());
		map_cmd.put(EnumComandos.RELLENAR_TB_PRODUCTOS_PEDIDO, new CMDRellenarTBProductosPedido());
		
		
	
	}

	// Metodos

	public CMD generaComando(EnumComandos nombreComando) {

		return map_cmd.get(nombreComando);

	}

}

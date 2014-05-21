
package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.TProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {	
				
		TProductoDeProveedor producto = (TProductoDeProveedor) objeto;
		
		RespuestaCMD respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "No se ha podido cargar el Producto seleccionado");	
				
		try {
			respuestaCMD = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR, FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().obtenerProductosProveedor(producto.getProveedor()).get(producto.getProducto()).getPrecio());
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestaCMD;		
	
	}
	
}

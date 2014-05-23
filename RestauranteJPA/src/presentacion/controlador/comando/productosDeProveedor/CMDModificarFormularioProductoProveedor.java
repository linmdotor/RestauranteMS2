package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.SAProductosDeProveedor;
import negocio.productosdeproveedor.TProductoDeProveedor;
import negocio.productosdeproveedor.ValidarTProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {	

		TProductoDeProveedor producto = (TProductoDeProveedor)objeto;
		
		SAProductosDeProveedor serviciosProductoProveedor = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor();
		RespuestaCMD respuestacomando = null;
				
		try {
			if(new ValidarTProductoDeProveedor().productoCorrecto((TProductoDeProveedor) objeto)) {
				respuestacomando = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR, serviciosProductoProveedor.obtenerProductosProveedor(producto.getProveedor()).get(producto.getProducto()).getPrecio());
			} else {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "No se ha podido cargar el Producto de Proveedor seleccionado");
			}
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestacomando;		
	
	}
	
}

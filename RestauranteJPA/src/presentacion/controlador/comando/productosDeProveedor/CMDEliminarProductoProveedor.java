
package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.productosdeproveedor.SAProductosDeProveedor;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import negocio.productosdeproveedor.transfer.ValidarTProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDEliminarProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		SAProductosDeProveedor serviciosProductoProveedor = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor();
		RespuestaCMD respuestacomando = null;
		
		if(new ValidarTProductoDeProveedor().productoCorrecto((TProductoDeProveedor) objeto)) 
		{
			try {	
				if(serviciosProductoProveedor.bajaProductoProveedor((TProductoDeProveedor)objeto))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO_PROVEEDOR, "Se ha quitado el Producto del Proveedor.");
			} catch (Exception e) {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al quitar el producto del proveedor. Los datos no son válidos.");
		
		return respuestacomando;
	}
}

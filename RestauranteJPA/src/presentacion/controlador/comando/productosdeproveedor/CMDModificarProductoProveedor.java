/**
 * 
 * Comando Modificar Producto
 * 
 * @author Marco Gonzï¿½lez, Juan Carlos * @author Martï¿½nez Dotor, Jesï¿½s * @author Picado ï¿½lvarez, Marï¿½a * @author Rojas Morï¿½n, Amy Alejandra * @author Serrano ï¿½lvarez, Josï¿½ * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.productosdeproveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.SAProductoDeProveedor;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import negocio.productosdeproveedor.transfer.ValidarTProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {

		SAProductoDeProveedor serviciosProductoProveedor = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor();
		RespuestaCMD respuestacomando = null;
		
		if(new ValidarTProductoDeProveedor().productoCorrecto((TProductoDeProveedor) objeto))
		{
			try {
				if(serviciosProductoProveedor.modificarProductoProveedor((TProductoDeProveedor)objeto))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO_PROVEEDOR, "Se ha modificado el Producto del Proveedor.");
				else
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar el Producto del Proveedor. Error al insertar los datos.");
			} catch (Exception e) {
				respuestacomando  = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar producto del proveedor. Los datos no son válidos.");
					
		return respuestacomando;
	}
}

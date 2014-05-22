/**
 * 
 * Comando Modificar Producto
 * 
 * @author Marco Gonz�lez, Juan Carlos * @author Mart�nez Dotor, Jes�s * @author Picado �lvarez, Mar�a * @author Rojas Mor�n, Amy Alejandra * @author Serrano �lvarez, Jos� * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.TProductoDeProveedor;
import negocio.productosdeproveedor.ValidarTProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		RespuestaCMD respuestaCMD  = null;
		try {
			if(new ValidarTProductoDeProveedor().productoCorrecto((TProductoDeProveedor) objeto)) {
				respuestaCMD  = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().modificarProductoProveedor(objeto);
			}
		} catch (Exception e) {
			respuestaCMD  = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return respuestaCMD;
	}
}

/**
 * 
 * Comando Modificar Producto
 * 
 * @author Marco Gonz�lez, Juan Carlos * @author Mart�nez Dotor, Jes�s * @author Picado �lvarez, Mar�a * @author Rojas Mor�n, Amy Alejandra * @author Serrano �lvarez, Jos� * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		return FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().modificarProductoProveedor(objeto);

	}

}

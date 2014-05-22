/**
 * 
 * Comando Añadir Producto
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.*;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProductos implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestacomando = null;
		
		try {
			respuestacomando = new RespuestaCMD(EnumComandos.OBTENER_PRODUCTOS, serviciosProducto.obtenerProductos());
		
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestacomando;

	}
}
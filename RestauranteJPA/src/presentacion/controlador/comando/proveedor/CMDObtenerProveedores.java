/**
 * 
 * Comando Añadir Producto
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.proveedor.SAProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProveedores implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProveedor serviciosproveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestacomando = null;
		
		try {
			respuestacomando = new RespuestaCMD(EnumComandos.OBTENER_PROVEEDORES, serviciosproveedor.obtenerProveedores());
			
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestacomando;

	}

}
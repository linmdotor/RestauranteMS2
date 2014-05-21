/**
 * 
 * Comando Añadir Producto
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.proveedor;

import java.util.List;

import negocio.factoria.FactoriaNegocio;
import negocio.proveedor.Proveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestaComando = null;
		
		try {
			
			List<Proveedor> proveedores = FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedores();
			
			respuestaComando = new RespuestaCMD(EnumComandos.OBTENER_PROVEEDORES, proveedores);
			
		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestaComando;

	}

}
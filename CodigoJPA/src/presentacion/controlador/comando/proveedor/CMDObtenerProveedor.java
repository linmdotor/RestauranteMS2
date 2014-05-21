/**
 * 
 * Comando A�adir Producto
 * 
 * @author Marco Gonz�lez, Juan Carlos * @author Mart�nez Dotor, Jes�s * @author Picado �lvarez, Mar�a * @author Rojas Mor�n, Amy Alejandra * @author Serrano �lvarez, Jos� * @author Vargas Paredes, Jhonny
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
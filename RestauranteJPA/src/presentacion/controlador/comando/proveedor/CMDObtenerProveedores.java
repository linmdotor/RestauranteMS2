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
import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProveedores implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProveedor serviciosproveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestaCMD = null;
		
		try {
			
			List<TProveedor> proveedores = serviciosproveedor.obtenerProveedores();
			
			respuestaCMD = new RespuestaCMD(EnumComandos.OBTENER_PROVEEDORES, proveedores);
			
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestaCMD;

	}

}
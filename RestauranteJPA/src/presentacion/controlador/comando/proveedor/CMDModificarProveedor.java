/**
 * 
 * Comando Modificar Producto
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import negocio.proveedor.ValidarTProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProveedor serviciosProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestaCMD = null;
		
		if(new ValidarTProveedor().proveedorCorrecto((TProveedor) objeto))
		{
			try {
				if (serviciosProveedor.modificarProveedor((TProveedor) objeto))
					respuestaCMD = new RespuestaCMD(EnumComandos.CORRECTO_PROVEEDOR, "Exito modificando el Proveedor.");
				else
					respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar Proveedor. Error al insertar los datos.");
			} catch (Exception e) {
				respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar proveedor. Los datos no son válidos.");
			
		return respuestaCMD;

	}

}

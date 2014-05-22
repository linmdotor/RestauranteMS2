/**
 * 
 * Comando Modificar Producto
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import negocio.producto.ValidarTProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestacomando = null;
		
		if(new ValidarTProducto().productoCorrecto((TProducto) objeto))
		{
			try {
				if (serviciosProducto.modificarProducto((TProducto) objeto))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Exito modificando el Plato.");
				else
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar Producto. Error al insertar los datos.");
			} catch (Exception e) {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar producto. Los datos no son válidos.");
			
		return respuestacomando;

	}

}

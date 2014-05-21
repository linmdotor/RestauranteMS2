/**
 * 
 * Comando Modificar Producto
 * 
 * @author Marco Gonz�lez, Juan Carlos * @author Mart�nez Dotor, Jes�s * @author Picado �lvarez, Mar�a * @author Rojas Mor�n, Amy Alejandra * @author Serrano �lvarez, Jos� * @author Vargas Paredes, Jhonny
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
		RespuestaCMD respuestaComando = null;
		
		if(new ValidarTProducto().productoCorrecto((TProducto) objeto))
		{
			try {
				if (serviciosProducto.modificarProducto((TProducto) objeto) != null)
					respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Exito modificando el Plato.");
				else
					respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar Producto. Error al insertar los datos.");
			} catch (Exception e) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar producto. Los datos no son v�lidos.");
			
		return respuestaComando;

	}

}

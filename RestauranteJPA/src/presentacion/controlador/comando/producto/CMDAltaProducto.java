package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.producto.transfer.TProducto;
import negocio.producto.transfer.ValidarTProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAltaProducto implements CMD {

	// Metodos

	public RespuestaCMD ejecuta(Object objeto) {
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestacomando = null;
		
		if(new ValidarTProducto().productoCorrecto((TProducto) objeto)) {
		
			try {
				if (serviciosProducto.altaProducto((TProducto) objeto))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Se ha añadido el Producto.");
				else
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de alta producto. Error al insertar los datos.");	
			} catch (Exception e) {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}	
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al insertar producto. Los datos no son válidos.");
		
		return respuestacomando;
		
	}
}

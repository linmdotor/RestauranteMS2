package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import negocio.producto.ValidarTProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAltaProducto implements CMD {

	// Metodos

	public RespuestaCMD ejecuta(Object objeto) {
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestaCMD= null;
		
		if(new ValidarTProducto().productoCorrecto((TProducto) objeto)) {
		
			try {
				if (serviciosProducto.altaProducto((TProducto) objeto) != null)
					respuestaCMD = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Se ha añadido el Producto.");
				else
					respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de alta producto. Error al insertar los datos.");	
			} catch (Exception e) {
				respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}	
		}
		
		return respuestaCMD;
		
	}
}

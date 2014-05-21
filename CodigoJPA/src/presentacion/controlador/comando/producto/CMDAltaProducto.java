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
		RespuestaCMD res = null;
		try {
			res = FactoriaNegocio.obtenerInstancia().generaSAProducto().altaProducto(objeto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
		
}

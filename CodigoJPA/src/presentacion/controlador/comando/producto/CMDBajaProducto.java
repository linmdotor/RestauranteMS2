
package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDBajaProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		RespuestaCMD res = null;
		try {
			res = FactoriaNegocio.obtenerInstancia().generaSAProducto().bajaProducto(FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProductos().get((Integer) objeto).getId_producto());
		} catch (Exception e) {
			res = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

}

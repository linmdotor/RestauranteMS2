
package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.RespuestaCMD;

public class CMDBajaProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		RespuestaCMD res = null;
		try {
			res = FactoriaNegocio.obtenerInstancia().generaSAProducto().bajaProducto(FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProductos().get((Integer) objeto).getId_producto());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}

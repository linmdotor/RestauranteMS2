
package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.RespuestaCMD;

public class CMDBajaProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {

		return FactoriaNegocio.obtenerInstancia().generaSAProducto().bajaProducto(FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProductos().get((Integer) objeto).getId_producto());

	}

}

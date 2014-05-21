
package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.RespuestaCMD;

public class CMDA�adirProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {

		return FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().anadirProductoProveedor(objeto);
	}

}

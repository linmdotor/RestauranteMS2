
package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.RespuestaCMD;

public class CMDEliminarProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		RespuestaCMD res = null;
		try {
			res = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().bajaProductoProveedor(objeto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}

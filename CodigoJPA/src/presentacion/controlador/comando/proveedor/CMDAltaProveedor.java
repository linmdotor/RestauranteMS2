package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.RespuestaCMD;

public class CMDAltaProveedor implements CMD {

	// Metodos

	public RespuestaCMD ejecuta(Object objeto) {
		RespuestaCMD res = null;
		try {
			res = FactoriaNegocio.obtenerInstancia().generaSAProveedor().altaProveedor(objeto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

}

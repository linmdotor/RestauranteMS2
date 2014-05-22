package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProductosProveedor implements CMD {
	
	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestaComando = null;
		try {
			respuestaComando = new RespuestaCMD(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().obtenerProductosProveedor(objeto));
		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestaComando;

	}

}

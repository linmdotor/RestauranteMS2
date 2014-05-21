package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProductosProveedor implements CMD {
	
	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().obtenerProductosProveedor(objeto));

		return respuestaComando;

	}

}

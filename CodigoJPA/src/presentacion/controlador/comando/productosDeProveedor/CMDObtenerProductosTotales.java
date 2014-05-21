package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProductosTotales implements CMD {
	
	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.OBTENER_PRODUCTOS_TOTALES, FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProductos());

		return respuestaComando;

	}

}

package presentacion.controlador.comando.productosdeproveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.SAProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProductosProveedor implements CMD {
	
	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProductoDeProveedor serviciosProductosDeProveedor = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor();
		RespuestaCMD respuestaComando = null;
		
		try {
			
			respuestaComando = new RespuestaCMD(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, serviciosProductosDeProveedor.obtenerProductosProveedor((int)objeto));
		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestaComando;

	}

}

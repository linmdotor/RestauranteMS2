package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDIniciarVistaProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestacomando = null;
		
		try {
			respuestacomando = new RespuestaCMD(EnumComandos.INICIAR_VISTA_PRODUCTO, serviciosProducto.obtenerProductos());
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestacomando;

	}
			
}

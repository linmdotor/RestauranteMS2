package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDIniciarVistaAltaPedido implements CMD{
	
	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestacomando = null;
		
		try {
			respuestacomando = new RespuestaCMD(EnumComandos.INICIAR_VISTA_ALTA_PEDIDO, FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProductosDisponibles());
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
		}
	
		return respuestacomando;
	}
}

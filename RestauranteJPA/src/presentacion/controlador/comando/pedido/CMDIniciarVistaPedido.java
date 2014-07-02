package presentacion.controlador.comando.pedido;

import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDIniciarVistaPedido implements CMD 
{
	
	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestaComando = null;
		
		try {
			respuestaComando = new RespuestaCMD(EnumComandos.INICIAR_VISTA_PEDIDO, null);
		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
		}

		return respuestaComando;

	}

}


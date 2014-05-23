package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerPedidos  implements CMD {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;
		
		try {
			respuestaComando = new RespuestaCMD(EnumComandos.OBTENER_PEDIDOS, serviciosPedido.obtenerPedidos());
		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestaComando;
	}

}

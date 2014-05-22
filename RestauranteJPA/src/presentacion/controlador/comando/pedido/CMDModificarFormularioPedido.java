package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioPedido  implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaCMD = null;
		
		try {
			respuestaCMD = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PEDIDO, serviciosPedido.obtenerPedidos().get((Integer) objeto ));
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestaCMD;
			
	}
}

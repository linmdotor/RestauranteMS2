package presentacion.controlador.comando.productosdepedido;

import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.productosdepedido.transfer.ValidarTProductoDePedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDQuitarProductoDePedido  implements CMD {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestacomando = null;
		
		if (new ValidarTProductoDePedido().productoCorrecto((TProductoDePedido)objeto))
			respuestacomando = new RespuestaCMD(EnumComandos.QUITAR_PRODUCTO_DE_PEDIDO, objeto);
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al quitar el producto del pedido. Los datos no son válidos.");
	
		return respuestacomando;
		
	}

}

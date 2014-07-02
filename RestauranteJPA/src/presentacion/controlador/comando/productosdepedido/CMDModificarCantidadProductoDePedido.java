package presentacion.controlador.comando.productosdepedido;

import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.productosdepedido.transfer.ValidarTProductoDePedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarCantidadProductoDePedido  implements CMD {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestacomando = null;
		
		if (new ValidarTProductoDePedido().productoCorrecto((TProductoDePedido)objeto))
			respuestacomando = new RespuestaCMD(EnumComandos.MODIFICAR_CANTIDAD_PRODUCTO_DE_PEDIDO, objeto);
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al añadir el producto al pedido. Los datos no son válidos.");
	
		return respuestacomando;
	}

}

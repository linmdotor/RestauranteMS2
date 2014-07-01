package presentacion.controlador.comando.productosdepedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDQuitarProductoDePedido  implements CMD {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		return new RespuestaCMD(EnumComandos.QUITAR_PRODUCTO_DE_PEDIDO, objeto);
	}

}

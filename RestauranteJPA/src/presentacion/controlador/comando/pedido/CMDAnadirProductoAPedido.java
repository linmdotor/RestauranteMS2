package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAnadirProductoAPedido  implements CMD {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		return new RespuestaCMD(EnumComandos.ANADIR_PRODUCTO_A_PEDIDO, objeto);
	}

}

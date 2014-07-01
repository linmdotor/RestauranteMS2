package presentacion.controlador.comando.productosdepedido;

import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarCantidadProductoDePedido  implements CMD {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		return new RespuestaCMD(EnumComandos.MODIFICAR_CANTIDAD_PRODUCTO_DE_PEDIDO, objeto);
	}

}

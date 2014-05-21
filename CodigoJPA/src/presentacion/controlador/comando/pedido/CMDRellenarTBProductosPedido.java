 package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDRellenarTBProductosPedido implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
	
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaCMD = null;
		
		try {
			respuestaCMD = new RespuestaCMD(EnumComandos.RELLENAR_TB_PRODUCTOS_PEDIDO, serviciosPedido.obtenerPedidoProductos((Integer) objeto ));
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestaCMD;
			
	}
	
}

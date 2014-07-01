 package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdepedido.SAProductoDePedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProductosPedido implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
	
		SAProductoDePedido serviciosPedidoProducto = FactoriaNegocio.obtenerInstancia().generaSAProductosDePedido();
		RespuestaCMD respuestaCMD = null;
		
		try {
			respuestaCMD = new RespuestaCMD(EnumComandos.OBTENER_PRODUCTOS_PEDIDO, serviciosPedidoProducto.obtenerProductosPedido((int) objeto));
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestaCMD;
			
	}
	
}

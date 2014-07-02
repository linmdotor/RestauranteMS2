 package presentacion.controlador.comando.productosdepedido;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.SAProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProductosProveedorPedido implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
	
		SAProductoDeProveedor serviciosproductosproveedor = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor();
		RespuestaCMD respuestacomando = null;
		
		try {
			respuestacomando = new RespuestaCMD(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR_PEDIDO, serviciosproductosproveedor.obtenerProductosProveedor((int) objeto));
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
		}
	
		return respuestacomando;
	}
	
}

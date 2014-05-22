
package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.productosdeproveedor.SAProductosDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDIniciarVistaProductosDeProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
			
		RespuestaCMD respuestaComando = null;
		
		try {
			respuestaComando = new RespuestaCMD(EnumComandos.INICIAR_VISTA_PRODUCTOS_DE_PROVEEDOR, FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedores().get((int) objeto).getId_proveedor());

		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestaComando;

	}

}

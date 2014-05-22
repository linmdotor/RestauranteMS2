
package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.TProductoDeProveedor;
import negocio.productosdeproveedor.ValidarTProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDEliminarProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		RespuestaCMD respuestaCMD = null;
		try {	
				respuestaCMD = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().bajaProductoProveedor(objeto);
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return respuestaCMD;
	}
}

package presentacion.controlador.comando.productosDeProveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.TProductoDeProveedor;
import negocio.productosdeproveedor.ValidarTProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAnadirProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		RespuestaCMD respuestaCMD = null;
		try {
			if(new ValidarTProductoDeProveedor().productoCorrecto((TProductoDeProveedor) objeto)) {	
				respuestaCMD = FactoriaNegocio.obtenerInstancia().generaSAProductosDeProveedor().anadirProductoProveedor(objeto);
			}	
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		return respuestaCMD;
	}
}
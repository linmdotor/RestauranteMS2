package presentacion.controlador.comando.productosdeproveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.productosdeproveedor.SAProductoDeProveedor;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import negocio.productosdeproveedor.transfer.ValidarTProductoDeProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {	

		RespuestaCMD respuestacomando = null;
				
		try {
			if((double)objeto >= 0) {
				respuestacomando = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR, (double)objeto);
			} else {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "No se ha podido cargar el Producto de Proveedor seleccionado");
			}
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestacomando;		
	
	}
	
}
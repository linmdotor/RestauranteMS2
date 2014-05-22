
package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.proveedor.SAProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
				
		SAProveedor serviciosProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestacomando = null;
		
		try {
			
			if(serviciosProveedor.obtenerProveedor(serviciosProveedor.obtenerProveedores().get((int) objeto).getId_proveedor()) != null)
			{
				respuestacomando = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PROVEEDOR, serviciosProveedor.obtenerProveedor(serviciosProveedor.obtenerProveedores().get((int) objeto).getId_proveedor()));
			} else {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "No se ha podido cargar el Proveedor seleccionado");
			}
			
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestacomando;		
	
	}
			
}

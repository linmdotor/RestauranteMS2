
package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.proveedor.SAProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDBajaProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		SAProveedor serviciosProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestacomando = null;
		
		int ID = -1;

		if ((Integer) objeto != -1) 
		{	
			try {
				ID = serviciosProveedor.obtenerProveedores().get((Integer) objeto).getId_proveedor();
				if(serviciosProveedor.bajaProveedor(ID))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PROVEEDOR, "Exito dando de baja Proveedor.");
				else
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de baja el Proveedor.");
				
			} catch (Exception e) {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de baja el proveedor, debe seleccionar un proveedor.");
			
		return respuestacomando;
	}

}

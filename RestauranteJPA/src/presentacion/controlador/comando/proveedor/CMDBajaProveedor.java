
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
		RespuestaCMD respuestaCMD = null;
		
		int ID = -1;

		if ((Integer) objeto != -1) 
		{	
			try {
				ID = serviciosProveedor.obtenerProveedores().get((Integer) objeto).getId_proveedor();
				if(serviciosProveedor.bajaProveedor(ID))
					respuestaCMD = new RespuestaCMD(EnumComandos.CORRECTO_PROVEEDOR, "Exito eliminando Proveedor.");
				else
					respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "Error al eliminar el Proveedor.");
				
			} catch (Exception e) {
				respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "Error al eliminar el proveedor, debe seleccionar un proveedor.");
			
		return respuestaCMD;
	}

}

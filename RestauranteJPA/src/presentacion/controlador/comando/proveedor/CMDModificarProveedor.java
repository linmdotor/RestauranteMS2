
package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.transfer.TProveedor;
import negocio.proveedor.transfer.ValidarTProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProveedor serviciosProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestacomando = null;
		
		if(new ValidarTProveedor().proveedorCorrecto((TProveedor) objeto))
		{
			try {
				if (serviciosProveedor.modificarProveedor((TProveedor) objeto))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PROVEEDOR, "Exito modificando el Proveedor.");
				else
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar Proveedor. Error al insertar los datos.");
			} catch (Exception e) {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar proveedor. Los datos no son v�lidos.");
			
		return respuestacomando;

	}

}

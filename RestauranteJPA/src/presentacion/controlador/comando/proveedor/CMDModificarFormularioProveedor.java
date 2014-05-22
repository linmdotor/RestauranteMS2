
package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
				
		RespuestaCMD respuestaCMD;
		
		try {
			
			if(FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedor(FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedores().get((int) objeto).getId_proveedor()) != null)
			{
				respuestaCMD = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PROVEEDOR, FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedor(FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedores().get((int) objeto).getId_proveedor()));
			} else {
				respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "No se ha podido cargar el Proveedor seleccionado");
			}
			
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestaCMD;		
	
	}
			
}

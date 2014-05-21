
package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestaCMD;
		
		try {
			
			if(FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProducto(FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProductos().get((int) objeto).getId_producto()) != null)
			{
				respuestaCMD = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO, FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProducto(FactoriaNegocio.obtenerInstancia().generaSAProducto().obtenerProductos().get((int) objeto).getId_producto()));
				
			} else {
				respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "No se ha podido cargar el Producto seleccionado");
			}
			
		} catch (Exception e) {
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestaCMD;		
	
	}
	
}


package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestacomando;
		
		try {
			
			if(serviciosProducto.obtenerProducto(serviciosProducto.obtenerProductos().get((int) objeto).getId_producto()) != null)
			{
				respuestacomando = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO, serviciosProducto.obtenerProducto(serviciosProducto.obtenerProductos().get((int) objeto).getId_producto()));				
			} else {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "No se ha podido cargar el Producto seleccionado");
			}
			
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
		return respuestacomando;		
	
	}
	
}

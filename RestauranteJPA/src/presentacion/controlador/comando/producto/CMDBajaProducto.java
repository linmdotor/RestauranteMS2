
package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.producto.transfer.TProducto;
import negocio.producto.transfer.ValidarTProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDBajaProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestacomando = null;
		
		int ID = -1;

		if ((Integer) objeto != -1) 
		{	
			try {
				ID = serviciosProducto.obtenerProductos().get((Integer) objeto).getId_producto();
				if(serviciosProducto.bajaProducto(ID))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Exito eliminando Producto.");
				else
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al eliminar el producto.");
			} catch (Exception e) {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al eliminar el producto, debe seleccionar un producto.");
		
		return respuestacomando;
	}

}

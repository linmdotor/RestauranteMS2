
package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import negocio.producto.ValidarTProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDBajaProducto implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
		SAProducto serviciosProducto = FactoriaNegocio.obtenerInstancia().generaSAProducto();
		RespuestaCMD respuestaCMD = null;
		
		int ID = -1;

		if ((Integer) objeto != -1) 
		{	
			try {
				if(serviciosProducto.bajaProducto((Integer) objeto))
					respuestaCMD = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Exito eliminando Producto.");
				else
					respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "Error al eliminar el producto.");
			} catch (Exception e) {
				respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestaCMD = new RespuestaCMD(EnumComandos.ERROR, "Error al eliminar el producto, debe seleccionar un producto.");
		
		return respuestaCMD;
	}

}

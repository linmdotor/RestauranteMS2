package presentacion.controlador.comando.productosdeproveedor;

import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDModificarFormularioProductoProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {	

		RespuestaCMD respuestacomando = null;
			
		if((double)objeto >= 0) 
		{
			try {
					respuestacomando = new RespuestaCMD(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR, (double)objeto);		 
					
				} catch (Exception e) {
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
					e.printStackTrace();
				}
			}
		else 
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al cargar el formulario. Los datos no son válidos.");
		
	return respuestacomando;		
	
	}
	
}

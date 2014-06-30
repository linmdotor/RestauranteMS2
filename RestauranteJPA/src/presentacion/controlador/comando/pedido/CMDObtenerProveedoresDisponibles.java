package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDObtenerProveedoresDisponibles implements CMD{

	public RespuestaCMD ejecuta(Object objeto) {
		
		RespuestaCMD respuestacomando = null;
		
		try {
			respuestacomando = new RespuestaCMD(EnumComandos.OBTENER_PROVEEDORES_DISPONIBLES, FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedoresDisponibles());
		} catch (Exception e) {
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
		}
	
		return respuestacomando;
	}
}

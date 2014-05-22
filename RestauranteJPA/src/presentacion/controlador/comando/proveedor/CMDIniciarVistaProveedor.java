package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.proveedor.SAProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDIniciarVistaProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {
			
		SAProveedor serviciosProveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestaComando = null;
		
		try {
			respuestaComando = new RespuestaCMD(EnumComandos.INICIAR_VISTA_PROVEEDOR, serviciosProveedor.obtenerProveedores());
		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}

		return respuestaComando;

	}

}

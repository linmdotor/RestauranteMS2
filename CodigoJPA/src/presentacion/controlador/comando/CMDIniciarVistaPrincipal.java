package presentacion.controlador.comando;

import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDIniciarVistaPrincipal implements CMD {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		return  new RespuestaCMD(EnumComandos.INICIAR_VISTA_PRINCIPAL, null);	

	}
	
}

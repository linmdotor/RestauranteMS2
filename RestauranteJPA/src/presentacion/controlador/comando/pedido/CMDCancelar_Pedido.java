package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDCancelar_Pedido implements CMD  {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;
		int ID = -1;
		
		ID = (Integer) objeto;
		
		if (ID != -1) 
		{
			try {
				
				if(serviciosPedido.cancelarPedido(ID)){
					
					respuestaComando = new RespuestaCMD(EnumComandos.CANCELAR_PEDIDO,"Se ha cancelado el Pedido");
					
				}else
					respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al cancelar el pedido.");	
			
			} catch (Exception e) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de cancelar el pedido, debe seleccionar un pedido.");

		return respuestaComando;

	}

}

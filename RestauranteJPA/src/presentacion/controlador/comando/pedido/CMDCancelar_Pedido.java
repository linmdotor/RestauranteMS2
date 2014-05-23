package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import negocio.pedido.TPedido;
import negocio.pedido.ValidarTPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDCancelar_Pedido implements CMD  {

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;
		int ID = -1;
		
		if(new ValidarTPedido().pedidoCorrecto((TPedido) objeto )){
			
			try {
				
				ID = serviciosPedido.obtenerPedidos().get((Integer) objeto).getId_pedido();
				
				if(serviciosPedido.cancelarPedido(ID)){
					
					respuestaComando = new RespuestaCMD(EnumComandos.CANCELAR_PEDIDO,"Se ha cancelado el Pedido");
					
				}else
					respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al cancelar el pedido.");	
			
			} catch (Exception e) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
	

		return respuestaComando;

	}

}

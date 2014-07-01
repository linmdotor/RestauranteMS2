package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import negocio.pedido.transfer.TPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDCancelarPedido implements CMD{

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;	
		
		TPedido tPedido = (TPedido)objeto;
		
		if(tPedido.getFechaEntregado().equals("---") && tPedido.getFechaCancelado().equals("---"))
		{
			try {
	
				if(serviciosPedido.cancelarPedido(tPedido)){
					respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PEDIDO,"Se ha cancelar el nuevo Pedido.");
				}else
					respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al cancelar pedido. Error al insertar los datos.");	
			
			} catch (Exception e) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "No se puede cancelar un pedido que ya ha sido almacenado/cancelado");
		
	
		return respuestaComando;
	}

}

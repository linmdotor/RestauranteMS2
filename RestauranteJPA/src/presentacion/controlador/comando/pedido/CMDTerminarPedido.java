package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import negocio.pedido.transfer.TPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDTerminarPedido implements CMD{

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;	
		
		TPedido tPedido = (TPedido)objeto;
		
		try {
			if(serviciosPedido.altaPedido(tPedido)){
				respuestaComando = new RespuestaCMD(EnumComandos.TERMINAR_PEDIDO,"Se ha almacenado el nuevo Pedido");
			}else
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al almacenar nuevo pedido. Error al insertar los datos.");	
		
		} catch (Exception e) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
			e.printStackTrace();
		}
		
	
		return respuestaComando;
	}

}

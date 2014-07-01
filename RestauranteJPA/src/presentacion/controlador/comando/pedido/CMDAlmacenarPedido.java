package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import negocio.pedido.transfer.TPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAlmacenarPedido implements CMD{

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;	
		
		TPedido tPedido = (TPedido)objeto;
		
		if(tPedido.getFechaEntregado().equals("---") && tPedido.getFechaCancelado().equals("---"))
		{
			try {
	
				if(serviciosPedido.almacenarPedido(tPedido)){
					respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PEDIDO,"Se ha almacenado el nuevo Pedido, y actualizado el Stock de los productos");
				}else
					respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al almacenar pedido. Error al insertar los datos.");	
			
			} catch (Exception e) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "No se puede almacenar un pedido que ya ha sido almacenado/cancelado");
		
	
		return respuestaComando;
	}

}

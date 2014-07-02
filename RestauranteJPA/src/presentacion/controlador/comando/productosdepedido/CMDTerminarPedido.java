package presentacion.controlador.comando.productosdepedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import negocio.pedido.transfer.TPedido;
import negocio.pedido.transfer.ValidarTPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDTerminarPedido implements CMD{

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;	
		
		//if (new ValidarTPedido().pedidoCorrecto((TPedido)objeto))
		//{
			TPedido tPedido = (TPedido)objeto;
			if(tPedido.getListaProductosPedido().size() > 0)
			{
				try {
					if(serviciosPedido.altaPedido(tPedido)){
						respuestaComando = new RespuestaCMD(EnumComandos.TERMINAR_PEDIDO,"Se ha almacenado el nuevo Pedido");
					}else
						respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al almacenar nuevo pedido. Error al insertar los datos.");	
				
				} catch (Exception e) {
					respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
					e.printStackTrace();
				}
			}
			else
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Debe seleccionar al menos un producto de la lista");
		//}
		//else
		//	respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al insertar el producto. Los datos no son válidos");
	
		return respuestaComando;
	}

}

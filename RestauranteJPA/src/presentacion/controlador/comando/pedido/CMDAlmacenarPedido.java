package presentacion.controlador.comando.pedido;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import negocio.pedido.TPedido;
import negocio.pedido.ValidarTPedido;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAlmacenarPedido implements CMD{

	@Override
	public RespuestaCMD ejecuta(Object objeto) {
		SAPedido serviciosPedido = FactoriaNegocio.obtenerInstancia().generaSAPedido();
		RespuestaCMD respuestaComando = null;
		
		if(new ValidarTPedido().pedidoCorrecto((TPedido) objeto )){
			
			try {
				if(serviciosPedido.almacenarPedido((TPedido) objeto)){
					respuestaComando = new RespuestaCMD(EnumComandos.ALMACENAR_PEDIDO,"Se ha almacenado el nuevo Pedido");
				}else
					respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al almacenar nuevo pedido. Error al insertar los datos.");	
			
			} catch (Exception e) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
	
		return respuestaComando;
	}

}

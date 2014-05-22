package negocio.pedido;

import java.util.List;

import presentacion.controlador.RespuestaCMD;

public interface SAPedido {
	public TPedido obtenerPedido(int ID) throws Exception;
	
	public List<Pedido> obtenerPedidos() throws Exception;
	
	public RespuestaCMD altaPedido(int IDprov) throws Exception;
		
	public RespuestaCMD cerrarPedido(List<Object> lista) throws Exception;
		
	public RespuestaCMD cancelarPedido(int ID) throws Exception;
	
	public RespuestaCMD almacenarPedido(int ID) throws Exception;

	public Object obtenerPedidoProductos(int objeto) throws Exception;

}

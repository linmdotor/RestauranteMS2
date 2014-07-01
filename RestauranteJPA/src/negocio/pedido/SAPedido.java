package negocio.pedido;

import java.util.List;

import negocio.pedido.transfer.TPedido;

public interface SAPedido {
	public TPedido obtenerPedido(int ID) throws Exception;
	
	public List<TPedido> obtenerPedidos() throws Exception;
	
	public boolean altaPedido(TPedido tPedido) throws Exception;
		
	public boolean almacenarPedido(int ID) throws Exception;
	
	public boolean cancelarPedido(int ID) throws Exception;

}

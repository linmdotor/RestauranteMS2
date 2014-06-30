package negocio.pedido;

import java.util.List;

import negocio.pedido.transfer.TPedido;
import negocio.productosdepedido.transfer.TProductoDePedido;

public interface SAPedido {
	public TPedido obtenerPedido(int ID) throws Exception;
	
	public List<TPedido> obtenerPedidos() throws Exception;
	
	public boolean altaPedido(int IDprov) throws Exception;
		
	public boolean cerrarPedido(List<TProductoDePedido> lista) throws Exception;
		
	public boolean cancelarPedido(int ID) throws Exception;
	
	public boolean almacenarPedido(TPedido tpedido) throws Exception;

	public Object obtenerPedidoProductos(int objeto) throws Exception;

}

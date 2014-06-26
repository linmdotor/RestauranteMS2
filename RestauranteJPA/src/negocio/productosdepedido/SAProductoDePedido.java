package negocio.productosdepedido;

import java.util.List;

import negocio.productosdepedido.transfer.TProductoDePedido;

public interface SAProductoDePedido {
	
	public boolean anadirProductoPedido(TProductoDePedido tProductoDePedido) throws Exception;
	
	public List<TProductoDePedido> obtenerProductosPedido(int ID) throws Exception;
	
	public boolean modificarProductoPedido(TProductoDePedido tProductoDePedido)throws Exception;
	
	public boolean bajaProductoPedido(TProductoDePedido tProductoDePedido)throws Exception;
	
}

package negocio.productosdepedido;

import java.util.List;

import negocio.productosdepedido.transfer.TProductoDePedido;

public interface SAProductoDePedido {

	public List<TProductoDePedido> obtenerProductosPedido(int ID) throws Exception;
	
}

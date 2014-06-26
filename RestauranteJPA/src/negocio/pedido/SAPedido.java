package negocio.pedido;

import java.util.List;

import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedido;
import negocio.pedido.transfer.TPedidoProducto;
import presentacion.controlador.RespuestaCMD;

public interface SAPedido {
	public TPedido obtenerPedido(int ID) throws Exception;
	
	public List<Pedido> obtenerPedidos() throws Exception;
	
	public boolean altaPedido(int IDprov) throws Exception;
		
	public boolean cerrarPedido(List<TPedidoProducto> lista) throws Exception;
		
	public boolean cancelarPedido(int ID) throws Exception;
	
	public boolean almacenarPedido(TPedido tpedido) throws Exception;

	public Object obtenerPedidoProductos(int objeto) throws Exception;

}

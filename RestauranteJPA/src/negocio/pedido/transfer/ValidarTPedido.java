package negocio.pedido.transfer;



public class ValidarTPedido {
	public boolean pedidoCorrecto(TPedido tPedido) {

		boolean productoCorrecto = false;

		if (tPedido.getFechaCancelado() != null && tPedido.getFechaCancelado() != ""
		&&  tPedido.getFechaEntregado() != null && tPedido.getFechaEntregado() != ""
		&&  tPedido.getFechaRealizado() != null && tPedido.getFechaRealizado() != ""
		&&  tPedido.getId_pedido() > 0 && tPedido.getId_proveedor() >0)
		{
			productoCorrecto = true;
		}
			
		return productoCorrecto;

	}
	
}

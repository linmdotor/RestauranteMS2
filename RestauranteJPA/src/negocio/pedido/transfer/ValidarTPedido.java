package negocio.pedido.transfer;



public class ValidarTPedido {
	public boolean pedidoCorrecto(TPedido tPedido) {

		boolean productoCorrecto = false;

		if (tPedido.getFechaCancelado().length() > 0
		&&  tPedido.getFechaEntregado().length() > 0
		&&  tPedido.getFechaRealizado().length() > 0
		&&  (tPedido.getId_pedido() > 0 || tPedido.getId_pedido() > -1)
		&& tPedido.getId_proveedor() > 0)
		{
			productoCorrecto = true;
		}
			
		return productoCorrecto;

	}
	
}

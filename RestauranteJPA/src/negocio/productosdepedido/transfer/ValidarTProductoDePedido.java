package negocio.productosdepedido.transfer;

public class ValidarTProductoDePedido {
	public boolean productoCorrecto(TProductoDePedido tProductoDePedido) {
		boolean productoCorrecto = false;

		if (tProductoDePedido.getPrecio() >= 0
				&& tProductoDePedido.getCantidad() > 0
				&& tProductoDePedido.getProducto() > 0 
				&& (tProductoDePedido.getPedido() > 0 || tProductoDePedido.getPedido() == -1))
			productoCorrecto = true;

		return productoCorrecto;
	}
}

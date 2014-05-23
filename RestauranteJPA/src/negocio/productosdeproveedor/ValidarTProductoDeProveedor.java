package negocio.productosdeproveedor;


public class ValidarTProductoDeProveedor {
	public boolean productoCorrecto(TProductoDeProveedor tProductoDeProveedor) {

		boolean productoCorrecto = false;

		if (tProductoDeProveedor.getPrecio() >= 0 && tProductoDeProveedor.getProducto() > 0 && tProductoDeProveedor.getProveedor() >0)
			productoCorrecto = true;

		return productoCorrecto;

	}
}

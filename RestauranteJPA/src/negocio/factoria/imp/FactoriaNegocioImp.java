/**
 * 
 * Factoria de persistencia
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package negocio.factoria.imp;

import negocio.factoria.FactoriaNegocio;
import negocio.pedido.SAPedido;
import negocio.pedido.imp.SAPedidoImp;
import negocio.producto.SAProducto;
import negocio.producto.imp.SAProductoImp;
import negocio.productosdepedido.SAProductoDePedido;
import negocio.productosdepedido.imp.SAProductoDePedidoImp;
import negocio.productosdeproveedor.SAProductoDeProveedor;
import negocio.productosdeproveedor.imp.SAProductoDeProveedorImp;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.imp.SAProveedorImp;

public class FactoriaNegocioImp extends FactoriaNegocio {

	// Metodos	

	public SAProducto generaSAProducto() {
		return new SAProductoImp();
	}
	
	public SAProveedor generaSAProveedor() {
		return new SAProveedorImp();
	}

	public SAPedido generaSAPedido() {
		return new SAPedidoImp();
	}

	public SAProductoDeProveedor generaSAProductosDeProveedor() {
		return new SAProductoDeProveedorImp();
	}
		
	public SAProductoDePedido generaSAProductosDePedido() {
		return new SAProductoDePedidoImp();
	}
}
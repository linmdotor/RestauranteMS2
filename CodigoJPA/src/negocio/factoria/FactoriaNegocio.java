/**
 * 
 * Factoria de la factoria de persistencia
 * 
 * @author Marco Gonz�lez, Juan Carlos * @author Mart�nez Dotor, Jes�s * @author Picado �lvarez, Mar�a * @author Rojas Mor�n, Amy Alejandra * @author Serrano �lvarez, Jos� * @author Vargas Paredes, Jhonny
 *  
 */

package negocio.factoria;

import negocio.factoria.imp.FactoriaNegocioImp;
import negocio.pedido.SAPedido;
import negocio.producto.SAProducto;
import negocio.productosdeproveedor.SAProductosDeProveedor;
import negocio.proveedor.SAProveedor;

public abstract class FactoriaNegocio {

	// Atributos

	private static FactoriaNegocio factoria;

	// Metodos

	public static FactoriaNegocio obtenerInstancia() {

		if (factoria == null)
			factoria = new FactoriaNegocioImp();

		return factoria;
	}

	public abstract SAProducto generaSAProducto();

	public abstract SAProveedor generaSAProveedor();
	
	public abstract SAPedido generaSAPedido();
	
	public abstract SAProductosDeProveedor generaSAProductosDeProveedor();

}
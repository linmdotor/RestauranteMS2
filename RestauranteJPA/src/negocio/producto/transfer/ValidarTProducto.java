/**
 * 
 * Validador de datos del Transfer Plato
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package negocio.producto.transfer;

public class ValidarTProducto {

	public boolean productoCorrecto(TProducto tProducto) {

		boolean productoCorrecto = false;

		if (tProducto.getNombre().length() > 0 && tProducto.getStock() >= 0)
			productoCorrecto = true;

		return productoCorrecto;

	}

}
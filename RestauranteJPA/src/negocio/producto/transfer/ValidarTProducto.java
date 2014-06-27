/**
 * 
 * Validador de datos del Transfer Plato
 * 
 * @author Marco Gonz�lez, Juan Carlos * @author Mart�nez Dotor, Jes�s * @author Picado �lvarez, Mar�a * @author Rojas Mor�n, Amy Alejandra * @author Serrano �lvarez, Jos� * @author Vargas Paredes, Jhonny
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
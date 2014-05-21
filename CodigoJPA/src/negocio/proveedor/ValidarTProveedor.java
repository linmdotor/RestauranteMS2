/**
 * 
 * Validador de datos del Transfer Plato
 * 
 * @author Marco Gonz�lez, Juan Carlos * @author Mart�nez Dotor, Jes�s * @author Picado �lvarez, Mar�a * @author Rojas Mor�n, Amy Alejandra * @author Serrano �lvarez, Jos� * @author Vargas Paredes, Jhonny
 *  
 */

package negocio.proveedor;

public class ValidarTProveedor {

	public boolean proveedorCorrecto(TProveedor tProveedor) {

		boolean proveedorCorrecto = false;

		if (tProveedor.getNombre().length() > 0 && tProveedor.getNIF().length() > 0 && tProveedor.getTelefono().length() > 0)
			proveedorCorrecto = true;

		return proveedorCorrecto;

	}

}

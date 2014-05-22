/**
 * 
 * Validador de datos del Transfer Plato
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
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

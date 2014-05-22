/**
 * Clase que comprueba si un valor dado es float
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 * @version 13/06/2013
 * 
 */

package negocio;

public class ComprobadorFloat {

	public boolean isNumeric(String cadena) {

		/**
		 * Comprueba si un String es un numero
		 * 
		 * @return boolean isNumeric
		 */
		try { // Comprueba si es un float si no da excepcion

			Float.parseFloat(cadena);
			return true;

		} catch (NumberFormatException nfe) {

			return false;

		}

	}

}

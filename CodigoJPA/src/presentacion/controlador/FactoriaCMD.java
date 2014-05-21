/**
 * 
 * Factoria de la factoria de Comandos
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador;

import presentacion.controlador.imp.FactoriaCMDImp;


public abstract class FactoriaCMD {

	// Atributos

	private static FactoriaCMD factoria;

	// Metodos

	public static FactoriaCMD obtenerInstancia() {

		if (factoria == null)
			factoria = new FactoriaCMDImp();

		return factoria;
	}

	public abstract CMD generaComando(EnumComandos nombreComando);

}

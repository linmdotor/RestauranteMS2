/**
 * 
 * Factoria del Controlador de Aplicacion
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador;

import presentacion.controlador.imp.ApplicationControllerImp;


public abstract class ApplicationController {

	// Atributos

	private static ApplicationController controladorAplicacion;

	// Metodos

	public static ApplicationController obtenerInstancia() {

		if (controladorAplicacion == null)
			controladorAplicacion = new ApplicationControllerImp();

		return controladorAplicacion;
	}

	public abstract void handleRequest(EnumComandos nombreComando, Object objeto);

}

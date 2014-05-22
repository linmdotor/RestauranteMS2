/**
 * 
 * Factoria del Dispatcher
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador;

import presentacion.controlador.imp.DispatcherImp;

public abstract class Dispatcher {

	// Atributos

	private static Dispatcher dispatcher;

	// Metodos

	public static Dispatcher obtenerInstancia() {

		if (dispatcher == null)
			dispatcher = new DispatcherImp();

		return dispatcher;
	}

	public abstract void despachaRespuesta(RespuestaCMD respuestaComando);

}

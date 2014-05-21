/**
 * 
 * Controlador de Aplicacion
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.controlador.imp;

import presentacion.controlador.ApplicationController;
import presentacion.controlador.CMD;
import presentacion.controlador.Dispatcher;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.FactoriaCMD;

public class ApplicationControllerImp extends ApplicationController {

	// Metodos

	public void handleRequest(EnumComandos nombreComando, Object objeto) {

		FactoriaCMD factoriaComandos = FactoriaCMD.obtenerInstancia();

		CMD comando = factoriaComandos.generaComando(nombreComando);
		Dispatcher.obtenerInstancia().despachaRespuesta(comando.ejecuta(objeto));

	}

}

/**
 * 
 * Clase principal que ejecutara toda la aplicacion
 * 
 * @author Marco González, Juan Carlos
 * @author Martínez Dotor, Jesús
 * @author Picado Álvarez, María
 * @author Rojas Morán, Amy Alejandra
 * @author Serrano Álvarez, José
 * @author Vargas Paredes, Jhonny
 *  
 */

//- Poner ID automatico
//- Comprobar cosas en negocio (no presentacion), por ejemplo, cuando intentamos crear un producto con un ID existente pues no deja, pero no avisa de nada
//- Tratar bien las excepciones
//- Poner concurrencia persitencia: em.lock(... (esta en diaspositivas muy bien, es una sola linea)
//- Cambios de la primera correccion
//- Factoria de las Ventanas: Hacer VentanaTabla que hereda de JFrame, y todas las ventanas heredan de ahi
//- Dispatcher Imp, los set visible deben estar dentro del actualizar

package principal;

import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.VentanaPrincipal;

public class Principal {

	public static String UNIDAD_PERSISTENCIA = "UNIDAD_PERSISTENCIA_RESTAURANTE";
	
	@SuppressWarnings("unused")
		
	// Metodos

	public void ejecuta() {

		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PRINCIPAL, null);
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		new Principal().ejecuta();

	}

}

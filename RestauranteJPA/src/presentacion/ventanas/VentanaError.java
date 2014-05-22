package presentacion.ventanas;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VentanaError extends JFrame {

	public VentanaError(String mensajeError) {

		javax.swing.JOptionPane.showMessageDialog(this, mensajeError, "Error",
				javax.swing.JOptionPane.ERROR_MESSAGE);

	}

}

package presentacion.ventanas;

import javax.swing.JFrame;

public class VentanaError extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1573726462892800138L;

	public VentanaError(String mensajeError) {

		javax.swing.JOptionPane.showMessageDialog(this, mensajeError, "Error",
				javax.swing.JOptionPane.ERROR_MESSAGE);

	}

}

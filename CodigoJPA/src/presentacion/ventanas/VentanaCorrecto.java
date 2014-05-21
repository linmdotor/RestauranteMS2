package presentacion.ventanas;

import javax.swing.JFrame;

public class VentanaCorrecto extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1155489456800897232L;

	public VentanaCorrecto(String mensaje) {

		javax.swing.JOptionPane.showMessageDialog(this, mensaje, "Correcto",
				javax.swing.JOptionPane.INFORMATION_MESSAGE);

	}

}

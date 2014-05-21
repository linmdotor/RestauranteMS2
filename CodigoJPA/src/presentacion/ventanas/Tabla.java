package presentacion.ventanas;

import javax.swing.table.DefaultTableModel;

public class Tabla extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3178555212834299076L;

	public boolean isCellEditable(int row, int column) {
		return false;

	}

}
/**
 * 
 * Vista Principal
 * 
 * @author Marco González, Juan Carlos 
 * @author Martínez Dotor, Jesús 
 * @author Picado Álvarez, María 
 * @author Rojas Morán, Amy Alejandra 
 * @author Serrano Álvarez, José 
 * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;

public class VentanaPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6438727790611004327L;

	// Constructor
	public VentanaPrincipal() {
		
		//PROPIEDADES DE LA VENTANA ---------------------------
		this.setLocationByPlatform(true);
		setTitle("Restaurante");
		setResizable(false);
		setVisible(true);
		setSize(326, 210);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//PRODUCTO ---------------------------
		JButton btnAlmacenDePlatos = new JButton("Gestionar PRODUCTOS");
		btnAlmacenDePlatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PRODUCTO, null);
				
			}
		});
		btnAlmacenDePlatos.setBounds(69, 45, 179, 26);
		getContentPane().add(btnAlmacenDePlatos);
		
		JButton btnGestionarProveedores = new JButton("Gestionar PROVEEDORES");
		btnGestionarProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PROVEEDOR, null);
				
			}
		});
		btnGestionarProveedores.setBounds(69, 97, 179, 26);
		getContentPane().add(btnGestionarProveedores);
		
		JButton btnGestionarPedidos = new JButton("Gestionar PEDIDOS");
		btnGestionarPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PEDIDO, null);
				
			}
		});
		btnGestionarPedidos.setBounds(69, 135, 179, 26);
		getContentPane().add(btnGestionarPedidos);
	
	}
}

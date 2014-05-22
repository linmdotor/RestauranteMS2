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

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {

	
	private static VentanaPrincipal ventana; //instancia singleton
	
	//GetInstance
	public static VentanaPrincipal obtenerInstancia() {

		if (ventana == null)
			ventana = new VentanaPrincipal();

		return ventana;
	}
	
	// Constructor
	public VentanaPrincipal() {
		
		//PROPIEDADES DE LA VENTANA ---------------------------
		this.setLocationByPlatform(true);
		setTitle("Restaurante");
		setResizable(false);
		setVisible(false);
		setSize(256, 200);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//PRODUCTO ---------------------------
		JButton btnAlmacenDePlatos = new JButton("Gestionar PRODUCTOS");
		btnAlmacenDePlatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PRODUCTO, null);
				
			}
		});
		btnAlmacenDePlatos.setBounds(38, 30, 183, 26);
		getContentPane().add(btnAlmacenDePlatos);
		
		JButton btnGestionarProveedores = new JButton("Gestionar PROVEEDORES");
		btnGestionarProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PROVEEDOR, null);
				
			}
		});
		btnGestionarProveedores.setBounds(38, 80, 183, 26);
		getContentPane().add(btnGestionarProveedores);
		
		JButton btnGestionarPedidos = new JButton("Gestionar PEDIDOS");
		btnGestionarPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PEDIDO, null);
				
			}
		});
		btnGestionarPedidos.setBounds(38, 130, 183, 26);
		getContentPane().add(btnGestionarPedidos);
	
	}
}

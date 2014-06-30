package presentacion.ventanas.pedido;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedido;
import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.proveedor.transfer.TProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;

@SuppressWarnings("serial")
/*
 * CODIGO DE JOSE
 * 
 * 
 * 
 * public class VentanaGestionPedidos extends JFrame{

	private static VentanaGestionPedidos ventana; //instancia singleton
	
	private Tabla tabla;
	private JTable tbPedidos;
	private Vector fila;
	private JComboBox boxProveedor;

	private JScrollPane scrollPanel;
	
	private List<TProveedor> listaProveedores;
	private JTable tbProductos;
	
	public List<TProveedor> getListaProveedores() {
		return listaProveedores;
	}

	public void setListaProveedores(List<TProveedor> listaProveedores) {
		this.listaProveedores = listaProveedores;
	}

	// Mutadores y Accedentes
	
	public JTable getTbPedidos() {
		return tbPedidos;
	}

	public void setTbPedidos(JTable tbPedidos) {
		this.tbPedidos = tbPedidos;
	}
	
	//GetInstance
	public static VentanaGestionPedidos obtenerInstancia() {

		if (ventana == null)
			ventana = new VentanaGestionPedidos();

		return ventana;
	}
				
	//Constructor
		
	public VentanaGestionPedidos()
	{
		setTitle("Gestion de Proveedores");
		setResizable(false);
		
		setSize(798, 390);
		//setVisible(true);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		
		// -------- PANEL PRINCIPAL --------------
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(567, 5, 215, 350);
		getContentPane().add(panelFormulario);
		panelFormulario.setLayout(null);
		
		boxProveedor = new JComboBox();
		boxProveedor.setBounds(28, 171, 160, 25);
		panelFormulario.add(boxProveedor);	
		
		JPanel panelLista = new JPanel();
		panelLista.setBounds(10, 5, 564, 353);
		getContentPane().add(panelLista);
		panelLista.setLayout(null);
		
		
		// ------- NUEVO PEDIDO ----------------------
		JButton btnNuevoPedido = new JButton("Nuevo Pedido");
		btnNuevoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_ALTA_PEDIDO, obtenerProveedor());

			}
		});
		btnNuevoPedido.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNuevoPedido.setBounds(28, 207, 160, 30);
		panelFormulario.add(btnNuevoPedido);
		
		JLabel lblSeleccioneProveedorPara = new JLabel("Seleccione Proveedor para");
		lblSeleccioneProveedorPara.setBounds(29, 121, 160, 26);
		panelFormulario.add(lblSeleccioneProveedorPara);
		
		JLabel lblElNuevoPedido = new JLabel(" el nuevo Pedido a Realizar");
		lblElNuevoPedido.setBounds(29, 144, 159, 16);
		panelFormulario.add(lblElNuevoPedido);
		
		

		// --------- CONSTRUCCI�N TABLA PEDIDOS-------------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 35, 259, 291);
		panelLista.add(scrollPane);
		
		JLabel lblPedidos = new JLabel("Pedidos");
		lblPedidos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPedidos.setBounds(45, 12, 192, 16);
		panelLista.add(lblPedidos);
		lblPedidos.setHorizontalAlignment(SwingConstants.CENTER);

		tbPedidos = new JTable();
		scrollPane.setViewportView(tbPedidos);
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(293, 35, 259, 291);
		panelLista.add(scrollPane_1);
		
		tbProductos = new JTable();
		scrollPane_1.setViewportView(tbProductos);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblProductosDelPedido = new JLabel("Productos del Pedido");
		lblProductosDelPedido.setBounds(373, 12, 121, 16);
		panelLista.add(lblProductosDelPedido);
		tbPedidos.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (getTbPedidos().getSelectedRow() != -1)
						{
							
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.RELLENAR_TB_PRODUCTOS_PEDIDO, tbPedidos.getSelectedRow()+1);
							
						
						}
					}
				});

	}

	// Metodos
	
	public void actualizarVentana(){
		
		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PEDIDOS, null);
		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PEDIDOS_PROVEEDOR, null);
	}

	public void actualizar(Object object) {

		List<Pedido> lista = new ArrayList<Pedido>();
		
		if (object == null)
			rellenarTablaPedidos(lista);
		else			
			rellenarTablaPedidos((List<Pedido>) object);

		setVisible(true);
		repaint();

	}
	
	public void actualizarTablaProductos(Object objeto) {

		TPedido pedido = (TPedido) objeto;
		
		tabla = new Tabla();

				tabla.addColumn("ID_PRODUCTO");
				tabla.addColumn("NOMBRE");
				tabla.addColumn("CANTIDAD");

				for (int i = 0; i < pedido.getListaProductosPedido().size(); i++) {

					fila = new Vector();
					ProductoDePedido bo = pedido.getListaProductosPedido().get(i);
					fila.add(bo.getProducto().getId_producto());
					fila.add(bo.getProducto().getNombre());
					fila.add(bo.getCantidad());

					tabla.addRow(fila);
				}

				tbProductos.setModel(tabla);

	}
	
	private void rellenarTablaPedidos(List<Pedido> lista) {
		tabla = new Tabla();

		tabla.addColumn("ID_PEDIDO");
		tabla.addColumn("ID_PROVEEDOR");
		tabla.addColumn("PRECIO");

		for (int i = 0; i < lista.size(); i++) {			
			
			fila = new Vector();
			Pedido bo = lista.get(i);			
			
			fila.add(bo.getId_pedido());
			fila.add(bo.getProveedor().getId_proveedor());
			fila.add(bo.getPrecio());

			tabla.addRow(fila);
		}

		tbPedidos.setModel(tabla);
		
	}
	
	private int obtenerProveedor() {
		
		
		return ((int) boxProveedor.getSelectedIndex() + 1);		
		
	}
	
	private boolean mensajeConfirmacionSiNo(String msj, String cabecera) {	
		return (JOptionPane.showConfirmDialog(this, msj, cabecera, JOptionPane.YES_NO_OPTION) == 0);

	}
	
	public float redondear(float numero){		
		return (float)Math.rint(numero*100)/100;		
	}

	public void actualizarProveedores(Object objeto) {
		
		listaProveedores = (List<TProveedor>) objeto;
				
		boxProveedor.removeAllItems();
		
		for (int j = 0; j < ((List<TProveedor>) objeto).size(); j++)
			boxProveedor.addItem(((List<TProveedor>) objeto).get(j).getNombre());		
		
	}	
}*/

public class VentanaGestionPedidos extends JFrame{

	private static VentanaGestionPedidos ventana; //instancia singleton

	private JComboBox boxProveedores;
	
	/*private JTextField textFieldID;
	private JTextField textFieldNombre;
	private JTextField textFieldNif;
	private JTextField textFieldTelefono;*/
	
	private Tabla tabla;
	private JTable tbPedidos;
	private JTable tbProductos;
	private Vector fila;

	private JScrollPane scrollPanel;

	// Mutadores y Accedentes

	public JTable getTbProductos() {
		return tbProductos;
	}

	public void setTbProductos(JTable tbProductos) {
		this.tbProductos = tbProductos;
	}
	
	public JTable getTbPedidos() {
		return tbPedidos;
	}

	public void setTbPedidos(JTable tbPedidos) {
		this.tbPedidos = tbPedidos;
	}
	
	//GetInstance
	public static VentanaGestionPedidos obtenerInstancia() {

		if (ventana == null)
			ventana = new VentanaGestionPedidos();

		return ventana;
	}
				
	//Constructor
		
	public VentanaGestionPedidos()
	{
		setTitle("Gestion de Proveedores");
		setResizable(false);
		
		setSize(798, 390);
		//setVisible(true);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		
		// -------- PANEL PRINCIPAL --------------
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(574, 5, 215, 350);
		getContentPane().add(panelFormulario);
		panelFormulario.setLayout(null);
		
		boxProveedores = new JComboBox();
		boxProveedores.setBounds(28, 171, 160, 25);
		panelFormulario.add(boxProveedores);
	
		JLabel lblSeleccioneProveedorPara = new JLabel("Seleccione Proveedor para");
		lblSeleccioneProveedorPara.setBounds(29, 121, 160, 26);
		panelFormulario.add(lblSeleccioneProveedorPara);
		
		JLabel lblElNuevoPedido = new JLabel(" el nuevo Pedido: ");
		lblElNuevoPedido.setBounds(29, 144, 159, 16);
		panelFormulario.add(lblElNuevoPedido);
		
		/*// ------------- ID -----------------------
		JLabel lblId = new JLabel("ID Proveedor:");
		lblId.setBounds(2, 46, 100, 16);
		panelFormulario.add(lblId);
		
		textFieldID = new JTextField();
		textFieldID.setBounds(83, 44, 118, 20);
		textFieldID.setEditable(false);
		panelFormulario.add(textFieldID);
		textFieldID.setColumns(10);

		// ------------- NOMBRE -------------------
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(30, 77, 48, 16);
		panelFormulario.add(lblNombre);

		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(83, 75, 118, 20);
		textFieldNombre.setEditable(false);
		panelFormulario.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		//------------- NIF -------------------
		JLabel lblNif = new JLabel("NIF:");
		lblNif.setBounds(58, 107, 20, 16);
		panelFormulario.add(lblNif);

		textFieldNif = new JTextField();
		textFieldNif.setBounds(83, 103, 118, 20);
		textFieldNif.setEditable(false);
		panelFormulario.add(textFieldNif);
		textFieldNif.setColumns(10);
	
		// ------------- TELEFONO ------------------
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(25, 135, 60, 16);
		panelFormulario.add(lblTelefono);
		
		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(83, 132, 118, 20);
		textFieldTelefono.setEditable(false);
		panelFormulario.add(textFieldTelefono);
		textFieldTelefono.setColumns(9);
		

		JLabel lblAadirNuevoProveedor = new JLabel("Datos Proveedor:");
		lblAadirNuevoProveedor.setFont(new Font("Dialog", Font.BOLD, 13));
		lblAadirNuevoProveedor.setBounds(12, 12, 192, 16);
		panelFormulario.add(lblAadirNuevoProveedor);
		lblAadirNuevoProveedor.setHorizontalAlignment(SwingConstants.CENTER);
		*/
		
		// ------- ALMACENAR PEDIDO ----------------------
		JButton btnalmacenarPedido = new JButton("Almacenar Pedido");
		btnalmacenarPedido.setBounds(9, 276, 192, 26);
		panelFormulario.add(btnalmacenarPedido);
		btnalmacenarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ALMACENAR_PEDIDO, obtenerPedido());
				//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PEDIDOS, null);

			}
		});
		
		// ------- CANCELAR PEDIDO ----------------------
		JButton btnCancelarPedido = new JButton("Cancelar Pedido");
		btnCancelarPedido.setBounds(9, 310, 192, 26);
		panelFormulario.add(btnCancelarPedido);
		btnCancelarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.CANCELAR_PEDIDO, obtenerPedido());
				//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PEDIDOS, null);

			}
		});
		
		JPanel panelLista = new JPanel();
		panelLista.setBounds(10, 5, 564, 353);
		getContentPane().add(panelLista);
		panelLista.setLayout(null);
		
		
		// ------- NUEVO PEDIDO ----------------------
		JButton btnNuevoPedido = new JButton("Nuevo Pedido");
		btnNuevoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_ALTA_PEDIDO, obtenerProveedor());

			}
		});
		btnNuevoPedido.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNuevoPedido.setBounds(40, 200, 142, 30);
		panelFormulario.add(btnNuevoPedido);
		
		

		// --------- CONSTRUCCI�N TABLA PEDIDOS-------------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 35, 280, 291);
		panelLista.add(scrollPane);
		
		JLabel lblPedidos = new JLabel("Pedidos");
		lblPedidos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPedidos.setBounds(45, 12, 192, 16);
		panelLista.add(lblPedidos);
		lblPedidos.setHorizontalAlignment(SwingConstants.CENTER);

		tbPedidos = new JTable();
		scrollPane.setViewportView(tbPedidos);
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbPedidos.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (getTbPedidos().getSelectedRow() != -1)
						{
							//A�ade en el formulario los datos del Proveedor del pedido actual
							//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PEDIDO, getTbPedidos().getValueAt(getTbPedidos().getSelectedRow(),0) );
							//A�ade los datos de la tabla de productos (lista de productos del pedido actual)
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PEDIDO, getTbPedidos().getValueAt(getTbPedidos().getSelectedRow(),0) );
						}
					}
				});
		
		// --------- CONSTRUCCI�N TABLA PRODUCTOS-------------
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(285, 35, 280, 291);
		panelLista.add(scrollPane2);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblProductos.setBounds(350, 12, 192, 16);
		panelLista.add(lblProductos);
		lblProductos.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbProductos = new JTable();
		scrollPane2.setViewportView(tbProductos);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//LA TABLA DE PRODUCTOS NO DEBE TENER UN ACTION LISTENER, PUES NO SE PRODUCE NINGUNA ACCI�N AL CLICKARSE

	}

	// Metodos

	public void actualizar() {

		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PEDIDOS, null);
		
		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PROVEEDORES_DISPONIBLES, null);

		setVisible(true);
		repaint();

	}

	public void rellenarTablaPedidos(Object object) {
		
		tabla = new Tabla();

		tabla.addColumn("ID_PED");
		tabla.addColumn("ID_PROV");
		tabla.addColumn("REALIZ.");
		tabla.addColumn("ENTREG.");
		tabla.addColumn("CANCEL.");

		if(object != null)
		{
		
			List<TPedido> lista = (List<TPedido>) object;
			
			for (int i = 0; i < lista.size(); i++) {
	
				fila = new Vector();
				TPedido pedido = lista.get(i);
				fila.add(pedido.getId_pedido());
				fila.add(pedido.getId_proveedor());
				fila.add(pedido.getFechaRealizado());
				fila.add(pedido.getFechaEntregado());
				fila.add(pedido.getFechaCancelado());
	
				tabla.addRow(fila);
			}

		}
		
		tbPedidos.setModel(tabla);
		
		repaint();
		
	}

	public void actualizarTablaProductos(Object objeto) {
		
		tabla = new Tabla();

		tabla.addColumn("ID_PRODUCTO");
		tabla.addColumn("PRECIO");
		tabla.addColumn("CANTIDAD");

		if (objeto != null)
		{
			List<TProductoDePedido> lista = (List<TProductoDePedido>) objeto;
			
			for (int i = 0; i < lista.size(); i++) {
	
				fila = new Vector();
				TProductoDePedido producto_pedido = lista.get(i);
				fila.add(producto_pedido.getProducto());
				fila.add(producto_pedido.getPrecio());
				fila.add(producto_pedido.getCantidad());
	
				tabla.addRow(fila);
			}
		}

		tbProductos.setModel(tabla);
				
		repaint();
		
	}
	
	public void actualizarListaProveedores(Object objeto) {
		
		List<TProveedor> listaProveedores = (List<TProveedor>) objeto;
		
		boxProveedores.removeAllItems();
		
		for (int i = 0; i < listaProveedores.size(); i++)
			boxProveedores.addItem(listaProveedores.get(i).getId_proveedor());		
				
		repaint();
		
	}
	
	/*
	 * Este m�todo recibe un TransferProveedor, encapsulado en un Object, y coge de �l el ID del proveedor
	 */
	public void modificarFormulario(Object objeto) {

		TProveedor proveedor = (TProveedor) objeto;
		/*
		textFieldID.setText(Integer.toString(proveedor.getId_proveedor()));
		textFieldNombre.setText(proveedor.getNombre());
		textFieldNif.setText(proveedor.getNIF());
		textFieldTelefono.setText(proveedor.getTelefono());
		*/
		/*
		 * TODO
		 * 
		 * 
		 * SI VEMOS QUE HACER ESTO ES MUY COMPLICADO o CREA ACOPLAMIENTOS INNECESARIOS (Que puede ser)
		 * PODEMOS OPTAR POR MOSTRAR SIMPLEMENTE EL ID_PROV DEL PEDIDO
		 * ELIMINANDO EL RESTO DEL FORMULARIO
		 * 
		 * As� que el m�todo cambiar�a a pasarle un TPedido, en vez de Tproveedor
		 * public void modificarFormulario(Object objeto) {

				TPedido pedido = (TPedido) objeto;
				
				textFieldID.setText(Integer.toString(pedido.getId_proveedor()));
		 * 
		 */
	}
	
	public TPedido obtenerPedido() {

		TPedido pedido = new TPedido();
	
		//TODO
		
		/*
		 * ESTE METODO LO �NICO QUE TIENE QUE HACER ES SACAR LOS DATOS DE LA TABLAPEDIDO y FORMULARIO,
		 * DE LA FILA QUE EST� SELECCIONADA, Y METERLOS DENTRO DE UN TRANSFER TAL CUAL,
		 * id_pedido
		 * id_producto
		 * fechas...
		 * SIN VALIDAR NI NADA, PORQUE SUPONEMOS QUE LO QUE EST� EN LA TABLA ES CORRECTO
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		return pedido;
	}

	private int obtenerProveedor() {
		return ((int)boxProveedores.getSelectedItem());			
	}
	
	private boolean mensajeConfirmacionSiNo(String msj, String cabecera) {	
		return (JOptionPane.showConfirmDialog(this, msj, cabecera, JOptionPane.YES_NO_OPTION) == 0);

	}
	
	public float redondear(float numero){		
		return (float)Math.rint(numero*100)/100;		
	}

	
	
	

}


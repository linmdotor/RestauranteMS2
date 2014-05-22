package presentacion.ventanas.pedido;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;
import java.util.List;

import javax.swing.JButton;
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










import negocio.ComprobadorEnteros;
import negocio.pedido.Pedido;
import negocio.pedido.TPedido;
import negocio.pedido.TPedidoProducto;
import negocio.producto.TProducto;
import negocio.proveedor.Proveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;
import presentacion.ventanas.producto.VentanaProducto;

@SuppressWarnings("serial")
public class VentanaGestionPedidos extends JFrame{

	private static VentanaGestionPedidos ventana; //instancia singleton

	private JTextField textFieldID;
	private JTextField textFieldNombre;
	private JTextField textFieldNif;
	private JTextField textFieldTelefono;
	
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
	
		// ------------- ID -----------------------
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

				

			}
		});
		btnNuevoPedido.setFont(new Font("Dialog", Font.BOLD, 14));
		btnNuevoPedido.setBounds(40, 200, 142, 30);
		panelFormulario.add(btnNuevoPedido);
		
		

		// --------- CONSTRUCCIÓN TABLA PEDIDOS-------------
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
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PEDIDO, getTbPedidos().getValueAt(getTbPedidos().getSelectedRow(),0) );
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.RELLENAR_TB_PRODUCTOS_PEDIDO, getTbPedidos().getValueAt(getTbPedidos().getSelectedRow(),0) );
						}
					}
				});
		
		// --------- CONSTRUCCIÓN TABLA PRODUCTOS-------------
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
		tbProductos.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (getTbProductos().getSelectedRow() != -1)
						{
							//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PEDIDO, getTbProductos().getSelectedRow() );	
						}
					}
				});

	}

	// Metodos

	public void actualizar(Object object) {

		List<Pedido> lista = new ArrayList<Pedido>();
		
		if (object == null)
			rellenarTabla(lista);
		else			
			rellenarTabla((List<Pedido>) object);

		setVisible(true);
		repaint();

	}
	
	/*public void rellenarTabla(List<BOProveedor> lista) {
	
	tabla = new Tabla();
	
	tabla.addColumn("ID");
	tabla.addColumn("NOMBRE");
	tabla.addColumn("NIF");
	tabla.addColumn("TELEFONO");
	
	for (int i = 0; i < lista.size(); i++) {

		fila = new Vector();
		BOProveedor bo = lista.get(i);
		fila.add(bo.getId_proveedor());
		fila.add(bo.getNombre());
		fila.add(bo.getNif());
		fila.add(bo.getTelefono());
		
		fila.add(bo.isDisponible());
		
		tabla.addRow(fila);
	}

	tbProductos.setModel(tabla);

}*/
	
	/*public BOProveedor obtenerProducto() {

	int ID = -1;


	ComprobadorEnteros comprobadorEntero = new ComprobadorEnteros();

	if (comprobadorEntero.isNumeric(textFieldID.getText())) {
		ID = Integer.parseInt(textFieldID.getText());
	}

	
	//Crea un tipo de BO u otro en función del tipo que sea el comboBox, pero lo encapsula en BOProducto
	BOProducto bo;
	
	

	bo.setId_proveedor(ID);
	bo.setNombre(textFieldNombre.getText());
	bo.setNif(textFieldNif.getText());
	bo.setTelefono(textFieldTelefono.getText());
	bo.setDisponible(true);
	

	
	return bo;
}*/
	
	private void rellenarTabla(List<Pedido> lista) {
		tabla = new Tabla();
/*
 * 	protected int id_pedido;
	protected int id_proveedor;
	protected String fecha_realizado;
	protected String fecha_entregado;
	protected String fecha_cancelado;
 */
		tabla.addColumn("ID_PEDIDO");
		tabla.addColumn("ID_PROVEEDOR");
		tabla.addColumn("FECHA_REALIZADO");
		tabla.addColumn("FECHA_ENTREGADO");
		tabla.addColumn("FECHA_CANCELADO");

		for (int i = 0; i < lista.size(); i++) {

			fila = new Vector();
			Pedido bo = lista.get(i);
			fila.add(bo.getId_pedido());
			fila.add(bo.getProveedor().getId_proveedor());
			fila.add(bo.getFechaRealizado());
			fila.add(bo.getFechaEntregado());
			fila.add(bo.getFechaCancelado());

			tabla.addRow(fila);
		}

		tbPedidos.setModel(tabla);
		
	}

	public void modificarFormulario(Object objeto) {

		TPedido pedido = (TPedido) objeto;
		
		textFieldID.setText(Integer.toString(pedido.getId_proveedor()));
		/*
		textFieldNombre.setText(boProveedor.getNombre());
		textFieldStock.setText(Integer.toString(boProveedor.getStock()));
		textFieldNif.setText(boProveedor.getNif());
		textFieldTelefono.setText(boProveedor.getTelefono());
	*/

	}
	
	public void actualizarTablaProductos(Object objeto) {
		
		List<TPedidoProducto> lista = (List<TPedidoProducto>)objeto;
		
		tabla = new Tabla();

				tabla.addColumn("ID_PRODUCTO");
				tabla.addColumn("PRECIO");
				tabla.addColumn("CANTIDAD");

				for (int i = 0; i < lista.size(); i++) {

					fila = new Vector();
					TPedidoProducto bo = lista.get(i);
					fila.add(bo.getProducto().getId_producto());
					fila.add(bo.getPrecio());
					fila.add(bo.getCantidad());

					tabla.addRow(fila);
				}

				tbProductos.setModel(tabla);
		
	}
	
	public void limpiarFormulario()
	{	
		textFieldID.setText("");
		textFieldNombre.setText("");
		textFieldNif.setText("");
		textFieldTelefono.setText("");
	}
	
	private boolean mensajeConfirmacionSiNo(String msj, String cabecera) {	
		return (JOptionPane.showConfirmDialog(this, msj, cabecera, JOptionPane.YES_NO_OPTION) == 0);

	}
	
	public float redondear(float numero){		
		return (float)Math.rint(numero*100)/100;		
	}

	
	
	

}

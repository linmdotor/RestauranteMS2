package presentacion.ventanas.proveedor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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
import negocio.proveedor.transfer.TProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;
import presentacion.ventanas.VentanaError;

@SuppressWarnings("serial")
public class VentanaProveedor extends JFrame{
	
	private static VentanaProveedor ventana; //instancia singleton

	private JTextField textFieldID;
	private JTextField textFieldNombre;
	private JTextField textFieldNif;
	private JTextField textFieldTelefono;

	private Tabla tabla;
	private JTable tbProveedores;
	private Vector fila;

	private JScrollPane scrollPanel;

	// Mutadores y Accedentes

	public JTable getTbProveedores() {
		return tbProveedores;
	}

	public void setTbProveedores(JTable tbProveedores) {
		this.tbProveedores = tbProveedores;
	}
	
	//GetInstance
	public static VentanaProveedor obtenerInstancia() {

		if (ventana == null)
			ventana = new VentanaProveedor();

		return ventana;
	}
		
	//Constructor
	public VentanaProveedor()
	{
		setTitle("Gestion de Proveedores");
		setResizable(false);
		setVisible(false);
		setSize(798, 390);	
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


		// -------- PANEL PRINCIPAL --------------
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(574, 5, 215, 350);
		getContentPane().add(panelFormulario);
		panelFormulario.setLayout(null);

		// ------------- ID -----------------------
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(64, 46, 17, 16);
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
		panelFormulario.add(textFieldNombre);
		textFieldNombre.setColumns(10);

		//------------- NIF -------------------
		JLabel lblNif = new JLabel("NIF:");
		lblNif.setBounds(58, 107, 20, 16);
		panelFormulario.add(lblNif);

		textFieldNif = new JTextField();
		textFieldNif.setBounds(83, 103, 118, 20);
		panelFormulario.add(textFieldNif);
		textFieldNif.setColumns(10);

		// ------------- TELEFONO ------------------
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setBounds(25, 135, 60, 16);
		panelFormulario.add(lblTelefono);

		textFieldTelefono = new JTextField();
		textFieldTelefono.setBounds(83, 132, 118, 20);
		panelFormulario.add(textFieldTelefono);
		textFieldTelefono.setColumns(9);


		JLabel lblAadirNuevoProveedor = new JLabel("Añadir o Modificar Proveedor:");
		lblAadirNuevoProveedor.setFont(new Font("Dialog", Font.BOLD, 13));
		lblAadirNuevoProveedor.setBounds(12, 12, 192, 16);
		panelFormulario.add(lblAadirNuevoProveedor);
		lblAadirNuevoProveedor.setHorizontalAlignment(SwingConstants.CENTER);

		// ------- ALTA PROVEEDOR ----------------------
		JButton btnAadirProveedor = new JButton("Añadir Proveedor");
		btnAadirProveedor.setBounds(9, 276, 192, 26);
		panelFormulario.add(btnAadirProveedor);
		btnAadirProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ALTA_PROVEEDOR, obtenerProveedor() );
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PROVEEDORES, null);

			}
		});

		// ------- MODIFICAR PROVEEDOR ----------------------
		JButton btnModificarProveedor = new JButton("Modificar Proveedor");
		btnModificarProveedor.setBounds(9, 310, 192, 26);
		panelFormulario.add(btnModificarProveedor);
		btnModificarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_PROVEEDOR, obtenerProveedor());
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PROVEEDORES, null);

			}
		});

		JPanel panelLista = new JPanel();
		panelLista.setBounds(10, 5, 564, 353);
		getContentPane().add(panelLista);
		panelLista.setLayout(null);

		// ------- BAJA PROVEEDOR ----------------------
		JButton btnEliminarProveedor = new JButton("Dar de baja Proveedor Seleccionado (Quitar DISPONIBLE)");
		btnEliminarProveedor.setBounds(0, 310, 564, 26);
		panelLista.add(btnEliminarProveedor);
		btnEliminarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(mensajeConfirmacionSiNo("¿Realmente desea dar de baja el proveedor?", "Confirmar dar de baja Producto"))
				{
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.BAJA_PROVEEDOR, getTbProveedores().getSelectedRow() );
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PROVEEDORES, null);

				}
			}
		});

		// ------- BORRAR FORMULARIO ----------------------
		JButton btnBorrarFormulario = new JButton("Borrar Formulario");
		btnBorrarFormulario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				limpiarFormulario();

			}
		});
		btnBorrarFormulario.setFont(new Font("Dialog", Font.BOLD, 9));
		btnBorrarFormulario.setBounds(83, 162, 118, 22);
		panelFormulario.add(btnBorrarFormulario);


		// ------- PRODUCTOS DE PROVEEDOR ----------------------
		JButton btnGestProductosProveedor = new JButton("<html><p>Gestionar Productos</p><p>del Proveedor</p></html>");
		btnGestProductosProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if ( getTbProveedores().getSelectedRow() != -1 ) //hay alguna fila seleccionada
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.INICIAR_VISTA_PRODUCTOS_DE_PROVEEDOR, getTbProveedores().getSelectedRow() );
				else
					new VentanaError("Debe seleccionar primero un proveedor.");
			}
		});

		btnGestProductosProveedor.setFont(new Font("Dialog", Font.BOLD, 12));
		btnGestProductosProveedor.setBounds(12, 200, 189, 50);
		panelFormulario.add(btnGestProductosProveedor);	
		

		// --------- CONSTRUCCIÓN TABLA -------------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 12, 564, 291);
		panelLista.add(scrollPane);

		tbProveedores = new JTable();
		scrollPane.setViewportView(tbProveedores);
		tbProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProveedores.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (getTbProveedores().getSelectedRow() != -1) //hay alguna fila seleccionada
						{
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PROVEEDOR, getTbProveedores().getSelectedRow() );	
						}
					}
				});

	}

	// Metodos

	@SuppressWarnings("unchecked")
	public void actualizar(Object object) {

		if (object == null)
			rellenarTabla(new ArrayList<TProveedor>());
		else			
			rellenarTabla((List<TProveedor>) object);

		setVisible(true);
		repaint();

	}

	public void rellenarTabla(List<TProveedor> lista) {

		tabla = new Tabla();

		tabla.addColumn("ID");
		tabla.addColumn("NOMBRE");
		tabla.addColumn("NIF");
		tabla.addColumn("TELEFONO");
		tabla.addColumn("DISPONIBLE");

		for (int i = 0; i < lista.size(); i++) {

			fila = new Vector();
			TProveedor prov = lista.get(i);
			fila.add(prov.getId_proveedor());
			fila.add(prov.getNombre());
			fila.add(prov.getNIF());
			fila.add(prov.getTelefono());
			fila.add(prov.isDisponible());

			tabla.addRow(fila);
		}

		tbProveedores.setModel(tabla);
		
		tbProveedores.getColumnModel().getColumn(0).setMaxWidth(60);

	}

	public TProveedor obtenerProveedor() {

		int ID = -1;

		ComprobadorEnteros comprobadorEntero = new ComprobadorEnteros();

		if (comprobadorEntero.isNumeric(textFieldID.getText())) {
			ID = Integer.parseInt(textFieldID.getText());
		}	

		TProveedor tProveedor = new TProveedor();

		tProveedor.setId_proveedor(ID);
		tProveedor.setNombre(textFieldNombre.getText());
		tProveedor.setNIF(textFieldNif.getText());
		tProveedor.setTelefono(textFieldTelefono.getText());
		tProveedor.setDisponible(true);

		return tProveedor;

	}

	public void modificarFormulario(Object objeto) {

		TProveedor tProveedor = (TProveedor) objeto;

		textFieldID.setText(Integer.toString(tProveedor.getId_proveedor()));
		textFieldNombre.setText(tProveedor.getNombre());
		textFieldNif.setText(tProveedor.getNIF());
		textFieldTelefono.setText(tProveedor.getTelefono());

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

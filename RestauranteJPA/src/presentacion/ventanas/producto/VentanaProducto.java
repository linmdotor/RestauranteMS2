/**
 * 
 * Vista del Almacen de Platos
 * 
 * @author Marco González, Juan Carlos * @author Martínez Dotor, Jesús * @author Picado Álvarez, María * @author Rojas Morán, Amy Alejandra * @author Serrano Álvarez, José * @author Vargas Paredes, Jhonny
 *  
 */

package presentacion.ventanas.producto;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
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

import negocio.ComprobadorEnteros;
import negocio.ComprobadorFloat;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;
import presentacion.ventanas.proveedor.VentanaProveedor;
import negocio.producto.Producto;
import negocio.producto.ProductoNoPerecedero;
import negocio.producto.ProductoPerecedero;
import negocio.producto.EnumTipoProducto;
import negocio.producto.TProducto;
import negocio.producto.TProductoNoPerecedero;
import negocio.producto.TProductoPerecedero;

@SuppressWarnings("serial")
public class VentanaProducto extends JFrame {

	private static VentanaProducto ventana; //instancia singleton

	private JTextField textFieldID;
	private JTextField textFieldNombre;
	private JTextField textFieldStock;
	
	private JLabel lblFecha;
	private JLabel lblRecomendaciones;
	private JTextField textFieldFecha;
	private JTextField textFieldRecomendaciones;
	
	private JComboBox comboBoxTipo;

	private Tabla tabla;
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

	//GetInstance
		public static VentanaProducto obtenerInstancia() {

			if (ventana == null)
				ventana = new VentanaProducto();

			return ventana;
		}
			
	//Constructor
	private VentanaProducto() {
		
		setTitle("Gestión de Productos");
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
		lblId.setBounds(64, 46, 14, 16);
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

		//------------- STOCK -------------------
		JLabel lblStock = new JLabel("Stock:");
		lblStock.setBounds(42, 107, 36, 16);
		panelFormulario.add(lblStock);

		textFieldStock = new JTextField();
		textFieldStock.setBounds(83, 103, 118, 20);
		panelFormulario.add(textFieldStock);
		textFieldStock.setColumns(10);
		
		// ------------- TIPO ------------------
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setBounds(51, 135, 27, 16);
		panelFormulario.add(lblTipo);
		
		comboBoxTipo = new JComboBox();
		comboBoxTipo.setBounds(83, 132, 118, 25);
		panelFormulario.add(comboBoxTipo);
		comboBoxTipo.setModel(new DefaultComboBoxModel(EnumTipoProducto.values()));		
		
		comboBoxTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comboBoxTipo.getSelectedItem().toString().compareTo(EnumTipoProducto.Perecedero.toString()) == 0)
				{				
					lblFecha.setVisible(true);
					textFieldFecha.setText("");
					textFieldFecha.setVisible(true);
					textFieldFecha.setEnabled(true);
					
					lblRecomendaciones.setVisible(false);
					textFieldRecomendaciones.setEnabled(false);
					textFieldRecomendaciones.setVisible(false);
				}
				else if(comboBoxTipo.getSelectedItem().toString().compareTo(EnumTipoProducto.NO_Perecedero.toString()) == 0)
				{
					lblFecha.setVisible(false);
					textFieldFecha.setVisible(false);
					textFieldFecha.setEnabled(false);
						
					lblRecomendaciones.setVisible(true);
					textFieldRecomendaciones.setText("");
					textFieldRecomendaciones.setEnabled(true);
					textFieldRecomendaciones.setVisible(true);
				}
			}
		});
		
		// ------------- FECHA CADUCIDAD ---------
		lblFecha = new JLabel("Fecha Caducidad:");
		lblFecha.setBounds(12, 164, 100, 16);
		panelFormulario.add(lblFecha);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setBounds(12, 186, 189, 20);
		panelFormulario.add(textFieldFecha);
		textFieldFecha.setColumns(10);		
		
		// ------------- RECOMENDACIONES -----------
		lblRecomendaciones = new JLabel("Recomendaciones:");
		lblRecomendaciones.setHorizontalAlignment(SwingConstants.LEFT);
		lblRecomendaciones.setBounds(12, 163, 189, 16);
		panelFormulario.add(lblRecomendaciones);
		
		textFieldRecomendaciones = new JTextField();
		textFieldRecomendaciones.setBounds(12, 186, 188, 20);
		panelFormulario.add(textFieldRecomendaciones);
		textFieldRecomendaciones.setColumns(100);		
		
		comboBoxTipo.setSelectedIndex(0);

		JLabel lblAadirNuevoProducto = new JLabel("Añadir o Modificar Producto:");
		lblAadirNuevoProducto.setFont(new Font("Dialog", Font.BOLD, 13));
		lblAadirNuevoProducto.setBounds(12, 12, 192, 16);
		panelFormulario.add(lblAadirNuevoProducto);
		lblAadirNuevoProducto.setHorizontalAlignment(SwingConstants.CENTER);
		
		// ------- ALTA PRODUCTO ----------------------
		JButton btnAadirProducto = new JButton("Añadir Producto");
		btnAadirProducto.setBounds(9, 276, 192, 26);
		panelFormulario.add(btnAadirProducto);
		btnAadirProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ALTA_PRODUCTO, obtenerProducto());
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS, null);
				
			}
		});

		// ------- MODIFICAR PRODUCTO ----------------------
		JButton btnModificarProducto = new JButton("Modificar Producto");
		btnModificarProducto.setBounds(9, 310, 192, 26);
		panelFormulario.add(btnModificarProducto);
		btnModificarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_PRODUCTO, obtenerProducto());
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS, null);

			}
		});
		
		JPanel panelLista = new JPanel();
		panelLista.setBounds(10, 5, 564, 353);
		getContentPane().add(panelLista);
		panelLista.setLayout(null);

		// ------- BAJA PRODUCTO ----------------------
		JButton btnEliminarProducto = new JButton("Dar de baja Producto Seleccionado (Quitar DISPONIBLE)");
		btnEliminarProducto.setBounds(0, 310, 564, 26);
		panelLista.add(btnEliminarProducto);
		btnEliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(mensajeConfirmacionSiNo("¿Realmente desea dar de baja el producto?", "Confirmar dar de baja Producto"))
				{
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.BAJA_PRODUCTO, getTbProductos().getSelectedRow() );
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS, null);

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
		btnBorrarFormulario.setBounds(83, 229, 118, 22);
		panelFormulario.add(btnBorrarFormulario);

		
		// --------- CONSTRUCCIÓN TABLA -------------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 12, 564, 291);
		panelLista.add(scrollPane);

		tbProductos = new JTable();
		scrollPane.setViewportView(tbProductos);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductos.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (getTbProductos().getSelectedRow() != -1) //hay alguna fila seleccionada
						{
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO, getTbProductos().getSelectedRow() );	
						}
					}
				});

	}

	// Metodos

	public void actualizar(Object object) {

		if (object == null)
			rellenarTabla(new ArrayList<TProducto>());
		else			
			rellenarTabla((List<TProducto>) object);

		setVisible(true);
		repaint();

	}

	public void rellenarTabla(List<TProducto> object) {
		
		tabla = new Tabla();

		tabla.addColumn("ID");
		tabla.addColumn("NOMBRE");
		tabla.addColumn("STOCK");
		tabla.addColumn("CADUCIDAD");
		tabla.addColumn("RECOMENDACIONES");
		tabla.addColumn("DISPONIBLE");

		for (int i = 0; i < object.size(); i++) {

			fila = new Vector();
			TProducto prod = object.get(i);
			fila.add(prod.getId_producto());
			fila.add(prod.getNombre());
			fila.add(prod.getStock());
			
			if(prod instanceof TProductoPerecedero)
			{
				TProductoPerecedero p = (TProductoPerecedero) prod;
				fila.add(p.getFechaCaducidad());
				fila.add("---");
			}
			else if(prod instanceof TProductoNoPerecedero)
			{
				TProductoNoPerecedero np = (TProductoNoPerecedero) prod;
				fila.add("---");
				fila.add(np.getRecomendaciones());
			}
			
			fila.add(prod.isDisponible());
			
			tabla.addRow(fila);
		}

		tbProductos.setModel(tabla);

	}

	public TProducto obtenerProducto() {

		int ID = -1;
		int stock = -1;

		ComprobadorEnteros comprobadorEntero = new ComprobadorEnteros();

		if (comprobadorEntero.isNumeric(textFieldID.getText())) {
			ID = Integer.parseInt(textFieldID.getText());
		}

		if (comprobadorEntero.isNumeric(textFieldStock.getText())) {
			stock = Integer.parseInt(textFieldStock.getText());
		}
		
		//Crea un tipo de transfer u otro en función del tipo que sea el comboBox, pero lo encapsula en TProducto
		TProducto tProducto;
		
		if(comboBoxTipo.getSelectedItem().toString().compareTo(EnumTipoProducto.Perecedero.toString()) == 0)
		{	
			TProductoPerecedero t_aux = new TProductoPerecedero();
			t_aux.setId_producto(ID);
			t_aux.setNombre(textFieldNombre.getText());
			t_aux.setStock(stock);
			t_aux.setFechaCaducidad(textFieldFecha.getText());
			t_aux.setDisponible(true);
			
			tProducto = t_aux;
		}
		else //if (comboBoxTipo.getSelectedItem().toString().compareTo(EnumTipoProducto.NO_Perecedero.toString()) != 0)
		{
			TProductoNoPerecedero t_aux = new TProductoNoPerecedero();
			t_aux.setId_producto(ID);
			t_aux.setNombre(textFieldNombre.getText());
			t_aux.setStock(stock);
			t_aux.setRecomendaciones(textFieldRecomendaciones.getText());
			t_aux.setDisponible(true);

			tProducto = t_aux;
		}

		return tProducto;
	}

	public void modificarFormulario(Object objeto) {

		TProducto tProducto = (TProducto) objeto;
		
		textFieldID.setText(Integer.toString(tProducto.getId_producto()));
		textFieldNombre.setText(tProducto.getNombre());
		textFieldStock.setText(Integer.toString(tProducto.getStock()));
		
		if(tProducto instanceof TProductoPerecedero)
		{
			TProductoPerecedero tProducto_aux = (TProductoPerecedero) tProducto;
			
			comboBoxTipo.setSelectedItem(EnumTipoProducto.Perecedero);
			textFieldFecha.setText(tProducto_aux.getFechaCaducidad());
		}
		if(tProducto instanceof TProductoNoPerecedero)
		{
			TProductoNoPerecedero tProducto_aux = (TProductoNoPerecedero) tProducto;
			comboBoxTipo.setSelectedItem(EnumTipoProducto.NO_Perecedero);
			textFieldRecomendaciones.setText(tProducto_aux.getRecomendaciones());
		}

	}
	
	public void limpiarFormulario()
	{
		textFieldID.setText("");
		textFieldNombre.setText("");
		textFieldStock.setText("");
		comboBoxTipo.setSelectedIndex(0);
		textFieldFecha.setText("");
		textFieldRecomendaciones.setText("");
	}
	
	private boolean mensajeConfirmacionSiNo(String msj, String cabecera) {	
		return (JOptionPane.showConfirmDialog(this, msj, cabecera, JOptionPane.YES_NO_OPTION) == 0);

	}
	
	public float redondear(float numero){		
		return (float)Math.rint(numero*100)/100;		
	}

}

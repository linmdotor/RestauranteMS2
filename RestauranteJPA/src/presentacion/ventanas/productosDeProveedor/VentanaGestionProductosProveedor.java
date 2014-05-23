package presentacion.ventanas.productosDeProveedor;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.Producto;
import negocio.producto.TProducto;
import negocio.productosdeproveedor.ProductosDeProveedor;
import negocio.productosdeproveedor.TProductoDeProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;
import presentacion.ventanas.proveedor.VentanaProveedor;

@SuppressWarnings("serial")
public class VentanaGestionProductosProveedor extends JFrame {
	
	private static VentanaGestionProductosProveedor ventana; //instancia singleton
	
	private JTextField textFieldPrecio;
	private JTextField textFieldID_Proveedor;
	private Tabla tabla;
	private JTable tbProductosProveedor;
	private JTable tbProductosTotales;
	
	private Vector fila;
	private JScrollPane scrollPanel;
	
	//Mutadores y Accedentes
	
	public JTable getTbProveedores()
	{
		return tbProductosProveedor;
	}
	
	public void setTbProveedores(JTable tbProveedores)
	{
		this.tbProductosProveedor = tbProveedores;
	}
	
	public JTable tbProductos()
	{
		return tbProductosTotales;
	}
	
	public void setTbProductos(JTable tbProductos)
	{
		this.tbProductosTotales = tbProductos;
	}
	
	//GetInstance
		public static VentanaGestionProductosProveedor obtenerInstancia() {

			if (ventana == null)
				ventana = new VentanaGestionProductosProveedor();

			return ventana;
		}
			
		//Constructor
	private VentanaGestionProductosProveedor()
	{
		setTitle("Gestion Productos de Proveedores");
		setResizable(false);
		
		setSize(798, 390);
		setVisible(false);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		// -------- PANEL PRINCIPAL --------------
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(574, 5, 215, 350);
		getContentPane().add(panelFormulario);
		panelFormulario.setLayout(null);
		
		// ------------- PRECIO -----------------------
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(31, 113, 40, 16);
		panelFormulario.add(lblPrecio);
		
		textFieldPrecio = new JTextField();
		textFieldPrecio.setBounds(83, 111, 118, 20);
		textFieldPrecio.setEditable(true);
		panelFormulario.add(textFieldPrecio);
		textFieldPrecio.setColumns(10);
		
		//-------------- ID PROV -------------------------
		JLabel lblIdProveedor = new JLabel("ID Proveedor:");
		lblIdProveedor.setBounds(44, 25, 76, 16);
		panelFormulario.add(lblIdProveedor);
		
		textFieldID_Proveedor = new JTextField();
		textFieldID_Proveedor.setBounds(130, 25, 40, 20);
		textFieldID_Proveedor.setEditable(false);
		panelFormulario.add(textFieldID_Proveedor);
		textFieldID_Proveedor.setColumns(10);
		
		// ------- MODIFICAR PRECIO ----------------------
		JButton btnModiPrecio = new JButton("Modificar Precio");
		btnModiPrecio.setBounds(59, 150, 142, 26);
		panelFormulario.add(btnModiPrecio);
		btnModiPrecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_PRODUCTO_PROVEEDOR, obtenerProductoProveedor(true));
				actualizar(Integer.parseInt(textFieldID_Proveedor.getText()));	
			}
		});
	
		
		// ------- AÑADIR PRODUCTO ----------------------
		JButton btnAnadirProducto = new JButton("Añadir Producto");
		btnAnadirProducto.setBounds(9, 265, 192, 26);
		panelFormulario.add(btnAnadirProducto);
		btnAnadirProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ANADIR_PRODUCTO_PROVEEDOR, obtenerProductoProveedor(false));
				actualizar(Integer.parseInt(textFieldID_Proveedor.getText()));
			}

		});
		
		// ------- ELIMINAR PRODUCTO ----------------------
		JButton btnEliminarProducto = new JButton("Eliminar Producto");
		btnEliminarProducto.setBounds(9, 300, 192, 26);
		panelFormulario.add(btnEliminarProducto);
		btnEliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ELIMINAR_PRODUCTO_PROVEEDOR, obtenerProductoProveedor(true));
				actualizar(Integer.parseInt(textFieldID_Proveedor.getText()));
			}
		});
		
		
		JPanel panelLista = new JPanel();
		panelLista.setBounds(10, 5, 564, 353);
		getContentPane().add(panelLista);
		panelLista.setLayout(null);
		
		// --------- CONSTRUCCIÓN TABLA PRODUCTOS PROVEEDOR-------------
		JScrollPane scrollPanePProv = new JScrollPane();
		scrollPanePProv.setBounds(0, 35, 280, 291);
		panelLista.add(scrollPanePProv);

		JLabel lblPedidos = new JLabel("Productos del proveedor actuales");
		lblPedidos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPedidos.setBounds(45, 12, 213, 18);
		panelLista.add(lblPedidos);
		lblPedidos.setHorizontalAlignment(SwingConstants.CENTER);

		
		
		tbProductosProveedor = new JTable();
		scrollPanePProv.setViewportView(tbProductosProveedor);
		tbProductosProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductosProveedor.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (getTbProveedores().getSelectedRow() != -1)
						{
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR, Double.parseDouble(tbProductosProveedor.getValueAt(tbProductosProveedor.getSelectedRow(), 1).toString()));
						}
					}
		});
		// --------- CONSTRUCCIÓN TABLA TODOS LOS PRODUCTOS-------------
		JScrollPane scrollPaneProd = new JScrollPane();
		scrollPaneProd.setBounds(285, 35, 280, 291);
		panelLista.add(scrollPaneProd);

		JLabel lblProductos = new JLabel("Productos disponibles");
		lblProductos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblProductos.setBounds(350, 12, 192, 16);
		panelLista.add(lblProductos);
		lblProductos.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbProductosTotales = new JTable();
		scrollPaneProd.setViewportView(tbProductosTotales);
		tbProductosTotales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductosTotales.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (tbProductosTotales.getSelectedRow() != -1)
						{							
							limpiarFormulario();
						}
					}
				});

	}
	
	public void actualizar(Object objeto) { //Este objeto será el ID del proveedor

		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, objeto);
		
		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_TOTALES, null);	
		
		textFieldID_Proveedor.setText(Integer.toString((int)objeto));
		
		setVisible(true);
		repaint();
			
	}
	
	/*Obtenemos un transfer con los datos de pantalla.
	 * Si se indica TRUE, coge el dato de los productos que tiene el proveedor en su lista (modificar y eliminar)
	 * Si se indica FALSE, coge el dato de los productos de la lista de TODOS los productos (añadir)
	 */
	private TProductoDeProveedor obtenerProductoProveedor(boolean cogerProductoDELProveedor) {
		
		TProductoDeProveedor tProductoDeProveedor = new TProductoDeProveedor();
		
		tProductoDeProveedor.setProveedor(Integer.parseInt(textFieldID_Proveedor.getText()));

		if(cogerProductoDELProveedor)
		{
			tProductoDeProveedor.setProducto(Integer.parseInt(tbProductosProveedor.getValueAt(tbProductosProveedor.getSelectedRow(), 0).toString()));
		}
		else
		{
			tProductoDeProveedor.setProducto(Integer.parseInt(tbProductosTotales.getValueAt(tbProductosTotales.getSelectedRow(), 0).toString()));
		}
		
		if (textFieldPrecio.getText().length() > 0)
			tProductoDeProveedor.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
		else
			tProductoDeProveedor.setPrecio(0);
		
		return tProductoDeProveedor;
		
	}
	
	public void rellenarTablaProductosActuales(Object object) {
		
		if (object != null){
		
			List<TProductoDeProveedor> listaProductos = (List<TProductoDeProveedor>) object;
			
			tabla = new Tabla();
			
			tabla.addColumn("ID");
			tabla.addColumn("PRECIO");
			
			for (int i = 0; i < listaProductos.size(); i++) {
		
				fila = new Vector();
				TProductoDeProveedor producto_prov = listaProductos.get(i);
				fila.add(producto_prov.getProducto());
				fila.add(producto_prov.getPrecio());		
				
				tabla.addRow(fila);
			}
		
			tbProductosProveedor.setModel(tabla);
		
		}
	
	}	
	
	public void rellenarTablaProductosTotales(Object object) {
		
		if (object != null){
		
			List<TProducto> listaProductos = (List<TProducto>) object;
			
			tabla = new Tabla();
			
			tabla.addColumn("ID");
			tabla.addColumn("NOMBRE");
			
			for (int i = 0; i < (listaProductos.size()); i++) {
		
				fila = new Vector();
				TProducto producto = (listaProductos.get(i));
				fila.add(producto.getId_producto());
				fila.add(producto.getNombre());
				
				tabla.addRow(fila);
			}
		
			tbProductosTotales.setModel(tabla);
		
		}
	
	}

	public void cambiarPrecio(Object objeto) {
		
		textFieldPrecio.setText(Double.toString((double) objeto));
		
	}

	public void limpiarFormulario() {
		textFieldPrecio.setText("");
		
	}	

}

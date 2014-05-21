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
import negocio.productosdeproveedor.ProductosDeProveedor;
import negocio.productosdeproveedor.TProductoDeProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;

public class VentanaGestionProductosProveedor extends JFrame {
	
	private JTextField textFieldPrecio;
	private Tabla tabla;
	private JTable tbProductosProveedor;
	private JTable tbProductosTotales;
	JLabel idProveedor;
	private int proveedor;

	private Vector fila;
	private JScrollPane scrollPanel;
	
	//Mutadores y Accedentes
	
	public JLabel getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(JLabel idProveedor) {
		this.idProveedor = idProveedor;
	}
	
	public int getProveedor() {
		return proveedor;
	}

	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}
	
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
	
	public VentanaGestionProductosProveedor(Object objeto)
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
		
		
		// ------- MODIFICAR PRECIO ----------------------
		JButton btnModiPrecio = new JButton("Modificar Precio");
		btnModiPrecio.setBounds(9, 235, 192, 26);
		panelFormulario.add(btnModiPrecio);
		btnModiPrecio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_PRODUCTO_PROVEEDOR, obtenerProveedor());
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, proveedor);				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_TOTALES, null);	
			}
		});
	
		
		// ------- AÑADIR PRODUCTO ----------------------
		JButton btnAnadirProducto = new JButton("A\u00F1adir Producto");
		btnAnadirProducto.setBounds(9, 270, 192, 26);
		panelFormulario.add(btnAnadirProducto);
		btnAnadirProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.AÑADIR_PRODUCTO_PROVEEDOR, obtenerProductoProveedor());
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, proveedor);				
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_TOTALES, null);	
			}

		});
		
		// ------- ELIMINAR PRODUCTO ----------------------
		JButton btnEliminarProducto = new JButton("Eliminar Producto");
		btnEliminarProducto.setBounds(9, 305, 192, 26);
		panelFormulario.add(btnEliminarProducto);
		
		JLabel lblIdProveedor = new JLabel("ID Proveedor:");
		lblIdProveedor.setBounds(44, 25, 76, 16);
		panelFormulario.add(lblIdProveedor);
		
		idProveedor= new JLabel(Integer.toString(proveedor));
		idProveedor.setBounds(132, 25, 55, 16);
		panelFormulario.add(idProveedor);
		btnEliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ALTA_PROVEEDOR, obtenerProveedor());
				//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PROVEEDORES, null);
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
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR, obtenerProveedor());
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

						if (getTbProveedores().getSelectedRow() != -1)
						{
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_FORMULARIO_PROVEEDOR, getTbProveedores().getSelectedRow() );	
						}
					}
				});

		actualizar(objeto);

	}
	
	public void actualizar(Object objeto) {
		
		if (objeto != null){
		
			ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR, objeto);
			
			ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_TOTALES, null);	
			
			idProveedor.setText(Integer.toString(proveedor));
			
		}		
	
		repaint();

	}
	
	private TProductoDeProveedor obtenerProductoProveedor() {
		
		TProductoDeProveedor tProductoDeProveedor = new TProductoDeProveedor();
		
		if (textFieldPrecio.getText().length() > 0)
			tProductoDeProveedor.setPrecio(Integer.parseInt(textFieldPrecio.getText()));
		else
			tProductoDeProveedor.setPrecio(0);
		
		tProductoDeProveedor.setProveedor(proveedor);
		
		tProductoDeProveedor.setProducto(tbProductosTotales.getSelectedRow());
		
		return tProductoDeProveedor;
		
	}
	
	private TProductoDeProveedor obtenerProveedor() {
		
		TProductoDeProveedor tProductoDeProveedor = new TProductoDeProveedor();
		
		if (textFieldPrecio.getText().length() > 0)
			tProductoDeProveedor.setPrecio(Double.parseDouble(textFieldPrecio.getText()));
		else
			tProductoDeProveedor.setPrecio(0);		
		
		tProductoDeProveedor.setProveedor(proveedor);
		
		tProductoDeProveedor.setProducto(tbProductosProveedor.getSelectedRow());
		
		return tProductoDeProveedor;
		
	}
	
	public void rellenarTablaProductosActuales(Object object) {
		
		if (object != null){
		
			List<ProductosDeProveedor> listaProductos = (List<ProductosDeProveedor>) object;
			
			tabla = new Tabla();
			
			tabla.addColumn("ID");
			tabla.addColumn("PRECIO");
			
			for (int i = 0; i < listaProductos.size(); i++) {
		
				fila = new Vector();
				ProductosDeProveedor producto = listaProductos.get(i);
				fila.add(producto.getProducto().getId_producto());
				fila.add(producto.getPrecio());		
				
				tabla.addRow(fila);
			}
		
			tbProductosProveedor.setModel(tabla);
		
		}
	
	}	
	
	public void rellenarTablaProductosTotales(Object object) {
		
		if (object != null){
		
			List<Producto> listaProductos = (List<Producto>) object;
			
			tabla = new Tabla();
			
			tabla.addColumn("ID");
			tabla.addColumn("NOMBRE");
			
			for (int i = 0; i < (listaProductos.size()); i++) {
		
				fila = new Vector();
				Producto producto = (listaProductos.get(i));
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
}

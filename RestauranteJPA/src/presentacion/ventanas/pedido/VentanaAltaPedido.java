package presentacion.ventanas.pedido;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
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
import javax.swing.table.DefaultTableModel;

import negocio.ComprobadorEnteros;
import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedido;
import negocio.producto.businessobject.Producto;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.productosdeproveedor.businessobject.ProductoDeProveedor;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import negocio.proveedor.businessobject.Proveedor;
import negocio.proveedor.transfer.TProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;


public class VentanaAltaPedido extends JFrame {
	
	private static VentanaAltaPedido ventana; //instancia singleton
	
	private static DefaultTableModel modelopedido;
	private static JTextField textFieldCantidad;
	private Tabla tabla;
	private JTable tbProductosProveedor;
	private static JTable tbProductosPedido;
	private JTextField txtIDProveedor;
	private static JTextField txtPrecioTotal;
	private static Vector fila;
	private JScrollPane scrollPanel;
	
	private Pedido pedido;

	//Mutadores y Accedentes		
	
	public JTable getTbProveedores()
	{
		return tbProductosProveedor;
	}
	
	public JTextField getTextFieldCantidad() {
		return textFieldCantidad;
	}

	public void setTextFieldCantidad(JTextField textFieldCantidad) {
		this.textFieldCantidad = textFieldCantidad;
	}

	public static JTable getTbProductosPedido() {
		return tbProductosPedido;
	}

	public void setTbProductosPedido(JTable tbProductosPedido) {
		this.tbProductosPedido = tbProductosPedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public void setTbProveedores(JTable tbProveedores)
	{
		this.tbProductosProveedor = tbProveedores;
	}
	
	public JTable getTbProductosProveedor() {
		return tbProductosProveedor;
	}

	public void setTbProductosProveedor(JTable tbProductosProveedor) {
		this.tbProductosProveedor = tbProductosProveedor;
	}
	
	//GetInstance
	public static VentanaAltaPedido obtenerInstancia() {

		if (ventana == null)
			ventana = new VentanaAltaPedido();

		return ventana;
	}
	
	//Constructor
		
	public VentanaAltaPedido(){
		setTitle("Gestion Productos de Pedido");
		setResizable(false);
		
		setSize(773, 423);
		setVisible(true);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		
		JPanel panelLista = new JPanel();
		panelLista.setBounds(12, 48, 743, 328);
		getContentPane().add(panelLista);
		panelLista.setLayout(null);
		
		// --------- CONSTRUCCIÓN TABLA PRODUCTOS PROVEEDOR-------------
		JScrollPane scrollPanePProv = new JScrollPane();
		scrollPanePProv.setBounds(0, 35, 280, 291);
		panelLista.add(scrollPanePProv);

		JLabel lblPedidos = new JLabel("Productos del pedido actuales");
		lblPedidos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPedidos.setBounds(32, 12, 213, 18);
		panelLista.add(lblPedidos);
		lblPedidos.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbProductosPedido = new JTable();
		scrollPanePProv.setViewportView(tbProductosPedido);
		tbProductosPedido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductosPedido.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {
					if (arg0.getValueIsAdjusting())
					{
						if (getTbProductosPedido().getSelectedRow() != -1)
						{							
							textFieldCantidad.setText(Integer.toString((int)getTbProductosPedido().getValueAt(getTbProductosPedido().getSelectedRow(), 3)));							
						}
					}
					}
		});
		// --------- CONSTRUCCIÓN TABLA TODOS LOS PRODUCTOS-------------
		JScrollPane scrollPaneProd = new JScrollPane();
		scrollPaneProd.setBounds(455, 35, 280, 291);
		panelLista.add(scrollPaneProd);

		JLabel lblProductos = new JLabel("Productos del Proveedor disponibles");
		lblProductos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblProductos.setBounds(472, 12, 233, 18);
		panelLista.add(lblProductos);
		lblProductos.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbProductosProveedor = new JTable();
		scrollPaneProd.setViewportView(tbProductosProveedor);
		tbProductosProveedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// ------- AÑADIR PRODUCTO ----------------------
		JButton btnAnadirProducto = new JButton("A\u00F1adir Producto");
		btnAnadirProducto.setBounds(292, 87, 151, 26);
		panelLista.add(btnAnadirProducto);
		btnAnadirProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getTbProductosProveedor().getSelectedRow() != -1)
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ANADIR_PRODUCTO_A_PEDIDO, obtenerProductoPedido(getTbProductosProveedor(), getTbProductosProveedor().getSelectedRow()));
				textFieldCantidad.setText("");
			}
		});
		
		// ------- ELIMINAR PRODUCTO ----------------------
		JButton btnEliminarProducto = new JButton("Eliminar Producto");
		btnEliminarProducto.setBounds(292, 213, 151, 26);
		panelLista.add(btnEliminarProducto);
		btnEliminarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getTbProductosPedido().getSelectedRow() != -1)				
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.QUITAR_PRODUCTO_DE_PEDIDO, obtenerProductoPedido(getTbProductosPedido(), getTbProductosPedido().getSelectedRow()));
				textFieldCantidad.setText("");
			}
		});
		
		// ------- MODIFICAR CANTIDAD ----------------------
		JButton btnModiCantidad = new JButton("Modificar Cantidad");
		btnModiCantidad.setBounds(292, 159, 151, 26);
		panelLista.add(btnModiCantidad);
		btnModiCantidad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(getTbProductosPedido().getSelectedRow() != -1)	
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.MODIFICAR_CANTIDAD_PRODUCTO_DE_PEDIDO, obtenerProductoPedido(getTbProductosPedido(), getTbProductosPedido().getSelectedRow()));
				textFieldCantidad.setText("");
			}
		});			
		
		// -------- CANTIDAD ----------------------------
		textFieldCantidad = new JTextField();
		textFieldCantidad.setBounds(312, 127, 118, 20);
		panelLista.add(textFieldCantidad);
		textFieldCantidad.setEditable(true);
		textFieldCantidad.setColumns(10);
		
		// ---------- PRECIO TOTAL ----------------------
		JLabel lblPrecioTotal = new JLabel("Importe del Pedido Actual:");
		lblPrecioTotal.setBounds(295, 261, 148, 16);
		panelLista.add(lblPrecioTotal);
		
		txtPrecioTotal = new JTextField("0.00");
		txtPrecioTotal.setHorizontalAlignment(SwingConstants.TRAILING);
		txtPrecioTotal.setBounds(327, 289, 70, 20);
		txtPrecioTotal.setEditable(false);
		panelLista.add(txtPrecioTotal);
		
		// ------ LABEL € -----------
		
		JLabel label_euros = new JLabel("\u20AC");
		label_euros.setBounds(401, 289, 32, 16);
		panelLista.add(label_euros);
		
		// ----- TERMINAR PEDIDO -------------
		JButton btnTerminarPedido = new JButton("TERMINAR Pedido");
		btnTerminarPedido.setBounds(290, 32, 155, 23);
		panelLista.add(btnTerminarPedido);
		btnTerminarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationController.obtenerInstancia().handleRequest(EnumComandos.TERMINAR_PEDIDO, obtenerPedido());
				setVisible(false);				
			}
		});
		
		// ------- ID PROVEEDOR ----------------
		JLabel lblIDProveedor = new JLabel("ID Proveedor:");
		lblIDProveedor.setBounds(315, 21, 76, 16);
		getContentPane().add(lblIDProveedor);
		
		txtIDProveedor = new JTextField("");
		txtIDProveedor.setBounds(401, 21, 40, 20);
		txtIDProveedor.setEditable(false);
		getContentPane().add(txtIDProveedor);

	}
	
	// Metodos
	
	private TProductoDePedido obtenerProductoPedido(JTable tabla, int fila) {

		TProductoDePedido t = null;
		
		if(fila != -1)
		{
			t = new TProductoDePedido();
				
			t.setProducto((int)tabla.getValueAt(fila, 0));
			t.setPedido(-1);
			t.setPrecio((double)tabla.getValueAt(fila, 2));
			t.setCantidad(obtenerCantidad());
		}
		
		return t;
	}
	
	/*
	 * Obtiene una lista TProductoDePedido con todos los productos del pedido
	 */
	private TPedido obtenerPedido() {
						
		List<TProductoDePedido> listaproductos = new ArrayList<TProductoDePedido>();	

		JTable tbpedido = getTbProductosPedido();
		int filas = modelopedido.getRowCount();
		
		for(int i = 0; i<filas; i++) //recorre toda la tabla pedido fila a fila	
		{   					
			TProductoDePedido tproducto = new TProductoDePedido();
			tproducto.setProducto((int)tbpedido.getValueAt(i, 0));
			tproducto.setPedido(-1);
			tproducto.setPrecio((double)tbpedido.getValueAt(i, 2));
			tproducto.setCantidad((int)tbpedido.getValueAt(i, 3));
			
			listaproductos.add(tproducto);
		}
	
		TPedido tpedido = new TPedido();
		
		tpedido.setId_pedido(-1);
		tpedido.setId_proveedor(Integer.parseInt(txtIDProveedor.getText()));
		tpedido.setFechaRealizado("---");
		tpedido.setFechaEntregado("---");
		tpedido.setFechaCancelado("---");
		tpedido.setListaProductosPedido(listaproductos);
		
		return tpedido;
	}

	public static int obtenerCantidad(){
		
		int cantidad = -1;
		
		ComprobadorEnteros comprobadorEntero = new ComprobadorEnteros();
		
		if (comprobadorEntero.isNumeric(textFieldCantidad.getText()))
			cantidad = Integer.parseInt(textFieldCantidad.getText());		
		
		return cantidad;
		
	}
	
	public void reiniciarTablaProductosPedido() {
		
		modelopedido = new DefaultTableModel();
		
		modelopedido.addColumn("ID");
		modelopedido.addColumn("NOMBRE");
		modelopedido.addColumn("PRECIO");
		modelopedido.addColumn("CANTIDAD");	
						
		tbProductosPedido.setModel(modelopedido);				

	}
	
	public void rellenarTablaProductosProveedor(Object objeto) { //recibe una lista de Tproductosproveedor

		tabla = new Tabla();
		
		tabla.addColumn("ID");
		tabla.addColumn("NOMBRE");
		tabla.addColumn("PRECIO");
	
		
		if (objeto != null)
		{
			List<TProductoDeProveedor> listaproductos = (List<TProductoDeProveedor>) objeto;
		
			for (TProductoDeProveedor TproductoDeProveedor: listaproductos){
				
				fila = new Vector();
				fila.add(TproductoDeProveedor.getProducto());
				fila.add("FALTA");	
				fila.add(TproductoDeProveedor.getPrecio());	
				
				tabla.addRow(fila);
				
			}
		
		}	
			
		tbProductosProveedor.setModel(tabla);
		
	}
	
	public static void anadirProducto(Object objeto) 
	{
		TProductoDePedido tProductoPedido = (TProductoDePedido)objeto;	
		
		JTable tbpedido = getTbProductosPedido();
		int filas = modelopedido.getRowCount();
		int fila_act = 0;
		boolean producto_encontrado = false;
		//busca fila a fila el producto
		while(fila_act < filas && !producto_encontrado) //recorre toda la tabla pedido fila a fila	
		{   			
			if((int)tbpedido.getValueAt(fila_act, 0) == tProductoPedido.getProducto())
			{			
				producto_encontrado = true;
				fila_act--;
			}
			fila_act++;
		}
		//si no lo encuentra, lo inserta
		if(!producto_encontrado && tProductoPedido.getCantidad()>0)
		{
			fila = new Vector();
			fila.add(tProductoPedido.getProducto());
			fila.add("FALTA");	
			fila.add(tProductoPedido.getPrecio());
			fila.add(tProductoPedido.getCantidad());	

			modelopedido.addRow(fila);
					
			recalcularTotal(tProductoPedido.getCantidad()*tProductoPedido.getPrecio());
		}
		
	}
	
	public static void quitarProducto(Object objeto) 
	{
		TProductoDePedido tProductoPedido = (TProductoDePedido)objeto;	
		
		JTable tbpedido = getTbProductosPedido();
		int filas = modelopedido.getRowCount();
		int fila_act = 0;
		boolean producto_encontrado = false;
		//busca fila a fila el producto
		while(fila_act < filas && !producto_encontrado) //recorre toda la tabla pedido fila a fila	
		{   			
			if((int)tbpedido.getValueAt(fila_act, 0) == tProductoPedido.getProducto())
			{			
				producto_encontrado = true;
				recalcularTotal( - ((double)tbpedido.getValueAt(fila_act, 2)*(int)tbpedido.getValueAt(fila_act, 3)));
				modelopedido.removeRow(fila_act);				
			}
			fila_act++;
		}
		
	}
	
	public static void modificarCantidadProducto(Object objeto) {
		TProductoDePedido tProductoPedido = (TProductoDePedido)objeto;	
		
		JTable tbpedido = getTbProductosPedido();
		int filas = modelopedido.getRowCount();
		int fila_act = 0;
		boolean producto_encontrado = false;
		//busca fila a fila el producto
		while(fila_act < filas && !producto_encontrado) //recorre toda la tabla pedido fila a fila	
		{   			
			if((int)tbpedido.getValueAt(fila_act, 0) == tProductoPedido.getProducto())
			{			
				producto_encontrado = true;
				fila_act--;
			}
			fila_act++;
		}
		//si lo encuentra, lo modifica
		if(producto_encontrado && tProductoPedido.getCantidad()>0)
		{
			recalcularTotal( - ((double)tbpedido.getValueAt(fila_act, 2)*(int)tbpedido.getValueAt(fila_act, 3)));
			modelopedido.setValueAt(tProductoPedido.getCantidad(), fila_act, 3);
			recalcularTotal(tProductoPedido.getCantidad()*tProductoPedido.getPrecio());
		}
		
	}

	public void actualizar(Object objeto) { //recibe el ID del proveedor encapsulado en un TProveedor

		limpiarFormulario();
		
		txtIDProveedor.setText(Integer.toString(((TProveedor)objeto).getId_proveedor()));
				
		reiniciarTablaProductosPedido();		
		
		ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR_PEDIDO, ((TProveedor)objeto).getId_proveedor() );
		
		setVisible(true);	
	}
	
	private void limpiarFormulario()
	{
		textFieldCantidad.setText("");
		txtIDProveedor.setText("");
		txtPrecioTotal.setText("0.0");

	}
	
	private static void recalcularTotal(double cant) 
	{		
		txtPrecioTotal.setText(Double.toString(redondear(Double.parseDouble(txtPrecioTotal.getText()) + cant)));	
	}

	public static double redondear(double numero){		
		return Math.rint(numero*100)/100;		
	}
	
}

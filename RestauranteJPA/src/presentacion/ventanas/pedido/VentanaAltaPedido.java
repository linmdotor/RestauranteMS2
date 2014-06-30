package presentacion.ventanas.pedido;

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

import negocio.ComprobadorEnteros;
import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedido;
import negocio.producto.businessobject.Producto;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.productosdeproveedor.businessobject.ProductoDeProveedor;
import negocio.proveedor.businessobject.Proveedor;
import negocio.proveedor.transfer.TProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;


public class VentanaAltaPedido extends JFrame {
	
	private static VentanaAltaPedido ventana; //instancia singleton
	
	private JTextField textFieldCantidad;
	private Tabla tabla;
	private JTable tbProductosProveedor;
	private JTable tbProductosPedido;
	private JLabel txtIDProveedor;
	private JLabel txtPrecioTotal;
	private Vector fila;
	private JScrollPane scrollPanel;
	
	private Pedido pedido;
	
	//Mutadores y Accedentes	
	
	//GetInstance
	public static VentanaAltaPedido obtenerInstancia() {

		if (ventana == null)
			ventana = new VentanaAltaPedido();

		return ventana;
	}
	
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

		public JTable getTbProductosPedido() {
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

							if (tbProductosPedido.getSelectedRow() != -1)
							{							
								cambiarCantidad(pedido.getListaProductosPedido().get(tbProductosPedido.getSelectedRow()).getCantidad());							
								
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
					
					boolean yaEstaProducto = false;
					
					Producto producto = pedido.getProveedor().getListaProductosProveedor().get(tbProductosProveedor.getSelectedRow()).getProducto();
							
					for ( ProductoDePedido productoPedido: pedido.getListaProductosPedido()){
						
						if (productoPedido.getProducto().getId_producto() == producto.getId_producto())
							yaEstaProducto = true;						
						
					}
					
					if (!yaEstaProducto)
						//pedido.getListaProductosPedido().add(new ProductoDePedido(producto, pedido, obtenerCantidad()));
					
					actualizarTablas();					
					
				}

			});
			
			// ------- ELIMINAR PRODUCTO ----------------------
			JButton btnEliminarProducto = new JButton("Eliminar Producto");
			btnEliminarProducto.setBounds(292, 213, 151, 26);
			panelLista.add(btnEliminarProducto);
			btnEliminarProducto.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
									
					pedido.getListaProductosPedido().remove(tbProductosPedido.getSelectedRow());
					
					actualizarTablas();
					
				}
			});
			
			// ------- MODIFICAR CANTIDAD ----------------------
			JButton btnModiCantidad = new JButton("Modificar Cantidad");
			btnModiCantidad.setBounds(292, 159, 151, 26);
			panelLista.add(btnModiCantidad);
			btnModiCantidad.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					pedido.getListaProductosPedido().get((tbProductosPedido.getSelectedRow())).setCantidad(obtenerCantidad());;
					
					actualizarTablas();
					
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
			
			txtPrecioTotal = new JLabel("0");
			txtPrecioTotal.setHorizontalAlignment(SwingConstants.TRAILING);
			txtPrecioTotal.setBounds(267, 289, 110, 16);
			panelLista.add(txtPrecioTotal);
			
			// ------ ¿? -----------
			
			JLabel label_1 = new JLabel("\u20AC");
			label_1.setBounds(381, 289, 32, 16);
			panelLista.add(label_1);
			
			// ----- ALMACENAR PEDIDO -------------
			JButton btnAlmacenarPedido = new JButton("Almacenar Pedido");
			btnAlmacenarPedido.setBounds(290, 32, 155, 23);
			panelLista.add(btnAlmacenarPedido);
			btnAlmacenarPedido.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ALMACENAR_PEDIDO, pedido);
					cerrar();				
				}
			});
			
			// ------- ID PROVEEDOR ----------------
			JLabel lblIDProveedor = new JLabel("ID Proveedor:");
			lblIDProveedor.setBounds(315, 21, 76, 16);
			getContentPane().add(lblIDProveedor);
			
			txtIDProveedor = new JLabel("");
			txtIDProveedor.setBounds(401, 21, 55, 16);
			getContentPane().add(txtIDProveedor);
			/*tbProductosProveedor.getSelectionModel().addListSelectionListener(
					new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent arg0) {

							if (getTbProveedores().getSelectedRow() != -1)
							{
								
								
							}
						}
					});*/

		}
		
		// Metodos
		
		private TProductoDePedido obtenerProductoProveedor() {
					
			//return new ProductoDePedido(new Producto(getTbProductosProveedor().getSelectedRow() + 1, false, -1, "", null),this.pedido,obtenerCantidad());
			return new TProductoDePedido();
		}
		
		private TProductoDePedido obtenerProductoPedido() {
							
			//return new ProductoDePedido(new Producto(this.pedido.getListaProductosPedido().get(tbProductosPedido.getSelectedRow()).getProducto().getId_producto(), false, -1, "", null),this.pedido,obtenerCantidad());
			return new TProductoDePedido();
		}

		public int obtenerCantidad(){
			
			int cantidad = -1;
			
			ComprobadorEnteros comprobadorEntero = new ComprobadorEnteros();
			
			if (comprobadorEntero.isNumeric(textFieldCantidad.getText()))
				cantidad = Integer.parseInt(this.textFieldCantidad.getText());		
			
			return cantidad;
			
		}
		
		public void rellenarTablaProductosPedido() {
			
			if (pedido.getListaProductosPedido() != null){
			
				tabla = new Tabla();
				
				tabla.addColumn("ID");
				tabla.addColumn("NOMBRE");
				tabla.addColumn("CANTIDAD");
			
				for (ProductoDePedido productoPedido: pedido.getListaProductosPedido()){
					
					fila = new Vector();
					fila.add(productoPedido.getProducto().getId_producto());
					fila.add(productoPedido.getProducto().getNombre());
					fila.add(productoPedido.getCantidad());
					
					tabla.addRow(fila);
					
				}		
								
				tbProductosPedido.setModel(tabla);				

				calcularTotal();
			
			}
			
		}
		
		public void rellenarTablaProductosProveedor() {
				
			if (pedido.getProveedor().getListaProductosProveedor() != null){
				
				tabla = new Tabla();
				
				tabla.addColumn("ID");
				tabla.addColumn("NOMBRE");
				tabla.addColumn("PRECIO");
				
				for (ProductoDeProveedor productoDeProveedor: pedido.getProveedor().getListaProductosProveedor()){
					
					fila = new Vector();
					fila.add(productoDeProveedor.getProducto().getId_producto());
					fila.add(productoDeProveedor.getProducto().getNombre());	
					fila.add(productoDeProveedor.getPrecio());	
					
					tabla.addRow(fila);
					
				}
			
				tbProductosProveedor.setModel(tabla);
			
			}
		
		}
		
		public void cambiarCantidad(int cantidad) {
			
			textFieldCantidad.setText(Integer.toString(cantidad));
			
		}	
		
		public void calcularTotal(){
			
			double precioTotal = 0;
			
			int contadorPedido = 0;
			int contadorProveedor = 0;
			
			while ( contadorPedido < pedido.getListaProductosPedido().size() ){
				
				contadorProveedor = 0;
				
				while ( contadorProveedor < pedido.getProveedor().getListaProductosProveedor().size() && contadorPedido < pedido.getListaProductosPedido().size() ){
				
					if ( pedido.getListaProductosPedido().get(contadorPedido).getProducto().getId_producto() == pedido.getProveedor().getListaProductosProveedor().get(contadorProveedor).getProducto().getId_producto() ){
						precioTotal = precioTotal + (pedido.getProveedor().getListaProductosProveedor().get(contadorProveedor).getPrecio()*pedido.getListaProductosPedido().get(contadorPedido).getCantidad());
						contadorPedido++;
					} else {
						contadorProveedor++;				
					}		
				
				}
				
			}
					
			txtPrecioTotal.setText(Double.toString(precioTotal));
			
			//pedido.setPrecio(precioTotal);
			
		}

		public void actualizar(Object objeto) {
			
			pedido = new Pedido();
			
			pedido.setProveedor(new Proveedor());	
			
			List<ProductoDePedido> lista = new ArrayList<ProductoDePedido>();
			
			pedido.setListaProductosPedido(lista);
			
			pedido.getProveedor().setId_proveedor(  ((TProveedor)objeto).getId_proveedor()       );
			
			//pedido.getProveedor().setListaProductosProveedor( ((TProveedor)objeto).getListaProductosProveedor());
			
			txtIDProveedor.setText(Integer.toString(pedido.getProveedor().getId_proveedor()));
					
			actualizarTablas();
			
			
		}

		private void actualizarTablas() {
			
			rellenarTablaProductosProveedor();
			
			rellenarTablaProductosPedido();		
						
			//ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PROVEEDOR_PEDIDO, null );
			
			setVisible(true);			
			
		}

		private void limpiarFormulario()
		{
			textFieldCantidad.setText("");
			txtIDProveedor.setText("");
			txtPrecioTotal.setText("");

		}
		
		public void cerrar() {		
			this.setVisible(false);
			limpiarFormulario();
		}
	}

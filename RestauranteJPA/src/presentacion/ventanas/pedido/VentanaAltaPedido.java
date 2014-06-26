package presentacion.ventanas.pedido;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;
import negocio.ComprobadorEnteros;
import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedido;
import negocio.producto.EnumTipoProducto;
import negocio.producto.businessobject.Producto;
import negocio.producto.transfer.TProducto;
import negocio.producto.transfer.TProductoNoPerecedero;
import negocio.producto.transfer.TProductoPerecedero;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import negocio.proveedor.businessobject.Proveedor;
import negocio.proveedor.transfer.TProveedor;


@SuppressWarnings("serial")
public class VentanaAltaPedido extends JFrame{
	
	private static VentanaAltaPedido ventana; //instancia singleton
	
	/***Tabla Producto**/
	private JPanel panelListaProd;
	private JTable tbProductos;
	private Vector filaProd;
	private JScrollPane scrollPanelProdcuto;
	

	/***Tabla Proveedor**/
	private JTable tbProveedores;
	private Vector filaProv;
	private JScrollPane scrollPanelProveedor;

	
	/***Tabla Pedido**/
	private JTable tbPedidoNuevo;
	private Vector filaPed;
	private JScrollPane scrollPanelPedido;
	
	private Tabla tabla;

	public JTable getTbProductos() {
		return tbProductos;
	}

	//GetInstance
	public static VentanaAltaPedido obtenerInstancia() {

		if (ventana == null)
			ventana = new VentanaAltaPedido();

		return ventana;
	}
					
	//Constructor
			
	public VentanaAltaPedido(){
		super(" Realizar Nuevo Pedido");
		setResizable(false);
		
		setSize(1030, 500);
		setVisible(true);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		Font myFont = new Font("Courier",Font.BOLD,26);
		
		//-------------------- -------------------------- -------------------//
		
		JButton btnAgregar = new JButton("+");
		btnAgregar.setBounds(590, 50, 50, 50);
		btnAgregar.setFont(myFont);
		getContentPane().add(btnAgregar);
		
		JButton btnElimina = new JButton("-");
		btnElimina.setBounds(590, 130, 50, 50);
		btnElimina.setFont(myFont);
		getContentPane().add(btnElimina);
		
		JButton btnFin = new JButton("FIN");
		btnFin.setBounds(570, 330, 100, 50);
		btnFin.setFont(myFont);
		getContentPane().add(btnFin);

		//-------------------- CONSTRUCCIÓN PRODUCTOS  -------------------//
		scrollPanelProdcuto = new JScrollPane();
		scrollPanelProdcuto.setBounds(30, 30, 250, 400);
		//panelListaProd.add(scrollPanelProdcuto);
		getContentPane().add(scrollPanelProdcuto);

		tbProductos = new JTable();
		scrollPanelProdcuto.setViewportView(tbProductos);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProductos.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent arg0) {

						if (getTbProductos().getSelectedRow() != -1) //hay alguna fila seleccionada
						{
							int proveedordelproducto = Integer.parseInt(getTbProductos().getModel().getValueAt(getTbProductos().getSelectedRow(), 0).toString());
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PROVEEDOR_PRODUCTO, null/*Producto*/);	
							
							System.out.println(Integer.parseInt(getTbProductos().getModel().getValueAt(getTbProductos().getSelectedRow(), 0).toString()));
						}
					}
				});
		
		//-------------------- CONSTRUCCIÓN PROVEEDORES  -------------------//
		
		scrollPanelProveedor = new JScrollPane();
		scrollPanelProveedor.setBounds(300, 30, 250, 400);
		getContentPane().add(scrollPanelProveedor);

		tbProveedores = new JTable();
		scrollPanelProveedor.setViewportView(tbProveedores);
		tbProveedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	
		//-------------------- CONSTRUCCIÓN PEDIDOS  -------------------//
		
		JLabel lblMiPedido = new JLabel("Mi Pedido");
		lblMiPedido.setBounds(750, 10, 300, 50);
		lblMiPedido.setFont(myFont);
		getContentPane().add(lblMiPedido);
		
		scrollPanelPedido = new JScrollPane();
		scrollPanelPedido.setBounds(690, 60, 300, 350);
		getContentPane().add(scrollPanelPedido);

		tbPedidoNuevo = new JTable();
		scrollPanelPedido.setViewportView(tbPedidoNuevo);
		tbPedidoNuevo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(800, 400, 100, 50);
		lblTotal.setFont(new Font("Courier",Font.BOLD,18));
		getContentPane().add(lblTotal);
		
		JTextField txtTotal = new JTextField();
		txtTotal.setBounds(870, 410, 120, 30);
		txtTotal.setFont(new Font("Courier",Font.BOLD,18));
		getContentPane().add(txtTotal);
		
		
	}
	
	public void actualizar(Object object) {

		List<TProducto> lista = new ArrayList<TProducto>();
		
		if (object == null)
			rellenarTabla(lista);
		else			
			rellenarTabla((List<TProducto>) object);

		setVisible(true);
		repaint();

	}
	
	
	public void actualizarProveedor(Object object) {

		List<TProveedor> lista = new ArrayList<TProveedor>();
		
		if (object == null)
			rellenarTablaProveedor(lista);
		else			
			rellenarTablaProveedor((List<TProveedor>) object);

		setVisible(true);
		repaint();

	}
	
	private void rellenarTablaProveedor(List<TProveedor> lista) {
		tabla = new Tabla();

		tabla.addColumn("ID");
		tabla.addColumn("NIF");
		tabla.addColumn("NOMBRE");
		tabla.addColumn("TELEFONO");

		for (int i = 0; i < lista.size(); i++) {

			filaProv = new Vector();
			TProveedor prov = lista.get(i);
			filaProv.add(prov.getId_proveedor());
			filaProv.add(prov.getNIF());
			filaProv.add(prov.getNombre());
			filaProv.add(prov.getTelefono());

			tabla.addRow(filaProv);
		}

		tbProveedores.setModel(tabla);
		
	}
	
	private void rellenarTabla(List<TProducto> lista) {
		tabla = new Tabla();

		tabla.addColumn("ID");
		tabla.addColumn("NOMBRE");
		tabla.addColumn("STOCK");
		tabla.addColumn("CADUCIDAD");
		tabla.addColumn("RECOMENDACIONES");
		tabla.addColumn("DISPONIBLE");

		for (int i = 0; i < lista.size(); i++) {

			filaProd = new Vector();
			TProducto prod = lista.get(i);
			filaProd.add(prod.getId_producto());
			filaProd.add(prod.getNombre());
			filaProd.add(prod.getStock());
			
			if(prod instanceof TProductoPerecedero)
			{
				TProductoPerecedero p = (TProductoPerecedero) prod;
				filaProd.add(p.getFechaCaducidad());
				filaProd.add("---");
			}
			else if(prod instanceof TProductoNoPerecedero)
			{
				TProductoNoPerecedero np = (TProductoNoPerecedero) prod;
				filaProd.add("---");
				filaProd.add(np.getRecomendaciones());
			}
			filaProd.add(prod.isDisponible());

			tabla.addRow(filaProd);
		}

		tbProductos.setModel(tabla);
		
	}
	
	/*public TProveedor obtenerProveedorProducto(TProducto tprod) {

		TProductoDeProveedor tProductoDeProveedor = new TProductoDeProveedor();
		
		tProductoDeProveedor.setProveedor(Integer.parseInt(textFieldID_Proveedor.getText()));

		tProductoDeProveedor.setProducto(Integer.parseInt(tbProductosTotales.getValueAt(tbProductosTotales.getSelectedRow(), 0).toString()));
		
		if (textFieldPrecio.getText().length() > 0)
			tProductoDeProveedor.setPrecio(Integer.parseInt(textFieldPrecio.getText()));
		else
			tProductoDeProveedor.setPrecio(0);
		
		return tProductoDeProveedor;
	}*/

	
	
	
}

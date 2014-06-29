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
import negocio.pedido.transfer.TPedidoProducto;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.proveedor.businessobject.Proveedor;
import negocio.proveedor.transfer.TProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;

@SuppressWarnings("serial")
public class VentanaGestionPedidos extends JFrame{

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
		
		

		// --------- CONSTRUCCIÓN TABLA PEDIDOS-------------
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
}

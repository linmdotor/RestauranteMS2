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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import negocio.pedido.transfer.TPedido;
import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.proveedor.transfer.TProveedor;
import presentacion.controlador.ApplicationController;
import presentacion.controlador.EnumComandos;
import presentacion.ventanas.Tabla;

@SuppressWarnings("serial")
public class VentanaGestionPedidos extends JFrame{

	private static VentanaGestionPedidos ventana; //instancia singleton

	private JComboBox boxProveedores;

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
		
		setSize(998, 390);
		//setVisible(true);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		
		// -------- PANEL PRINCIPAL --------------
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBounds(774, 5, 215, 350);
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
		
		// ------- ALMACENAR PEDIDO ----------------------
		JButton btnalmacenarPedido = new JButton("Almacenar Pedido");
		btnalmacenarPedido.setBounds(9, 276, 192, 26);
		panelFormulario.add(btnalmacenarPedido);
		btnalmacenarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(getTbPedidos().getSelectedRow() != -1) //hay alguna fila seleccionada
				{
					if(mensajeConfirmacionSiNo("¿Seguro que desea cancelar este pedido? Los productos pasarán a formar parte del Stock", "Almacenar Pedido"))
					{
						ApplicationController.obtenerInstancia().handleRequest(EnumComandos.ALMACENAR_PEDIDO, obtenerPedido());
						ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PEDIDOS, null);
					}
				}
			}
		});
		
		// ------- CANCELAR PEDIDO ----------------------
		JButton btnCancelarPedido = new JButton("Cancelar Pedido");
		btnCancelarPedido.setBounds(9, 310, 192, 26);
		panelFormulario.add(btnCancelarPedido);
		btnCancelarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(getTbPedidos().getSelectedRow() != -1) //hay alguna fila seleccionada
				{
					if(mensajeConfirmacionSiNo("¿Seguro que desea cancelar este pedido?", "Cancelar Pedido"))
					{
						ApplicationController.obtenerInstancia().handleRequest(EnumComandos.CANCELAR_PEDIDO, obtenerPedido());
						ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PEDIDOS, null);
					}
				}
			}
		});
		
		JPanel panelLista = new JPanel();
		panelLista.setBounds(10, 5, 764, 353);
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
		
		

		// --------- CONSTRUCCIÓN TABLA PEDIDOS-------------
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 35, 480, 300);
		panelLista.add(scrollPane);
		
		JLabel lblPedidos = new JLabel("Pedidos");
		lblPedidos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblPedidos.setBounds(125, 12, 192, 16);
		panelLista.add(lblPedidos);
		lblPedidos.setHorizontalAlignment(SwingConstants.CENTER);

		tbPedidos = new JTable();
		scrollPane.setViewportView(tbPedidos);
		tbPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbPedidos.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					
					public void valueChanged(ListSelectionEvent arg0) //hay alguna fila seleccionada
					{
					if (arg0.getValueIsAdjusting()) //sólo al hacer click (no al soltar el click)
					{
						if (getTbPedidos().getSelectedRow() != -1)
						{
							//Añade los datos de la tabla de productos (lista de productos del pedido actual)
							ApplicationController.obtenerInstancia().handleRequest(EnumComandos.OBTENER_PRODUCTOS_PEDIDO, getTbPedidos().getValueAt(getTbPedidos().getSelectedRow(),0) );
						}
					}
					}
				});
		
		// --------- CONSTRUCCIÓN TABLA PRODUCTOS-------------
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(485, 35, 280, 300);
		panelLista.add(scrollPane2);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(new Font("Dialog", Font.BOLD, 13));
		lblProductos.setBounds(525, 12, 192, 16);
		panelLista.add(lblProductos);
		lblProductos.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbProductos = new JTable();
		scrollPane2.setViewportView(tbProductos);
		tbProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//LA TABLA DE PRODUCTOS NO DEBE TENER UN ACTION LISTENER, PUES NO SE PRODUCE NINGUNA ACCIÓN AL CLICKARSE

	}

	// Metodos
	public void actualizar() {
		
		actualizarTablaProductos(null);

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
			
			for (TPedido tpedido : lista) {
	
				fila = new Vector();

				fila.add(tpedido.getId_pedido());
				fila.add(tpedido.getId_proveedor());
				fila.add(tpedido.getFechaRealizado());
				fila.add(tpedido.getFechaEntregado());
				fila.add(tpedido.getFechaCancelado());
	
				tabla.addRow(fila);
			}

		}
		
		tbPedidos.setModel(tabla);
		
		tbPedidos.getColumnModel().getColumn(0).setMaxWidth(60); //ajusta el ancho de las columnas ID
		tbPedidos.getColumnModel().getColumn(1).setMaxWidth(60);
		
		repaint();
		
	}

	public void actualizarTablaProductos(Object objeto) {
		
		tabla = new Tabla();

		tabla.addColumn("ID_PROD.");
		tabla.addColumn("PRECIO");
		tabla.addColumn("CANT.");
		
		if (objeto != null)
		{
			List<TProductoDePedido> lista = (List<TProductoDePedido>) objeto;
			
			for (TProductoDePedido tproducto_pedido: lista) {
	
				fila = new Vector();
				
				fila.add(tproducto_pedido.getProducto());
				fila.add(tproducto_pedido.getPrecio());
				fila.add(tproducto_pedido.getCantidad());
	
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
	
	public TPedido obtenerPedido() {

		TPedido tpedido = new TPedido();
		
		JTable tbpedidos = getTbPedidos();
		int fila_sel = tbpedidos.getSelectedRow();
		
		tpedido.setId_pedido((int)tbpedidos.getValueAt(fila_sel, 0));
		tpedido.setId_proveedor((int)tbpedidos.getValueAt(fila_sel, 1));
		tpedido.setFechaRealizado((String)tbpedidos.getValueAt(fila_sel, 2));
		tpedido.setFechaEntregado((String)tbpedidos.getValueAt(fila_sel, 3));
		tpedido.setFechaCancelado((String)tbpedidos.getValueAt(fila_sel, 4));
		
		JTable tbproductos = getTbProductos();
		
		int filas = tbproductos.getRowCount();
		
		List<TProductoDePedido> listaproductos = new ArrayList<TProductoDePedido>();
		for(int i = 0; i<filas; i++) //recorre toda la tabla productos del pedido fila a fila	
		{   					
			TProductoDePedido tproducto = new TProductoDePedido();
			tproducto.setProducto((int)tbproductos.getValueAt(i, 0));
			tproducto.setPedido((int)tbpedidos.getValueAt(fila_sel, 0));
			tproducto.setPrecio((double)tbproductos.getValueAt(i, 1));
			tproducto.setCantidad((int)tbproductos.getValueAt(i, 2));
			
			listaproductos.add(tproducto);
		}
		
		tpedido.setListaProductosPedido(listaproductos);		
		
		return tpedido;
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


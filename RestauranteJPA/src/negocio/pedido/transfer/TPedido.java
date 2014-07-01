package negocio.pedido.transfer;

import java.util.ArrayList;
import java.util.List;

import negocio.pedido.businessobject.Pedido;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.productosdepedido.transfer.TProductoDePedido;




public class TPedido {

	protected int id_pedido;
	protected int id_proveedor;
	protected String fecha_realizado;
	protected String fecha_entregado;
	protected String fecha_cancelado;
	private List<TProductoDePedido> listaProductosPedido;

	public TPedido(){
		
	}
	
	public TPedido(Pedido pedido) {
		this.id_pedido = pedido.getId_pedido();
		this.id_proveedor = pedido.getProveedor().getId_proveedor();
		this.fecha_realizado = pedido.getFechaRealizado();
		this.fecha_entregado = pedido.getFechaEntregado();
		this.fecha_cancelado = pedido.getFechaCancelado();
		
		listaProductosPedido = new ArrayList<TProductoDePedido>();
		for(ProductoDePedido prod : pedido.getListaProductosPedido())
		{
			listaProductosPedido.add(new TProductoDePedido(prod));
		}
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}
	
	public int getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public String getFechaRealizado(){
		return this.fecha_realizado;
	}
	
	public void setFechaRealizado(String fecha_realizado){
		this.fecha_realizado=fecha_realizado;
	}
	
	public String getFechaEntregado(){
		return this.fecha_entregado;
	}
	
	public void setFechaEntregado(String fecha_entregado){
		this.fecha_entregado=fecha_entregado;
	}
	
	public String getFechaCancelado(){
		return this.fecha_cancelado;
	}
	
	public void setFechaCancelado(String fecha_cancelado){
		this.fecha_cancelado=fecha_cancelado;
	}

	public List<TProductoDePedido> getListaProductosPedido() {
		return listaProductosPedido;
	}

	public void setListaProductosPedido(List<TProductoDePedido> listaProductosPedido) {
		this.listaProductosPedido = listaProductosPedido;
	}

	
}

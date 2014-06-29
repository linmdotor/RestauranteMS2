package negocio.productosdepedido.transfer;

import negocio.productosdepedido.businessobject.ProductoDePedido;

public class TProductoDePedido {
	
	// Atributos
	
	private int producto;
	private int pedido;
	private double precio;
	
	// Constructores
	
	public TProductoDePedido() {	
	}
	
	public TProductoDePedido(int producto, int pedido, float precio) {
		
		this.producto= producto;
		this.pedido = pedido;
		this.precio = precio;
		
	}
	
public TProductoDePedido(ProductoDePedido producto_pedido) {
		
		this.producto= producto_pedido.getProducto().getId_producto();
		this.pedido = producto_pedido.getPedido().getId_pedido();
		
	}
		
	// Mutadores y Accedentes

	public int getProducto() {
		
		return producto;
	
	}

	public void setProducto(int producto) {
		
		this.producto = producto;
	
	}

	public int getPedido() {
		
		return pedido;
	
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public double getPrecio() {
		
		return precio;
	
	}

	public void setPrecio(double precio) {
		
		this.precio = precio;
	
	}
}

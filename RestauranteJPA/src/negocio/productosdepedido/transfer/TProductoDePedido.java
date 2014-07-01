package negocio.productosdepedido.transfer;

import negocio.productosdepedido.businessobject.ProductoDePedido;

public class TProductoDePedido {
	
	// Atributos
	
	private int producto;
	private int pedido;
	private double precio;
	private int cantidad;
	
	// Constructores
	
	public TProductoDePedido() {	
	}
	
	public TProductoDePedido(int producto, int pedido, double precio, int cantidad) {
		
		this.producto= producto;
		this.pedido = pedido;
		this.precio = precio;
		this.cantidad = cantidad;
	}
	
	public TProductoDePedido(ProductoDePedido producto_pedido) {
		
		this.producto= producto_pedido.getProducto().getId_producto();
		this.pedido = producto_pedido.getPedido().getId_pedido();
		this.precio = producto_pedido.getPrecio();
		this.cantidad = producto_pedido.getCantidad();
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

	public void setPrecio(double d) {
		
		this.precio = d;
	
	}
	
	public int getCantidad() {
		
		return cantidad;
	
	}

	public void setCantidad(int cantidad) {
		
		this.cantidad = cantidad;
	
	}
}

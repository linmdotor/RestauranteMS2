package negocio.pedido;

import negocio.producto.Producto;

public class TPedidoProducto {
	//Precio(float) y cantidad(int)
	 
	 private Producto producto;
	 private Pedido pedido;
	 private float precio;
	 private int cantidad;
	 
	 public TPedidoProducto()
	 {
		 
	 }
	 
	 public TPedidoProducto (Producto producto, Pedido pedido, int cantidad, float precio)
	 {
		 this.cantidad = cantidad;
		 this.pedido = pedido;
		 this.precio = precio;
		 this.producto = producto;
	 }
	 
	 public TPedidoProducto (Object objeto)
	 {
		 TPedidoProducto tProductosPedido = (TPedidoProducto) objeto;
		 
		 this.producto = tProductosPedido.getProducto();
		 this.pedido = tProductosPedido.getPedido();
		 this.precio = tProductosPedido.getPrecio();
		 this.cantidad = tProductosPedido.getCantidad();
	 }
	 
	// Mutadores y Accedentes
	 public Producto getProducto() {		
		 return producto;	
	 }
	 
	 public void setProducto(Producto producto) {
		 this.producto = producto;
	 }  
	 
	 public Pedido getPedido() {		
		 return pedido;	
	 }
	 
	 public void setPedido(Pedido pedido) {
		 this.pedido = pedido;
	 }  
	
	 public float getPrecio() {
		 return precio;
	 }
	 
	 public void setPrecio(float precio) {
		this.precio = precio;
	 }
	 
	 public int getCantidad() {
		 return cantidad;
	 }
	 
	 public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	 }

}
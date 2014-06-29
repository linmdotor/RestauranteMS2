package negocio.productosdepedido.businessobject;


import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedidoProducto;
import negocio.producto.businessobject.Producto;
import negocio.productosdeproveedor.businessobject.ProductoDeProveedor;
import negocio.productosdeproveedor.businessobject.ProductoDeProveedorId;

import javax.persistence.*;

import java.io.Serializable;


/**
 * Entity implentation class for Entity: PedidoProducto
 *
 */
@Entity  
@IdClass(ProductoDePedidoID.class) 
public class ProductoDePedido {

	//Atributos
	@Id
	@ManyToOne
	private Pedido pedido;
	
	@Id
	@ManyToOne
	private Producto producto;
	
    @Version
    private int version;
	
	private int cantidad;
	 
	 public ProductoDePedido ()
	 {
		 super();
	 }
	 
	 public ProductoDePedido (Producto producto, Pedido pedido, int cantidad, float precio)
	 {
		 this.cantidad = cantidad;
		 this.pedido = pedido;
		 this.producto = producto;
	 }
	 
	 public ProductoDePedido (Object objeto)
	 {
		 TPedidoProducto tProductosPedido = (TPedidoProducto) objeto;
		 
		 this.producto = tProductosPedido.getProducto();
		 this.pedido = tProductosPedido.getPedido();
		 this.cantidad = tProductosPedido.getCantidad();
	 }
	 
		public ProductoDePedido(Producto producto, Pedido pedido, int cantidad) {
			
			this.pedido = pedido;
			this.producto = producto;
			this.cantidad = cantidad;
			
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
	 
	 public int getCantidad() {
		 return cantidad;
	 }
	 
	 public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	 }

}

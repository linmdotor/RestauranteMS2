package negocio.productosdepedido.businessobject;

import negocio.pedido.businessobject.Pedido;
import negocio.producto.businessobject.Producto;

import javax.persistence.*;



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
	private float precio;
	 
	 public ProductoDePedido ()
	 {
		 super();
	 }
	 
	 public ProductoDePedido (Producto producto, Pedido pedido, int cantidad, float precio)
	 {
		 this.producto = producto;
		 this.pedido = pedido;
		 this.cantidad = cantidad;		 
		 this.precio = precio;
		 
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

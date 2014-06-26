package negocio.productosdeproveedor.businessobject;

import javax.persistence.*;

import negocio.producto.businessobject.Producto;
import negocio.proveedor.businessobject.Proveedor;

@Entity
@IdClass(ProductoDeProveedorId.class)
public class ProductoDeProveedor {
	
	/*@Version
	 private long version;*/
	
	@Id
	@ManyToOne
	private Proveedor proveedor;
	
	@Id
	@ManyToOne
	private Producto producto;
	
	private double precio;

	public ProductoDeProveedor() {
		super();
		
	}
	
	public ProductoDeProveedor(Proveedor proveedor, Producto producto, double precio) {
		
		this.proveedor = proveedor;
		this.producto = producto;
		this.setPrecio(precio);
		
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
}

package negocio.productosdeproveedor;


public class TProductoDeProveedor {
	
	// Atributos
	
	private int producto;
	private int proveedor;
	private double precio;
	
	// Constructores
	
	public TProductoDeProveedor() {	
		
		
	}
	
	public TProductoDeProveedor(int producto, int proveedor, float precio) {
		
		this.producto= producto;
		this.proveedor = proveedor;
		this.precio = precio;
		
	}
		
	// Mutadores y Accedentes

	public int getProducto() {
		
		return producto;
	
	}

	public void setProducto(int producto) {
		
		this.producto = producto;
	
	}

	public int getProveedor() {
		
		return proveedor;
	
	}

	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}

	public double getPrecio() {
		
		return precio;
	
	}

	public void setPrecio(double precio) {
		
		this.precio = precio;
	
	}

}

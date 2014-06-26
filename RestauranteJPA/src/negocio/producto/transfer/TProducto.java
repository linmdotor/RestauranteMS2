package negocio.producto.transfer;

import negocio.producto.businessobject.Producto;

public class TProducto  {

	protected int id_producto;
	protected boolean disponible;
	protected int stock;
	protected String nombre;
	
	// Constructores
   	
	public TProducto(){
		
	}
	
	public TProducto(int ID_Plato, boolean disponible, int stock, String nombre) {

		this.id_producto = ID_Plato;
		this.disponible = disponible;
		this.stock = stock;
		this.nombre = nombre;
		
	}


	public TProducto(Producto producto) {
		
		this.id_producto = producto.getId_producto();
		this.disponible = producto.isDisponible();
		this.stock = producto.getStock();
		this.nombre = producto.getNombre();
		
	}
	
	// Mutadores y Accedentes

	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
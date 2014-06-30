/**
 * 
 */
package negocio.producto.transfer;

import negocio.producto.businessobject.Producto;
import negocio.producto.businessobject.ProductoPerecedero;

public class TProductoPerecedero extends TProducto{

	private String fechaCaducidad;
	
	public TProductoPerecedero() {
		
	}
	
	public TProductoPerecedero(int ID_Plato, boolean disponible, int stock,	String nombre) {
		
		super(ID_Plato, disponible, stock, nombre);
		
	}

	public TProductoPerecedero(Producto producto) {
		
		ProductoPerecedero productoPerecedero = (ProductoPerecedero) producto;
		
		this.id_producto = productoPerecedero.getId_producto();
		this.disponible = productoPerecedero.isDisponible();
		this.stock = productoPerecedero.getStock();
		this.nombre = productoPerecedero.getNombre();
		this.fechaCaducidad = productoPerecedero.getFechaCaducidad();
		
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fecha) {
		this.fechaCaducidad = fecha; 
	}
	
	public void setAll(TProducto nuevoTProducto) {
		
		TProductoPerecedero boProducto_aux = (TProductoPerecedero) nuevoTProducto;
		
		this.id_producto = nuevoTProducto.getId_producto();
		this.disponible = nuevoTProducto.isDisponible();
		this.stock = nuevoTProducto.getStock();
		this.nombre = nuevoTProducto.getNombre();
		this.fechaCaducidad = boProducto_aux.getFechaCaducidad();
		
	}
	
}

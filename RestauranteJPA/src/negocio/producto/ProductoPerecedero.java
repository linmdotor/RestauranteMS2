/**
 * 
 */
package negocio.producto;

import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class ProductoPerecedero extends Producto{

	/**
	 * 
	 */
	/*@Version
	 private long version;*/
	
	private String fechaCaducidad;
	
	public ProductoPerecedero() {
		
	}

	public ProductoPerecedero(TProductoPerecedero tProducto) {
		
		this.id_producto = tProducto.getId_producto();
		this.disponible = tProducto.isDisponible();
		this.stock = tProducto.getStock();
		this.nombre = tProducto.getNombre();
		this.fechaCaducidad = tProducto.getFechaCaducidad();
	}

	public String getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(String fecha) {
		this.fechaCaducidad = fecha; 
	}
	
	public void setAll(TProducto nuevoTProducto) {
		
		TProductoPerecedero boProducto_aux = (TProductoPerecedero) nuevoTProducto;
		
		this.id_producto = boProducto_aux.getId_producto();
		this.disponible = boProducto_aux.isDisponible();
		this.stock = boProducto_aux.getStock();
		this.nombre = boProducto_aux.getNombre();
		this.fechaCaducidad = boProducto_aux.getFechaCaducidad();
		
	}
	
}

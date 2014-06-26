/**
 * 
 */
package negocio.producto.businessobject;

import javax.persistence.Entity;
import javax.persistence.Version;

import negocio.producto.transfer.TProducto;
import negocio.producto.transfer.TProductoPerecedero;

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
		
		TProductoPerecedero tProducto_aux = (TProductoPerecedero) nuevoTProducto;
		
		this.id_producto = tProducto_aux.getId_producto();
		this.disponible = tProducto_aux.isDisponible();
		this.stock = tProducto_aux.getStock();
		this.nombre = tProducto_aux.getNombre();
		this.fechaCaducidad = tProducto_aux.getFechaCaducidad();
		
	}
	
}

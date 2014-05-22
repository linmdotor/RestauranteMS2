package negocio.producto;

import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class ProductoNoPerecedero extends Producto{
	
	/**
	 * 
	 */
	/*@Version
	 private long version;*/
	
	private String recomendaciones;

	public ProductoNoPerecedero() {
		
	}

	public ProductoNoPerecedero(TProductoNoPerecedero tProducto) {
		
		this.id_producto = tProducto.getId_producto();
		this.disponible = tProducto.isDisponible();
		this.stock = tProducto.getStock();
		this.nombre = tProducto.getNombre();
		this.recomendaciones = tProducto.getRecomendaciones();
	}

	public String getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(String recomendaciones) {
		this.recomendaciones = recomendaciones;
	}
	
	public void setAll(TProducto nuevoTProducto) {
		
		TProductoNoPerecedero boProducto_aux = (TProductoNoPerecedero) nuevoTProducto;
		
		this.id_producto = boProducto_aux.getId_producto();
		this.disponible = boProducto_aux.isDisponible();
		this.stock = boProducto_aux.getStock();
		this.nombre = boProducto_aux.getNombre();
		this.recomendaciones = boProducto_aux.getRecomendaciones();
				
	}

}

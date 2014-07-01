package negocio.producto.businessobject;

import javax.persistence.Entity;

import negocio.producto.transfer.TProducto;
import negocio.producto.transfer.TProductoNoPerecedero;

@Entity
public class ProductoNoPerecedero extends Producto{
	
	//Atributos
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
		
		TProductoNoPerecedero tProducto_aux = (TProductoNoPerecedero) nuevoTProducto;
		
		this.id_producto = tProducto_aux.getId_producto();
		this.disponible = tProducto_aux.isDisponible();
		this.stock = tProducto_aux.getStock();
		this.nombre = tProducto_aux.getNombre();
		this.recomendaciones = tProducto_aux.getRecomendaciones();
				
	}

}

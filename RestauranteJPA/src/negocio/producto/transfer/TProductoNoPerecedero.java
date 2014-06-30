package negocio.producto.transfer;

import negocio.producto.businessobject.Producto;
import negocio.producto.businessobject.ProductoNoPerecedero;

public class TProductoNoPerecedero extends TProducto{
	
	private String recomendaciones;
	
	public TProductoNoPerecedero() {
		
	}
	
	public TProductoNoPerecedero(int ID_Plato, boolean disponible, int stock, String nombre) {
		
		super(ID_Plato, disponible, stock, nombre);
		
	}
	
	public TProductoNoPerecedero(Producto producto) {
		
		ProductoNoPerecedero productoNoPerecedero = (ProductoNoPerecedero) producto;
		
		this.id_producto = productoNoPerecedero.getId_producto();
		this.disponible = productoNoPerecedero.isDisponible();
		this.stock = productoNoPerecedero.getStock();
		this.nombre = productoNoPerecedero.getNombre();
		this.recomendaciones = productoNoPerecedero.getRecomendaciones();
		
	}
	
	public String getRecomendaciones() {
		return recomendaciones;
	}

	public void setRecomendaciones(String recomendaciones) {
		
		this.recomendaciones = recomendaciones;
		
	}
	
	public void setAll(TProducto nuevoTProducto) {
		
		TProductoNoPerecedero boProducto_aux = (TProductoNoPerecedero) nuevoTProducto;
		
		this.id_producto = nuevoTProducto.getId_producto();
		this.disponible = nuevoTProducto.isDisponible();
		this.stock = nuevoTProducto.getStock();
		this.nombre = nuevoTProducto.getNombre();
		this.recomendaciones = boProducto_aux.getRecomendaciones();
				
	}

}

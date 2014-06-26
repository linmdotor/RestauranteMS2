package negocio.producto;

import java.util.List;
import negocio.producto.transfer.TProducto;


public interface SAProducto {

	public TProducto obtenerProducto(int ID) throws Exception;
	
	public List<TProducto> obtenerProductos() throws Exception;
	
	public List<TProducto> obtenerProductosDisponibles() throws Exception;
	
	public boolean altaProducto(TProducto tProducto) throws Exception;
		
	public boolean modificarProducto(TProducto tProducto) throws Exception;

	public boolean bajaProducto(int ID)throws Exception;
	
}

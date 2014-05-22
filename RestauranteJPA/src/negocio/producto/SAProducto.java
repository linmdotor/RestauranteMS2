package negocio.producto;

import java.util.List;

import presentacion.controlador.RespuestaCMD;

public interface SAProducto {

	public TProducto obtenerProducto(int ID) throws Exception;
	
	public List<Producto> obtenerProductos()throws Exception;
	
	public RespuestaCMD altaProducto(Object objeto)throws Exception;
		
	public RespuestaCMD modificarProducto(Object objeto)throws Exception;

	public RespuestaCMD bajaProducto(int ID)throws Exception;
	
}

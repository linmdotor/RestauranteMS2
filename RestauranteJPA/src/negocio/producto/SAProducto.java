package negocio.producto;

import java.util.ArrayList;
import java.util.List;

import presentacion.controlador.RespuestaCMD;

public interface SAProducto {

	public TProducto obtenerProducto(int ID) throws Exception;
	
	public ArrayList<TProducto> obtenerProductos()throws Exception;
	
	public boolean altaProducto(TProducto tProducto)throws Exception;
		
	public boolean modificarProducto(TProducto tProducto)throws Exception;

	public boolean bajaProducto(int ID)throws Exception;
	
}

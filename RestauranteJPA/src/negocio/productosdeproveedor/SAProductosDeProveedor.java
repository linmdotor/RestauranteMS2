package negocio.productosdeproveedor;

import java.util.List;

import presentacion.controlador.RespuestaCMD;

public interface SAProductosDeProveedor {

	public RespuestaCMD anadirProductoProveedor(Object objeto) throws Exception;
	public List<TProductoDeProveedor> obtenerProductosProveedor(int ID) throws Exception;
	public RespuestaCMD modificarProductoProveedor(Object objeto)throws Exception;
	public RespuestaCMD bajaProductoProveedor(Object objeto)throws Exception;
	
}

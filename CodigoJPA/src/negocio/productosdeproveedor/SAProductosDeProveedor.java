package negocio.productosdeproveedor;

import java.util.List;

import presentacion.controlador.RespuestaCMD;

public interface SAProductosDeProveedor {

	public RespuestaCMD anadirProductoProveedor(Object objeto) throws Exception;
	public List<ProductosDeProveedor> obtenerProductosProveedor(Object objecto) throws Exception;
	public RespuestaCMD modificarProductoProveedor(Object objeto)throws Exception;
	public RespuestaCMD bajaProductoProveedor()throws Exception;
	
}

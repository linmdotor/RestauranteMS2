package negocio.productosdeproveedor;

import java.util.List;

import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import presentacion.controlador.RespuestaCMD;

public interface SAProductosDeProveedor {

	public boolean anadirProductoProveedor(TProductoDeProveedor tProductoDeProveedor) throws Exception;
	public List<TProductoDeProveedor> obtenerProductosProveedor(int ID) throws Exception;
	public boolean modificarProductoProveedor(TProductoDeProveedor tProductoDeProveedor)throws Exception;
	public boolean bajaProductoProveedor(TProductoDeProveedor tProductoDeProveedor)throws Exception;
	
}

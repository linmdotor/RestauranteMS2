package negocio.productosdeproveedor;

import java.util.List;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;

public interface SAProductoDeProveedor {

	public TProductoDeProveedor obtenerProductoProveedor(int IDprod, int IDprov) throws Exception;
	
	public List<TProductoDeProveedor> obtenerProductosProveedor(int ID) throws Exception;
	
	public boolean anadirProductoProveedor(TProductoDeProveedor tProductoDeProveedor) throws Exception;
	
	public boolean modificarProductoProveedor(TProductoDeProveedor tProductoDeProveedor)throws Exception;
	
	public boolean bajaProductoProveedor(TProductoDeProveedor tProductoDeProveedor)throws Exception;
	
}

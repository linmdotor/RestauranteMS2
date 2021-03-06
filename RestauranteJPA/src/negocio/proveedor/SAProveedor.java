package negocio.proveedor;

import java.util.List;

import negocio.proveedor.transfer.TProveedor;

public interface SAProveedor {

	public TProveedor obtenerProveedor(int ID) throws Exception;
	
	public List<TProveedor> obtenerProveedores() throws Exception;
	
	public List<TProveedor> obtenerProveedoresDisponibles() throws Exception;
	
	public boolean altaProveedor(TProveedor tproveedor) throws Exception;
		
	public boolean modificarProveedor(TProveedor tproveedor) throws Exception;

	public boolean bajaProveedor(int ID) throws Exception;
	
}

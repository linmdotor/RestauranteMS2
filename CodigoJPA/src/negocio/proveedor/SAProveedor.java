package negocio.proveedor;

import java.util.List;

import presentacion.controlador.RespuestaCMD;

public interface SAProveedor {

	public TProveedor obtenerProveedor(int ID) throws Exception;
	
	public List<TProveedor> obtenerProveedores() throws Exception;
	
	public boolean altaProveedor(TProveedor tproveedor) throws Exception;
		
	public RespuestaCMD modificarProveedor(Object objeto) throws Exception;

	public RespuestaCMD bajaProveedor(int ID)throws Exception;
	
}

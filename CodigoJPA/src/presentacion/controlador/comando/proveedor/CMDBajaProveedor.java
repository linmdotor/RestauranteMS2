
package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import presentacion.controlador.CMD;
import presentacion.controlador.RespuestaCMD;

public class CMDBajaProveedor implements CMD {

	public RespuestaCMD ejecuta(Object objeto) {

		return FactoriaNegocio.obtenerInstancia().generaSAProveedor().bajaProveedor(FactoriaNegocio.obtenerInstancia().generaSAProveedor().obtenerProveedores().get((Integer) objeto).getId_proveedor());

	}

}

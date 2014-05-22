package presentacion.controlador.comando.proveedor;

import negocio.factoria.FactoriaNegocio;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import negocio.proveedor.ValidarTProveedor;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAltaProveedor implements CMD {

	// Metodos

	public RespuestaCMD ejecuta(Object objeto) {
		SAProveedor serviciosproveedor = FactoriaNegocio.obtenerInstancia().generaSAProveedor();
		RespuestaCMD respuestacomando = null;
		
		if(new ValidarTProveedor().proveedorCorrecto((TProveedor) objeto))
		{
			try {
				if(serviciosproveedor.altaProveedor((TProveedor)objeto))
					respuestacomando = new RespuestaCMD(EnumComandos.CORRECTO_PROVEEDOR, "Se ha añadido el proveedor.");
				else
					respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al insertar proveedor. Error al insertar los datos.");
			} catch (Exception e) {
				respuestacomando = new RespuestaCMD(EnumComandos.ERROR, e.getMessage());
				e.printStackTrace();
			}
		}
		else
			respuestacomando = new RespuestaCMD(EnumComandos.ERROR, "Error al insertar proveedor. Los datos no son válidos.");
			
		return respuestacomando;
	}

}

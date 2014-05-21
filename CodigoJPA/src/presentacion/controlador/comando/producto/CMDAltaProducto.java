package presentacion.controlador.comando.producto;

import negocio.factoria.FactoriaNegocio;
import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import negocio.producto.ValidarTProducto;
import presentacion.controlador.CMD;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;

public class CMDAltaProducto implements CMD {

	// Metodos

	public RespuestaCMD ejecuta(Object objeto) {
		
		return FactoriaNegocio.obtenerInstancia().generaSAProducto().altaProducto(objeto);

	}
		
}

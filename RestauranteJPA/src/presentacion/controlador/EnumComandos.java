package presentacion.controlador;

public enum EnumComandos {
	
	INICIAR_VISTA_PRINCIPAL,
	
	// PRODUCTO
	
	ALTA_PRODUCTO,
	BAJA_PRODUCTO,
	MODIFICAR_PRODUCTO,
	OBTENER_PRODUCTOS,
	MODIFICAR_FORMULARIO_PRODUCTO,
	INICIAR_VISTA_PRODUCTO,
	
	// PROVEEDOR
	
	ALTA_PROVEEDOR,
	BAJA_PROVEEDOR,
	MODIFICAR_PROVEEDOR,
	OBTENER_PROVEEDORES,
	INICIAR_VISTA_PROVEEDOR,
	MODIFICAR_FORMULARIO_PROVEEDOR,
	
	// PRODUCTOS DE PROVEEDOR
	
	ANADIR_PRODUCTO_PROVEEDOR,
	ELIMINAR_PRODUCTO_PROVEEDOR,
	MODIFICAR_PRODUCTO_PROVEEDOR,
	OBTENER_PRODUCTOS_PROVEEDOR,
	OBTENER_PRODUCTOS_TOTALES,
	INICIAR_VISTA_PRODUCTOS_DE_PROVEEDOR,
	MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR,
		
	//GESTI�N DE PEDIDOS
	
	ALMACENAR_PEDIDO,
	CANCELAR_PEDIDO,
	OBTENER_PEDIDOS,
	OBTENER_PRODUCTOS_PEDIDO,	
	OBTENER_PROVEEDORES_DISPONIBLES,
	INICIAR_VISTA_PEDIDO,	
	
	//NUEVO PEDIDO
	 
	ANADIR_PRODUCTO_A_PEDIDO, 
	QUITAR_PRODUCTO_DE_PEDIDO,
	MODIFICAR_CANTIDAD_PRODUCTO_DE_PEDIDO, 
	TERMINAR_PEDIDO,
	OBTENER_PRODUCTOS_PROVEEDOR_PEDIDO,
	INICIAR_VISTA_ALTA_PEDIDO,
	
	// MENSAJES DE PANTALLA	
	
	CORRECTO_PRODUCTO,
	CORRECTO_PROVEEDOR,	
	CORRECTO_PRODUCTO_PROVEEDOR,
	CORRECTO_PEDIDO,
	CORRECTO,
	ERROR,   
	
}

package presentacion.controlador;

public enum EnumComandos {
	
	INICIAR_VISTA_PRINCIPAL,
	
	// PRODUCTO
	
	ALTA_PRODUCTO,
	BAJA_PRODUCTO,
	MODIFICAR_PRODUCTO,
	OBTENER_PRODUCTOS,
	INICIAR_VISTA_PRODUCTO,
	MODIFICAR_FORMULARIO_PRODUCTO,
	LIMPIAR_FORMULARIO_PRODUCTO,
	
	// PROVEEDOR
	
	ALTA_PROVEEDOR,
	BAJA_PROVEEDOR,
	MODIFICAR_PROVEEDOR,
	OBTENER_PROVEEDORES,
	INICIAR_VISTA_PROVEEDOR,
	MODIFICAR_FORMULARIO_PROVEEDOR,
	LIMPIAR_FORMULARIO_PROVEEDOR,
	
	// PRODUCTOS DE PROVEEDOR
	
	INICIAR_VISTA_PRODUCTOS_DE_PROVEEDOR,
	OBTENER_PRODUCTOS_PROVEEDOR,
	OBTENER_PRODUCTOS_TOTALES,
	ANADIR_PRODUCTO_PROVEEDOR,
	ELIMINAR_PRODUCTO_PROVEEDOR,
	MODIFICAR_PRODUCTO_PROVEEDOR,	
	MODIFICAR_FORMULARIO_PRODUCTO_PROVEEDOR,
		
	//PEDIDO
	
	INICIAR_VISTA_PEDIDO,	
	MODIFICAR_FORMULARIO_PEDIDO,
	OBTENER_PRODUCTOS_PEDIDO,	
	OBTENER_PROVEEDOR_PRODUCTO,
	OBTENER_PROVEEDORES_ACTIVOS,
	
	//NUEVO PEDIDO
	 
	INICIAR_VISTA_ALTA_PEDIDO,
	MODIFICAR_FORMULARIO_ALTA_PEDIDO,
	CANCELAR_PEDIDO,
	OBTENER_PEDIDOS,
	ALMACENAR_PEDIDO,
	
	// MENSAJES DE PANTALLA	
	
	CORRECTO_PRODUCTO,
	CORRECTO_PROVEEDOR,
	CORRECTO_PEDIDO,
	CORRECTO_PRODUCTO_PROVEEDOR,
	CORRECTO,
	ERROR,          	OBTENER_PEDIDO_PROVEEDOR, A�ADIR_PRODUCTO_PEDIDO
	
}

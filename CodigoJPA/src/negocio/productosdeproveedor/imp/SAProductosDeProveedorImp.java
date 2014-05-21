package negocio.productosdeproveedor.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;
import principal.Principal;
import negocio.producto.Producto;
import negocio.producto.ProductoPerecedero;
import negocio.producto.TProducto;
import negocio.producto.TProductoNoPerecedero;
import negocio.producto.TProductoPerecedero;
import negocio.productosdeproveedor.ProductosDeProveedor;
import negocio.productosdeproveedor.SAProductosDeProveedor;
import negocio.productosdeproveedor.TProductoDeProveedor;
import negocio.proveedor.Proveedor;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import negocio.proveedor.ValidarTProveedor;

public class SAProductosDeProveedorImp implements SAProductosDeProveedor{

	public RespuestaCMD anadirProductoProveedor(Object objeto)  throws Exception {
		
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error dando de alta un precio de un producto de proveedor.");				

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		TProductoDeProveedor tproductodeproveedor = new TProductoDeProveedor();
		tproductodeproveedor=(TProductoDeProveedor) objeto;

		Proveedor proveedor = em.find(Proveedor.class, tproductodeproveedor.getProveedor());
		Producto producto = em.find(Producto.class, tproductodeproveedor.getProducto());
		
		ProductosDeProveedor precioProductoProveedor = new ProductosDeProveedor(proveedor, producto, tproductodeproveedor.getPrecio());

		try {
			
			em.getTransaction().begin();
				
			em.persist(precioProductoProveedor);
			
			em.getTransaction().commit();	
			
			respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO, "Exito al insertar  precio de un producto de proveedor.");
		
		} catch(OptimisticLockException oe) {
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
		}			
		catch (Exception e) {
			
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al insertar precio de un producto de proveedor.. Error al insertar los datos.");
			
		} finally {
			 
			em.close();
			emf.close();
			
		 }			
		
		return respuestaComando;		
		
	}

	@Override
	public List<ProductosDeProveedor> obtenerProductosProveedor(Object objecto)
			throws Exception {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
/*>>>*/	TypedQuery query = em.createQuery("SELECT e FROM Proveedor e", ProductosDeProveedor.class);
		
		@SuppressWarnings("unchecked")
		List<ProductosDeProveedor> listaProductosDeProveedores = query.getResultList();
		
		em.close();
		emf.close();

		return listaProductosDeProveedores;
	}

	@Override
	public RespuestaCMD modificarProductoProveedor(Object objeto)
			throws Exception {

		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar Producto de Proveedor.");
			

		return respuestaComando;
	}

	@Override
	public RespuestaCMD bajaProductoProveedor() throws Exception {

		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de baja un Producto de Proveedor.");
			

		return respuestaComando;
	}

}
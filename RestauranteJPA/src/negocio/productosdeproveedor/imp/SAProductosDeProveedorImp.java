package negocio.productosdeproveedor.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
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
import negocio.productosdeproveedor.ProductosDeProveedorId;
import negocio.productosdeproveedor.SAProductosDeProveedor;
import negocio.productosdeproveedor.TProductoDeProveedor;
import negocio.proveedor.Proveedor;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import negocio.proveedor.ValidarTProveedor;

public class SAProductosDeProveedorImp implements SAProductosDeProveedor{

	public boolean anadirProductoProveedor(TProductoDeProveedor tProductoDeProveedor)  throws Exception {
		
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error dando de alta un precio de un producto de proveedor.");				

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();

		Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());
		Producto producto = em.find(Producto.class, tProductoDeProveedor.getProducto());
		
		ProductosDeProveedor precioProductoProveedor = new ProductosDeProveedor(proveedor, producto, tProductoDeProveedor.getPrecio());

		try {
			
			em.getTransaction().begin();
			em.lock(proveedor, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
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
		
		//return respuestaComando;	
		return true;
		
	}

	@Override
	public List<TProductoDeProveedor> obtenerProductosProveedor(int ID) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		//cambiar precio por el id del producto del proveedor, en la bbdd se llama proveedor_id_proveedor
		//pero al ponerlo en la consulta, dice que no puede encontrarlo.
		TypedQuery query = em.createQuery("select e from ProductosDeProveedor e where e.precio = :arg", ProductosDeProveedor.class);
		query.setParameter("arg", ID);
		List<ProductosDeProveedor> listaProductos = query.getResultList();
		
		em.close();
		emf.close();

		List<TProductoDeProveedor> listatransfer = new ArrayList<TProductoDeProveedor>();
		/*for(ProductosDeProveedorId pp : listaProductos)
		{
			listatransfer.add(new TProductoDeProveedor(pp));
		}*/
		
		return listatransfer;
		
		/* ESTO ES LO QUE ESTABA HACIENDO YO (LIN), PERO NO HE CONSEguIDO NADA, solo hace que explote el JPA
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		List<ProductosDeProveedor> ppObtenido = null;
		TypedQuery<ProductosDeProveedor> query = null;
		try { 
			em.getTransaction().begin();
			
			query = em.createQuery("select e from ProductosDeProveedor e where e.proveedor.id_proveedor = :arg", ProductosDeProveedor.class);
			
			query.setParameter("arg", ID);
			
			ppObtenido = query.getResultList();
			
			em.lock(ppObtenido, LockModeType.OPTIMISTIC);
			
			em.getTransaction().commit();
			
		}catch(NoResultException ex){
			
			em.getTransaction().rollback();
			
			throw new Exception("No existe el producto con ID: " + ID);
			
		} catch (Exception ex) {

			if (ex instanceof Exception) {

				em.getTransaction().rollback();
				
				throw ex;

			} else {

				em.getTransaction().rollback();
				
				throw new Exception(ex.getLocalizedMessage());

			}
			
		} finally {
	
			if (ppObtenido != null)
				em.detach(ppObtenido); //esta operación, al utilizar Transfer, no es necesaria.
	
			em.close();
	
		}		
		
		List<TProductoDeProveedor> listatransfer = new ArrayList<TProductoDeProveedor>();
		for(ProductosDeProveedor pp : ppObtenido)
		{
			TProductoDeProveedor tpp = new TProductoDeProveedor();
			tpp.setProducto(pp.getProducto().getId_producto());
			tpp.setProveedor(pp.getProveedor().getId_proveedor());
			tpp.setPrecio(pp.getPrecio());
			listatransfer.add(tpp);
		}
		
		return listatransfer;*/
		
	}

	@Override
	public boolean modificarProductoProveedor(TProductoDeProveedor tProductoDeProveedor) throws Exception {

		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar Producto de Proveedor.");
		
		if(tProductoDeProveedor.getPrecio() > 0){
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");	
			EntityManager em = emf.createEntityManager();
			
			try {
							
				em.getTransaction().begin();			
	
				Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());
				Producto producto = em.find(Producto.class, tProductoDeProveedor.getProducto());
				
				em.lock(proveedor, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				
				// Actualizamos
				
				proveedor.getListaProductosProveedor().get(proveedor.getListaProductosProveedor().indexOf(tProductoDeProveedor)).setPrecio(tProductoDeProveedor.getPrecio());
				
				em.getTransaction().commit();
				
				respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Se ha modificado el Producto de Proveedor.");
				
			} catch(OptimisticLockException oe) {
				
				em.getTransaction().rollback();
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
			}			
			catch (Exception e) {
				
				em.getTransaction().rollback();
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar un producto de un proveedor. Error al insertar los datos.");
				
			} finally {
				 
				em.close();
				emf.close();
				
			 }
		} else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "El Precio debe ser entero positivo.");
		
		//return respuestaComando;
		return true;
	}

	@Override
	public boolean bajaProductoProveedor(TProductoDeProveedor tProductoDeProveedor) throws Exception {

		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de baja un Producto de Proveedor.");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();
		
		try {
			
			em.getTransaction().begin();			

			Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());
			Producto producto = em.find(Producto.class, tProductoDeProveedor.getProducto());
			
			em.lock(proveedor, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			
			// Borramos
			
			em.remove(proveedor.getListaProductosProveedor().get(proveedor.getListaProductosProveedor().indexOf(tProductoDeProveedor)));
									
			proveedor.getListaProductosProveedor().remove(proveedor.getListaProductosProveedor().indexOf(tProductoDeProveedor));
			
			em.getTransaction().commit();
			
			respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Se ha eliminado Producto de Proveedor.");
			
		} catch(OptimisticLockException oe) {
			
			em.getTransaction().rollback();
			
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
		}			
		catch (Exception e) {
			
			em.getTransaction().rollback();
			
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al eliminar producto de un proveedor. Error al insertar los datos.");
			
		} finally {
			 
			em.close();
			emf.close();
			
		}
		
		//return respuestaComando;
		return true;
	}

}
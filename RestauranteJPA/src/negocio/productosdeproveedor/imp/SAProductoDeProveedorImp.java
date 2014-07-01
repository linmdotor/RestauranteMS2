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

import negocio.producto.businessobject.Producto;
import negocio.productosdeproveedor.SAProductoDeProveedor;
import negocio.productosdeproveedor.businessobject.ProductoDeProveedor;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import negocio.proveedor.businessobject.Proveedor;

public class SAProductoDeProveedorImp implements SAProductoDeProveedor{

	
	@Override
	public TProductoDeProveedor obtenerProductoProveedor(int IDprod, int IDprov) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		TProductoDeProveedor resultado = null;
		
		Proveedor proveedorObtenido = em.find(Proveedor.class, IDprov);
		TypedQuery<Proveedor> query = null;
		
		try{
			
			em.getTransaction().begin();
			
			query = em.createNamedQuery(Proveedor.QUERY_OBTENER_PROVEEDOR, Proveedor.class);
			
			query.setParameter("arg", IDprov);
			
			proveedorObtenido = query.getSingleResult();
			
			em.lock(proveedorObtenido, LockModeType.OPTIMISTIC);
			
			em.getTransaction().commit();
							
		} catch(NoResultException ex){
			
			em.getTransaction().rollback();			
			throw new Exception("No existe el proveedor con ID: " + IDprov);
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
			
		} finally {

			em.close();
			emf.close();
	
		}		
		
		List<ProductoDeProveedor> listaproductos = proveedorObtenido.getListaProductosProveedor();
		
		for(ProductoDeProveedor p : listaproductos)
		{
			if(p.getProducto().getId_producto() == IDprod)
				return(new TProductoDeProveedor(p));
		}
		
		return resultado;
		
	}
	
	@Override
	public List<TProductoDeProveedor> obtenerProductosProveedor(int ID) throws Exception {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		Proveedor proveedorObtenido = em.find(Proveedor.class, ID);
		TypedQuery<Proveedor> query = null;
		
		try{
			
			em.getTransaction().begin();
			
			query = em.createNamedQuery(Proveedor.QUERY_OBTENER_PROVEEDOR, Proveedor.class);
			
			query.setParameter("arg", ID);
			
			proveedorObtenido = query.getSingleResult();
			
			em.lock(proveedorObtenido, LockModeType.OPTIMISTIC);
			
			em.getTransaction().commit();
							
		} catch(NoResultException ex){
			
			em.getTransaction().rollback();
			
			throw new Exception("No existe el proveedor con ID: " + ID);
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
			
		} finally {

			em.close();
			emf.close();
	
		}		
		
		List<ProductoDeProveedor> listaproductos = proveedorObtenido.getListaProductosProveedor();
		
		List<TProductoDeProveedor> listatProd = new ArrayList<TProductoDeProveedor>();
		
		for(ProductoDeProveedor p : listaproductos)
		{
			listatProd.add(new TProductoDeProveedor(p));
		}
		
		return listatProd;
		
	}

public boolean anadirProductoProveedor(TProductoDeProveedor tProductoDeProveedor)  throws Exception {
		
		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		try {
			//Realiza todas las validaciones del transfer
			if(tProductoDeProveedor == null)
			{
				throw new Exception("ProductoProveedor nulo");
			}
			
			if(tProductoDeProveedor.getPrecio() < 0)
			{
				throw new Exception("El precio del producto debe ser >= 0");
			}
			
			if(tProductoDeProveedor.getProducto() < 0)
			{
				throw new Exception("El ID de Producto tiene que ser >= 0");
			}
			
			if(tProductoDeProveedor.getProveedor() < 0)
			{
				throw new Exception("El ID de Proveedor tiene que ser >= 0");
			}
			
			//Valida que no se haya insertado ya ese producto-proveedor
			if(obtenerProductoProveedor(tProductoDeProveedor.getProducto(), tProductoDeProveedor.getProveedor()) != null)
			{
				throw new Exception("Ya existe ese producto en ese proveedor");
			}
			
			Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());
			em.lock(proveedor, LockModeType.OPTIMISTIC);
			Producto producto = em.find(Producto.class, tProductoDeProveedor.getProducto());
			em.lock(producto, LockModeType.OPTIMISTIC);
			
			ProductoDeProveedor precioProductoProveedor = new ProductoDeProveedor(proveedor, producto, tProductoDeProveedor.getPrecio());
					
			em.persist(precioProductoProveedor);
			
			em.getTransaction().commit();	
			
			resultado =  true;
		
		} catch(OptimisticLockException oe) {			
			em.getTransaction().rollback();			
			throw new Exception("No se pudo añadir el producto al proveedor, porque está bloqueado");
		}			
		catch (Exception e) {			
			em.getTransaction().rollback();			
			throw e;
		} finally {
			 
			em.close();
			emf.close();
			
		 }			
		
		return resultado;
	}

	
	@Override
	public boolean modificarProductoProveedor(TProductoDeProveedor tProductoDeProveedor) throws Exception {

		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");	
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		try {
			
			//Realiza todas las validaciones del transfer
			if(tProductoDeProveedor == null)
			{
				throw new Exception("ProductoProveedor nulo");
			}
			
			if(tProductoDeProveedor.getPrecio() < 0)
			{
				throw new Exception("El precio del producto debe ser >= 0");
			}
			
			if(tProductoDeProveedor.getProducto() < 0)
			{
				throw new Exception("El ID de Producto tiene que ser >= 0");
			}
			
			if(tProductoDeProveedor.getProveedor() < 0)
			{
				throw new Exception("El ID de Proveedor tiene que ser >= 0");
			}
			
			//Valida que exista ese producto-proveedor
			if(obtenerProductoProveedor(tProductoDeProveedor.getProducto(), tProductoDeProveedor.getProveedor()) == null)
			{
				throw new Exception("No existe ese producto en ese proveedor");
			}
												
			Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());			
			em.lock(proveedor, LockModeType.OPTIMISTIC);
			
			// Actualizamos ese producto en concreto buscando en la lista cual es el que corresponde a ese ID			
			for(ProductoDeProveedor pp : proveedor.getListaProductosProveedor())
			{
				if(pp.getProducto().getId_producto() == tProductoDeProveedor.getProducto())
					pp.setPrecio(tProductoDeProveedor.getPrecio());
			}
						
			em.getTransaction().commit();			
			resultado = true;
			
		} catch(OptimisticLockException oe) {			
			em.getTransaction().rollback();				
			throw new Exception("No se pudo modificar el producto al proveedor, porque está bloqueado");
		}			
		catch (Exception e) {				
			em.getTransaction().rollback();
			throw e;				
		} finally {
			 
			em.close();
			emf.close();
			
		 }
		
		return resultado;
	}

	@Override
	public boolean bajaProductoProveedor(TProductoDeProveedor tProductoDeProveedor) throws Exception {

		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		try {
			
			//Realiza todas las validaciones del transfer
			if(tProductoDeProveedor == null)
			{
				throw new Exception("ProductoProveedor nulo");
			}
			
			//Como es para eliminar, el precio nos da igual
			
			if(tProductoDeProveedor.getProducto() < 0)
			{
				throw new Exception("El ID de Producto tiene que ser >= 0");
			}
			
			if(tProductoDeProveedor.getProveedor() < 0)
			{
				throw new Exception("El ID de Proveedor tiene que ser >= 0");
			}
			
			//Valida que exista ese producto-proveedor
			if(obtenerProductoProveedor(tProductoDeProveedor.getProducto(), tProductoDeProveedor.getProveedor()) == null)
			{
				throw new Exception("No existe ese producto en ese proveedor");
			}
			
			Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());			
			em.lock(proveedor, LockModeType.OPTIMISTIC);
			
			// Borramos	ese producto en concreto buscando en la lista cual es el que corresponde a ese ID	
			ProductoDeProveedor productoAeliminar = null;
			for(ProductoDeProveedor pp : proveedor.getListaProductosProveedor())
			{
				if(pp.getProducto().getId_producto() == tProductoDeProveedor.getProducto())
				{
					em.remove(pp);
					productoAeliminar = pp;
				}
			}
			
			proveedor.getListaProductosProveedor().remove(proveedor.getListaProductosProveedor().indexOf(productoAeliminar));
			
			em.getTransaction().commit();
			
			resultado = true;
			
		} catch(OptimisticLockException oe) {			
			em.getTransaction().rollback();
			throw new Exception("No se pudo eliminar el producto al proveedor, porque está bloqueado");
		}			
		catch (Exception e) {		
			em.getTransaction().rollback();
			throw e;
		} finally {
			 
			em.close();
			emf.close();
			
		}
		
		return resultado;
	}

}
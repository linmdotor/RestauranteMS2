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
import negocio.productosdeproveedor.SAProductosDeProveedor;
import negocio.productosdeproveedor.businessobject.ProductosDeProveedor;
import negocio.productosdeproveedor.transfer.TProductoDeProveedor;
import negocio.proveedor.businessobject.Proveedor;

public class SAProductosDeProveedorImp implements SAProductosDeProveedor{

	public boolean anadirProductoProveedor(TProductoDeProveedor tProductoDeProveedor)  throws Exception {
		
		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();

		Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());
		Producto producto = em.find(Producto.class, tProductoDeProveedor.getProducto());
		
		ProductosDeProveedor precioProductoProveedor = new ProductosDeProveedor(proveedor, producto, tProductoDeProveedor.getPrecio());

		try {
			
			em.getTransaction().begin();
			
			em.lock(proveedor, LockModeType.OPTIMISTIC);
			
			em.persist(precioProductoProveedor);
			
			em.getTransaction().commit();	
			
			resultado =  true;
		
		} catch(OptimisticLockException oe) {			
			em.getTransaction().rollback();			
			throw new Exception("No se pudo añadir el producto al proveedor, porque está bloqueado");
		}			
		catch (Exception e) {			
			em.getTransaction().rollback();			
			throw new Exception("No se pudo añadir el producto al proveedor.");
		} finally {
			 
			em.close();
			emf.close();
			
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

			if (ex instanceof Exception) {

				em.getTransaction().rollback();
				
				throw ex;

			} else {

				em.getTransaction().rollback();
				
				throw new Exception(ex.getLocalizedMessage());

			}
			
		} finally {

			em.close();
			emf.close();
	
		}		
		
		List<ProductosDeProveedor> listaproductos = proveedorObtenido.getListaProductosProveedor();
		
		List<TProductoDeProveedor> listatProd = new ArrayList<TProductoDeProveedor>();
		
		for(ProductosDeProveedor p : listaproductos)
		{
			listatProd.add(new TProductoDeProveedor(p));
		}
		
		return listatProd;
		
	}

	@Override
	public boolean modificarProductoProveedor(TProductoDeProveedor tProductoDeProveedor) throws Exception {

		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");	
		EntityManager em = emf.createEntityManager();
		
		try {
						
			em.getTransaction().begin();			

			Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());
			
			em.lock(proveedor, LockModeType.OPTIMISTIC);
			
			// Actualizamos ese producto en concreto buscando en la lista cual es el que corresponde a ese ID			
			for(ProductosDeProveedor pp : proveedor.getListaProductosProveedor())
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
			throw new Exception("No se pudo modificar el producto al proveedor.");				
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
		
		try {
			
			em.getTransaction().begin();			

			Proveedor proveedor = em.find(Proveedor.class, tProductoDeProveedor.getProveedor());
			
			em.lock(proveedor, LockModeType.OPTIMISTIC);
			
			// Borramos	ese producto en concreto buscando en la lista cual es el que corresponde a ese ID	
			ProductosDeProveedor productoAeliminar = null;
			for(ProductosDeProveedor pp : proveedor.getListaProductosProveedor())
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
			throw new Exception("No se pudo eliminar el producto al proveedor.");
		} finally {
			 
			em.close();
			emf.close();
			
		}
		
		return resultado;
	}

}
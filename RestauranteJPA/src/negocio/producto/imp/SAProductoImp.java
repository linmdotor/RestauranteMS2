package negocio.producto.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import negocio.producto.Producto;
import negocio.producto.ProductoNoPerecedero;
import negocio.producto.ProductoPerecedero;
import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import negocio.producto.TProductoNoPerecedero;
import negocio.producto.TProductoPerecedero;



public class SAProductoImp implements SAProducto {

	protected EntityManager em;
		
	public TProducto obtenerProducto(int ID) throws Exception {
				
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		TProducto tProducto;
		Producto productoObtenido = null;
		TypedQuery<Producto> query = null;
		
		try { 
			em.getTransaction().begin();
			
			query = em.createNamedQuery(Producto.QUERY_OBTENER_PRODUCTO, Producto.class);
			
			query.setParameter("arg", ID);
			
			productoObtenido = query.getSingleResult();
			
			em.lock(productoObtenido, LockModeType.OPTIMISTIC);
			
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
	
			if (productoObtenido != null)
				em.detach(productoObtenido);
	
			em.close();
	
		}
		
		if(productoObtenido instanceof ProductoPerecedero)
			tProducto = new TProductoPerecedero(productoObtenido);
		else //(productoObtenido instanceof ProductoNoPerecedero)
			tProducto = new TProductoNoPerecedero(productoObtenido);
		
		return tProducto;
		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TProducto> obtenerProductos() throws Exception {		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Producto e", Producto.class);
		
		List<Producto> listaProductos = query.getResultList();
		
		em.close();
		emf.close();
		
		ArrayList<TProducto> listaTProd = new ArrayList<TProducto>(); //se crea una arraylist de tProd para desvincularlo del BO
		
		for(Producto prod : listaProductos)
		{
			TProducto tProducto;
			
			if(prod instanceof ProductoPerecedero)
				tProducto = new TProductoPerecedero(prod);
			else //(prod instanceof ProductoNoPerecedero)
				tProducto = new TProductoNoPerecedero(prod);
			
			listaTProd.add(tProducto);
		}

		return listaTProd;
		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<TProducto> obtenerProductosDisponibles() throws Exception {		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Producto e where e.disponible = 1", Producto.class);
		
		List<Producto> listaProductos = query.getResultList();
		
		em.close();
		emf.close();
		
		ArrayList<TProducto> listaTProd = new ArrayList<TProducto>(); //se crea una arraylist de tProd para desvincularlo del BO
		
		for(Producto prod : listaProductos)
		{
			TProducto tProducto;
			
			if(prod instanceof ProductoPerecedero)
				tProducto = new TProductoPerecedero(prod);
			else //(prod instanceof ProductoNoPerecedero)
				tProducto = new TProductoNoPerecedero(prod);
			
			listaTProd.add(tProducto);
		}

		return listaTProd;
		
	}
	
	public boolean altaProducto(TProducto tProducto) throws Exception {		
		
		boolean respuesta = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			
			if(tProducto instanceof TProductoPerecedero){
				
				ProductoPerecedero producto = new ProductoPerecedero((TProductoPerecedero) tProducto);
				em.persist(producto);
				
			}
			else{ //if(tProducto instanceof TProductoNoPerecedero)
				
				ProductoNoPerecedero producto = new ProductoNoPerecedero((TProductoNoPerecedero) tProducto);
				em.persist(producto);
			}

			em.getTransaction().commit();
			respuesta = true;
		
		} catch(OptimisticLockException oe) {
			throw new Exception("No se pudo añadir el producto, porque está bloqueado");
		} catch (Exception e) {
			throw new Exception("No se pudo añadir el producto.");
		} finally {
			 
			em.close();
			emf.close();
			
		 }	
		
		return respuesta;

	}		
	
	public boolean modificarProducto(TProducto tProducto) throws Exception {
		
		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			
			Producto producto = em.find(Producto.class, (tProducto.getId_producto()));
				
			if (producto != null){
				
				if((tProducto instanceof TProductoPerecedero && producto instanceof ProductoNoPerecedero)
						|| (tProducto instanceof TProductoNoPerecedero && producto instanceof ProductoPerecedero))
					throw new Exception("No se puede modificar el tipo del Producto.");	    	

				if (producto instanceof ProductoPerecedero) 
					((ProductoPerecedero)producto).setAll(tProducto);
				else
					((ProductoNoPerecedero)producto).setAll(tProducto);	
				
				em.getTransaction().commit();		
				resultado = true;
			}									
				
		} catch(OptimisticLockException oe) {
			throw new Exception("No se pudo modificar el proveedor, porque está bloqueado");
		}
		finally {
			 
			em.close();
			emf.close();
			
		 }		

		return resultado;
		
		
	}
	
	public boolean bajaProducto(int ID)  throws Exception {
				
		boolean respuesta = false;
		
		if (ID >= 0){				
		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
			EntityManager em = emf.createEntityManager();
			
			try {
				
				em.getTransaction().begin();
				
				Producto producto = em.find(Producto.class, ID);
					
				if (producto != null){
					producto.setDisponible(false);
					respuesta = true;
					em.getTransaction().commit();		
				}
					
			} catch(OptimisticLockException oe) {
				throw new Exception("No se pudo eliminar el producto, porque está bloqueado");
			}			
			catch (Exception e) {
				throw new Exception("No se pudo eliminar el producto.");
			} finally {
				 
				em.close();
				emf.close();
				
			}	
			
		} else
			throw new Exception("No se pudo eliminar el producto. No existe el ID " + ID);
			
		return respuesta;
		
	}

}

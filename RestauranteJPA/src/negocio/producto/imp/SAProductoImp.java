package negocio.producto.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import negocio.producto.SAProducto;
import negocio.producto.businessobject.Producto;
import negocio.producto.businessobject.ProductoNoPerecedero;
import negocio.producto.businessobject.ProductoPerecedero;
import negocio.producto.transfer.TProducto;
import negocio.producto.transfer.TProductoNoPerecedero;
import negocio.producto.transfer.TProductoPerecedero;



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
			em.getTransaction().rollback();
			
			if (ex instanceof Exception) {

				throw ex;

			} else {

				throw new Exception(ex.getLocalizedMessage());

			}
			
		} finally {

			em.close();
			emf.close();
	
		}
		
		if(productoObtenido instanceof ProductoPerecedero)
			tProducto = new TProductoPerecedero(productoObtenido);
		else //(productoObtenido instanceof ProductoNoPerecedero)
			tProducto = new TProductoNoPerecedero(productoObtenido);
		
		return tProducto;
		
	}
	
	public TProducto obtenerProductoPorNombre(String nombre) throws Exception {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		TProducto tProducto;
		Producto productoObtenido = null;
		TypedQuery<Producto> query = null;
		
		try { 
			em.getTransaction().begin();
			
			query = em.createNamedQuery(Producto.QUERY_OBTENER_PRODUCTO_NOMBRE, Producto.class);
			
			query.setParameter("arg", nombre);
			
			productoObtenido = query.getSingleResult();
			
			em.lock(productoObtenido, LockModeType.OPTIMISTIC);
			
			em.getTransaction().commit();
			
		}catch(NoResultException ex){			
			em.getTransaction().rollback();			
			return null;
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			
			if (ex instanceof Exception) {

				throw ex;

			} else {

				throw new Exception(ex.getLocalizedMessage());

			}
			
		} finally {

			em.close();
			emf.close();
	
		}
		
		if(productoObtenido instanceof ProductoPerecedero)
			tProducto = new TProductoPerecedero(productoObtenido);
		else //(productoObtenido instanceof ProductoNoPerecedero)
			tProducto = new TProductoNoPerecedero(productoObtenido);
		
		return tProducto;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TProducto> obtenerProductos() throws Exception {		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Producto e", Producto.class);
		
		List<Producto> listaProductos = query.getResultList();
		
		em.close();
		emf.close();
		
		List<TProducto> listaTProd = new ArrayList<TProducto>(); //se crea una arraylist de tProd para desvincularlo del BO
		
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
	public List<TProducto> obtenerProductosDisponibles() throws Exception {		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Producto e where e.disponible = 1", Producto.class);
		
		List<Producto> listaProductos = query.getResultList();
		
		em.close();
		emf.close();
		
		List<TProducto> listaTProd = new ArrayList<TProducto>(); //se crea una arraylist de tProd para desvincularlo del BO
		
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
		
		em.getTransaction().begin();
		
		//Realiza todas las validaciones del transfer
		if(tProducto == null)
		{
			em.getTransaction().rollback();
			throw new Exception("Producto nulo");
		}
		
		if(!tProducto.isDisponible())
		{
			em.getTransaction().rollback();
			throw new Exception("Se debe insertar el producto como 'disponible'");
		}
		
		if(tProducto.getStock() < 0)
		{
			em.getTransaction().rollback();
			throw new Exception("El Stock del producto no puede ser negativo");
		}
		
		if(tProducto.getNombre().length() <= 0)
		{
			em.getTransaction().rollback();
			throw new Exception("Debe ponerle un nombre al producto");
			
		}
		
		if(obtenerProductoPorNombre(tProducto.getNombre()) != null)
		{
			em.getTransaction().rollback();
			throw new Exception("Ya existe un producto con ese nombre");
		}
		
		if(tProducto instanceof TProductoPerecedero){
			//Validación de la fecha

			int dia = Integer.parseInt(((TProductoPerecedero) tProducto).getFechaCaducidad().split("-")[0]);
			int mes = Integer.parseInt(((TProductoPerecedero) tProducto).getFechaCaducidad().split("-")[1]);
			int ano = Integer.parseInt(((TProductoPerecedero) tProducto).getFechaCaducidad().split("-")[2]);
			
			if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) 
			{//meses 31
			
				if(dia<1 ||dia >31)
				{
					em.getTransaction().rollback();
					throw new Exception("El dia no puede ser mayor de 31");
				}
			} 
			else 
			{//meses 30 o menos
				if (mes == 4 || mes == 6 || mes == 9 || mes == 11) 
				{//meses 30
					if(dia >30)
					{
						em.getTransaction().rollback();
						throw new Exception("El dia no puede ser mayor de 30");
					}
				} 
				else if(mes == 2)
				{//febrero
					if ((ano%4 == 0 && ano % 100 != 0) || ano % 400 == 0) 
					{//es bisiesto
						if(dia >29)
						{
							em.getTransaction().rollback();
							throw new Exception("El dia no puede ser mayor de 29");
						}
					}
					else
					{//no bisiesto
						if(dia >28)
						{
							em.getTransaction().rollback();
							throw new Exception("El dia no puede ser mayor de 28");
						}
					}
				}
				else
				{
					em.getTransaction().rollback();
					throw new Exception("El mes tiene que ser del 1 al 12");
				}
			}
			
		} //Si es producto no perecedero, Las recomendaciones no tienen ningún tipo de restricción que validar
		
		try {
			
			//Como todo es correcto, lo inserta
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
			em.getTransaction().rollback();
			throw new Exception("No se pudo añadir el producto, porque está bloqueado");
		} catch (Exception e) {
			em.getTransaction().rollback();
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
			em.getTransaction().rollback();
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
				em.getTransaction().rollback();
				throw new Exception("No se pudo eliminar el producto, porque está bloqueado");
			}			
			catch (Exception e) {
				em.getTransaction().rollback();
				throw new Exception("No se pudo eliminar el producto.");
			} finally {
				 
				em.close();
				emf.close();
				
			}	
			
		} else
			throw new Exception("No se pudo eliminar el producto. No existe el ID " + ID);
			
		return respuesta;
		
	}
	
	boolean validarFecha()
	{
		return true;
	}

}

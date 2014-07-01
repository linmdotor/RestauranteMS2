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
			throw ex;

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
			throw ex;
			
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
		
		try {
		
			//Realiza todas las validaciones del transfer
			if(tProducto == null)
			{
				throw new Exception("Producto nulo");
			}
			
			if(!tProducto.isDisponible())
			{
				throw new Exception("Se debe insertar el producto como 'disponible'");
			}
			
			if(tProducto.getStock() < 0)
			{
				throw new Exception("El Stock del producto no puede ser negativo");
			}
			
			if(tProducto.getNombre().length() <= 0)
			{
				throw new Exception("Debe ponerle un nombre al producto");			
			}
			
			//Valida que el nombre no esté repetido
			if(obtenerProductoPorNombre(tProducto.getNombre()) != null)
			{
				throw new Exception("Ya existe un producto con ese nombre");
			}
			
			if(tProducto instanceof TProductoPerecedero)
			{	
				validarFecha(((TProductoPerecedero) tProducto).getFechaCaducidad());	
			} //Si es producto no perecedero, Las recomendaciones no tienen ningún tipo de restricción que validar
		
		
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
			throw e;
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
		
		em.getTransaction().begin();
			
		try {
			//Realiza todas las validaciones del transfer
			if(tProducto == null)
			{
				throw new Exception("Producto nulo");
			}
			
			if(!tProducto.isDisponible())
			{
				throw new Exception("Se debe insertar el producto como 'disponible'");
			}
			
			if(tProducto.getStock() < 0)
			{
				throw new Exception("El Stock del producto no puede ser negativo");
			}
			
			if(tProducto.getNombre().length() <= 0)
			{
				throw new Exception("Debe ponerle un nombre al producto");			
			}
			
			if(tProducto instanceof TProductoPerecedero)
			{	
				validarFecha(((TProductoPerecedero) tProducto).getFechaCaducidad());	
			} //Si es producto no perecedero, Las recomendaciones no tienen ningún tipo de restricción que validar
						
			//Obtiene el producto que va a modificar
			Producto producto = em.find(Producto.class, (tProducto.getId_producto()));
			em.lock(producto, LockModeType.OPTIMISTIC);
				
			if (producto != null){
				
				//Comprueba que el nuevo nombre no se repite (si alguien insertara el mismo paralelamente)
				if(obtenerProductoPorNombre(tProducto.getNombre()) != null && !tProducto.getNombre().equals(producto.getNombre()))
				{
					throw new Exception("Ya existe un producto con ese nombre");
				}
			
				if((tProducto instanceof TProductoPerecedero && producto instanceof ProductoNoPerecedero)
						|| (tProducto instanceof TProductoNoPerecedero && producto instanceof ProductoPerecedero))
					throw new Exception("No se puede modificar el tipo del Producto.");	    	
				
				//Como todo es correcto, lo modifica
				if (producto instanceof ProductoPerecedero) 
					((ProductoPerecedero)producto).setAll(tProducto);
				else
					((ProductoNoPerecedero)producto).setAll(tProducto);	
				
				em.getTransaction().commit();		
				resultado = true;
			}
			else
			{
				throw new Exception("No existe el producto con ese ID");
			}
				
		} catch(OptimisticLockException oe) {
			em.getTransaction().rollback();
			throw new Exception("No se pudo modificar el producto, porque está bloqueado");
		} catch(Exception e) {
			em.getTransaction().rollback();
			throw e;
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
			
			em.getTransaction().begin();
			
			try {
				
				Producto producto = em.find(Producto.class, ID);
				em.lock(producto, LockModeType.OPTIMISTIC);
				
				//Valida que el producto sigue existiendo antes de darlo de baja
				if (producto != null){
					if(!producto.isDisponible())
						throw new Exception("Este producto ya está marcado como NO disponible.");
					producto.setDisponible(false);
					respuesta = true;
					em.getTransaction().commit();		
				}
				else
				{
					throw new Exception("No existe el producto con ese ID.");
				}
				
			} catch(OptimisticLockException oe) {
				em.getTransaction().rollback();
				throw new Exception("No se pudo eliminar el producto, porque está bloqueado");
			}			
			catch (Exception e) {
				em.getTransaction().rollback();
				throw e;
			} finally {
				 
				em.close();
				emf.close();
				
			}	
			
		} else
			throw new Exception("No se pudo eliminar el producto. El ID debe ser positivo");
			
		return respuesta;
		
	}
	
	/*
	 * Valida una fecha en formato: DD:MM:YYYY
	 */
	boolean validarFecha(String fecha) throws Exception	{
		
		int dia;
		int mes;
		int ano;
		
		try{
			dia = Integer.parseInt(fecha.split("-")[0]);
			mes = Integer.parseInt(fecha.split("-")[1]);
			ano = Integer.parseInt(fecha.split("-")[2]);
		}
		catch(Exception e)
		{
			throw new Exception("El formato de fecha debe ser: DD-MM-YYYY");
		}
		
		if(dia <= 0 || mes <= 0 || ano <= 1000)
		{
			throw new Exception("La fecha debe ser mayor que 0");
		}
		
		if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) 
		{//meses de 31 dias
		
			if(dia >31)
			{
				throw new Exception("El mes " + mes + " no tiene más de 31 días");
			}
		} 
		else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) 
		{//meses 30
			if(dia >30)
			{
				throw new Exception("El mes " + mes + " no tiene más de 30 días");
			}
		}
		else if(mes == 2)
		{//febrero
			if ((ano%4 == 0 && ano % 100 != 0) || ano % 400 == 0) 
			{//es bisiesto
				if(dia >29)
				{
					
					throw new Exception("El dia en Febrero no puede ser mayor de 29 (ese año es bisiesto)");
				}
			}
			else
			{//no bisiesto
				if(dia >28)
				{
					
					throw new Exception("El dia en Febrero no puede ser mayor de 28 (ese año no es bisiesto)");
				}
			}
		}
		else
		{
			throw new Exception("Los meses son hasta 12");
		}
		
		
		return true;
	}

}

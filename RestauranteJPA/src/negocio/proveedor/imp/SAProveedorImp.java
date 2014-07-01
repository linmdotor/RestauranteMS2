package negocio.proveedor.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import negocio.proveedor.SAProveedor;
import negocio.proveedor.businessobject.Proveedor;
import negocio.proveedor.transfer.TProveedor;

public class SAProveedorImp implements SAProveedor {

	public TProveedor obtenerProveedor(int ID) throws Exception{
				
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		Proveedor proveedorObtenido = null;
		TypedQuery<Proveedor> query = null;
		try { 
			em.getTransaction().begin();
			
			query = em.createNamedQuery(Proveedor.QUERY_OBTENER_PROVEEDOR, Proveedor.class);
			
			query.setParameter("arg", ID);
			
			proveedorObtenido = query.getSingleResult();
			
			em.lock(proveedorObtenido, LockModeType.OPTIMISTIC);
			
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
		
		return new TProveedor(proveedorObtenido);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TProveedor> obtenerProveedores() throws Exception{		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Proveedor e", Proveedor.class);
		
		List<Proveedor> listaProveedores = query.getResultList();
		
		em.close();
		emf.close();

		List<TProveedor> listaTprov = new ArrayList<TProveedor>();
		for(Proveedor prov : listaProveedores)
		{
			listaTprov.add(new TProveedor(prov));
		}
		
		return listaTprov;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<TProveedor> obtenerProveedoresDisponibles() throws Exception{		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Proveedor e WHERE e.disponible = 1", Proveedor.class);
		
		List<Proveedor> listaProveedores = query.getResultList();
		
		em.close();
		emf.close();

		List<TProveedor> listaTprov = new ArrayList<TProveedor>();
		for(Proveedor prov : listaProveedores)
		{
			listaTprov.add(new TProveedor(prov));
		}
		
		return listaTprov;
		
	}
	
	public boolean altaProveedor(TProveedor tproveedor) throws Exception{		
			
		boolean resultado = false;
						
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		try {
			
			//Realiza todas las validaciones del transfer
			if(tproveedor == null)
			{
				throw new Exception("Proveedor nulo");
			}
			
			if(tproveedor.getNombre().length() <= 0)
			{
				throw new Exception("Debe ponerle un nombre al proveedor");			
			}
			
			if(!tproveedor.isDisponible())
			{
				throw new Exception("Se debe insertar el proveedor como 'disponible'");
			}
			
			validarNIF(tproveedor.getNIF());
			
			for(int i = 0; i < tproveedor.getTelefono().length();i++)
			{	
				if(tproveedor.getTelefono().charAt(i)<'0' ||  tproveedor.getTelefono().charAt(i)>'9')
				{
					throw new Exception("el teléfono sólo debe contener numeros");
				}

			}
			
			//Los nombres en proveedor se pueden repetir, por lo que no hay que validarlo
			
			//Como todo es correcto, lo inserta
			Proveedor proveedor = new Proveedor(tproveedor);
				
			em.persist(proveedor);
			
			em.getTransaction().commit();	
			
			resultado = true;
		
		} catch(OptimisticLockException oe) {
			em.getTransaction().rollback();	
			throw new Exception("No se pudo añadir el proveedor, porque está bloqueado");
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
	
	
	public boolean modificarProveedor(TProveedor tProveedor) throws Exception{
		
		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		try {
			//Realiza todas las validaciones del transfer
			if(tProveedor == null)
			{
				throw new Exception("Proveedor nulo");
			}
			
			if(tProveedor.getNombre().length() <= 0)
			{
				throw new Exception("Debe ponerle un nombre al proveedor");			
			}
			
			if(!tProveedor.isDisponible())
			{
				throw new Exception("Se debe modificar el proveedor como 'disponible'");
			}
			
			validarNIF(tProveedor.getNIF());
			
			for(int i = 0; i < tProveedor.getTelefono().length();i++)
			{	
				if(tProveedor.getTelefono().charAt(i)<'0' ||  tProveedor.getTelefono().charAt(i)>'9')
				{
					throw new Exception("el teléfono sólo debe contener numeros");
				}

			}
			
			//Como todo es correcto, lo inserta
			Proveedor proveedor = em.find(Proveedor.class, (tProveedor.getId_proveedor()));
			em.lock(proveedor, LockModeType.OPTIMISTIC);
			
				//valida que no lo han eliminado
			if (proveedor != null)
			{

				proveedor.setAll(tProveedor);					
				em.getTransaction().commit();		
				resultado = true;
			}									
			else
			{
				throw new Exception("No existe el proveedor con ese ID");
			}
				
		} catch(OptimisticLockException oe) {
			em.getTransaction().rollback();
			throw new Exception("No se pudo modificar el proveedor, porque está bloqueado");
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
	
	
	public boolean bajaProveedor(int ID)throws Exception{
						
		boolean respuesta = false;
		
		if (ID >= 0){				
		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			
			try {
				
				Proveedor proveedor = em.find(Proveedor.class, ID);
				em.lock(proveedor, LockModeType.OPTIMISTIC);
				
				//Valida que no ha sido eliminado antes de darlo de baja
				if (proveedor != null){
					if(!proveedor.isDisponible())
						throw new Exception("Este proveedor ya está marcado como NO disponible.");
					
					proveedor.setDisponible(false);
					respuesta = true;
					em.getTransaction().commit();		
				}
				else
				{
					throw new Exception("No existe el proveedor con ese ID.");
				}

			} catch(OptimisticLockException oe) {
				em.getTransaction().rollback();
				throw new Exception("No se pudo eliminar el proveedor, porque está bloqueado");
			}			
			catch (Exception e) {
				em.getTransaction().rollback();
				throw e;
			} finally {
				 
				em.close();
				emf.close();
				
			}	
			
		} else
			throw new Exception("No se pudo eliminar el proveedor, el ID debe ser positivo.");
			
		return respuesta;
		
	}
	
	boolean validarNIF(String nif) throws Exception
	{
		if(nif.length() <8 ||  nif.length() >9)
		{
			throw new Exception("El NIF no tiene la longitud necesaria");		
		}
		else
		{
			for(int i = 0; i < nif.length();i++)
			{
				if(i==nif.length()-1)
				{
					if(nif.toUpperCase().charAt(i)<'A' || nif.toUpperCase().charAt(i)>'Z')
					{
						throw new Exception("el NIF no contiene una letra al final");
					}
				}
				else
				{
					if(nif.charAt(i)<'0' ||  nif.charAt(i)>'9')
					{
						throw new Exception("el NIF debe contener numeros");
					}
				}
			}
		}
		
		return true;
	}
	
}

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

import negocio.producto.businessobject.Producto;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.businessobject.Proveedor;
import negocio.proveedor.transfer.TProveedor;
import negocio.proveedor.transfer.ValidarTProveedor;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;
import principal.Principal;

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
			
			if (ex instanceof Exception) {
	
				throw ex;

			} else {
			
				throw new Exception(ex.getLocalizedMessage());

			}
			
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
	
	
	public boolean altaProveedor(TProveedor tproveedor) throws Exception{		
			
		boolean resultado = false;
		
		Proveedor proveedor = new Proveedor(tproveedor);
							
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		try {
			
			em.getTransaction().begin();
				
			em.persist(proveedor);
			
			em.getTransaction().commit();	
			
			resultado = true;
		
		} catch(OptimisticLockException oe) {
			em.getTransaction().rollback();	
			throw new Exception("No se pudo añadir el proveedor, porque está bloqueado");
		}			
		catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception("No se pudo añadir el proveedor.");
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
		
		try {
			
			em.getTransaction().begin();
			
			Proveedor proveedor = em.find(Proveedor.class, (tProveedor.getId_proveedor()));
				
			if (proveedor != null){
				
				proveedor.setAll(tProveedor);					
				em.getTransaction().commit();		
				resultado = true;
			}									
			else
			{
				em.getTransaction().rollback();
			}
				
		} catch(OptimisticLockException oe) {
			em.getTransaction().rollback();
			throw new Exception("No se pudo modificar el proveedor, porque está bloqueado");
		}			
		catch (Exception e) {
			em.getTransaction().rollback();
			throw new Exception("No se pudo modificar el proveedor.");
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
			
			try {
				
				em.getTransaction().begin();
				
				Proveedor proveedor = em.find(Proveedor.class, ID);
					
				if (proveedor != null){
					proveedor.setDisponible(false);
					respuesta = true;
					em.getTransaction().commit();		
				}
				else
				{
					em.getTransaction().rollback();
				}
					
			} catch(OptimisticLockException oe) {
				em.getTransaction().rollback();
				throw new Exception("No se pudo eliminar el proveedor, porque está bloqueado");
			}			
			catch (Exception e) {
				em.getTransaction().rollback();
				throw new Exception("No se pudo eliminar el proveedor.");
			} finally {
				 
				em.close();
				emf.close();
				
			}	
			
		} else
			throw new Exception("No se pudo eliminar el proveedor, el ID debe ser entero positivo.");
			
		return respuesta;
		
	}
	
}

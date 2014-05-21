package negocio.proveedor.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import negocio.producto.Producto;
import negocio.proveedor.Proveedor;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import negocio.proveedor.ValidarTProveedor;
import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;
import principal.Principal;

public class SAProveedorImp implements SAProveedor {

	protected EntityManager em;
		
	public TProveedor obtenerProveedor(int ID) throws Exception{
				
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		TProveedor tProveedor;
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

			if (ex instanceof Exception) {

				em.getTransaction().rollback();
				
				throw ex;

			} else {

				em.getTransaction().rollback();
				
				throw new Exception(ex.getLocalizedMessage());

			}
			
		} finally {
	
			if (proveedorObtenido != null)
				em.detach(proveedorObtenido);
	
			em.close();
	
		}
		
		
		tProveedor = new TProveedor(proveedorObtenido);
		return tProveedor;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Proveedor> obtenerProveedores() throws Exception{		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Proveedor e", Proveedor.class);
		
		List<Proveedor> listaProveedores = query.getResultList();
		
		em.close();
		emf.close();

		return listaProveedores;
		
	}
	
	public RespuestaCMD altaProveedor(Object objeto) throws Exception{		
		
		Proveedor proveedor;
		
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error dando de alta un proveedor.");;
		
		if (new ValidarTProveedor().proveedorCorrecto((TProveedor) objeto)){
			
			proveedor = new Proveedor((TProveedor) objeto);
								
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
			EntityManager em = emf.createEntityManager();
			
			try {
				
				em.getTransaction().begin();
					
				em.persist(proveedor);
				
				em.getTransaction().commit();	
				
				respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO, "Exito al insertar proveedor.");
			
			} catch(OptimisticLockException oe) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
			}			
			catch (Exception e) {
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al insertar proveedor. Error al insertar los datos.");
				
			} finally {
				 
				em.close();
				emf.close();
				
			 }		
		
		}
		
		return respuestaComando;
		
	
	}		
	
	public RespuestaCMD modificarProveedor(Object objeto) throws Exception{
		
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar Proveedor.");
			
		if (new ValidarTProveedor().proveedorCorrecto((TProveedor) objeto)){		
		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
			EntityManager em = emf.createEntityManager();
			
			try {
				
				em.getTransaction().begin();
				
				Proveedor proveedor = em.find(Proveedor.class, ((TProveedor) objeto).getId_proveedor());
					
				if (proveedor != null){
					
					proveedor.setAll((TProveedor) objeto);					
					em.getTransaction().commit();		
					respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PROVEEDOR, "Se ha modificado el Proveedor.");
				}									
					
			} catch(OptimisticLockException oe) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
			}			
			catch (Exception e) {
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar proveedor. Error al insertar los datos.");
				
			} finally {
				 
				em.close();
				emf.close();
				
			 }		
			
		} else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar proveedor. Los datos no son válidos.");
		
		return respuestaComando;
		
	}
	
	public RespuestaCMD bajaProveedor(int ID)throws Exception{
						
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de baja un proveedor.");
		
		if (ID >= 0){				
		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
			EntityManager em = emf.createEntityManager();
			
			try {
				
				em.getTransaction().begin();
				
				Proveedor proveedor = em.find(Proveedor.class, ID);
					
				if (proveedor != null){
					proveedor.setDisponible(false);
					respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PROVEEDOR, "Exito dando de baja un Proveedor.");
					em.getTransaction().commit();		
				}
					
			} catch(OptimisticLockException oe) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
			}			
			catch (Exception e) {
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de baja un proveedor. Debe seleccionar un Producto.");
				
			} finally {
				 
				em.close();
				emf.close();
				
			}	
			
		} else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "El ID debe ser entero positivo.");
			
		return respuestaComando;
		
	}
	
}

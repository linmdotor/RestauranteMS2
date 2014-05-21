package negocio.producto.imp;

import java.util.List;

import javax.persistence.*;

import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;
import negocio.producto.Producto;
import negocio.producto.ProductoNoPerecedero;
import negocio.producto.ProductoPerecedero;
import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import negocio.producto.TProductoNoPerecedero;
import negocio.producto.TProductoPerecedero;
import negocio.producto.ValidarTProducto;


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
			
		tProducto = new TProducto(productoObtenido);
		return tProducto;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Producto> obtenerProductos() throws Exception {		
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
		EntityManager em = emf.createEntityManager();
		
		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Producto e", Producto.class);
		
		List<Producto> listaProductos = query.getResultList();
		
		em.close();
		emf.close();

		return listaProductos;
		
	}
	
	public RespuestaCMD altaProducto(Object objeto) throws Exception {		
		
		
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error dando de alta un producto.");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
		EntityManager em = emf.createEntityManager();
		
		TProducto productoNuevo = new TProducto();
		productoNuevo = (TProducto)objeto ;

		if (new ValidarTProducto().productoCorrecto(productoNuevo))
		{
			
			try {
				em.getTransaction().begin();
				
				if(productoNuevo instanceof TProductoPerecedero){
					
					ProductoPerecedero producto = new ProductoPerecedero((TProductoPerecedero) productoNuevo);
					//producto.setAll(productoNuevo);
					em.persist(producto);
					
				}
				else{//if(boProducto instanceof BOProductoNoPerecedero)
					
					ProductoNoPerecedero producto = new ProductoNoPerecedero((TProductoNoPerecedero) productoNuevo);
					//producto.setAll(productoNuevo);
					em.persist(producto);
				}

				em.getTransaction().commit();
				respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Exito al almacenar nuevo Producto.");
			
			} catch(OptimisticLockException oe) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
			} catch (Exception e) {
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al insertar nuevo producto.");
				
			} finally {
				 
				em.close();
				emf.close();
				
			 }		
		
		}
		
		return respuestaComando;
		
	
	}		
	
	public RespuestaCMD modificarProducto(Object objeto) throws Exception {
					
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar producto.");
		TProducto tproducto = new TProducto();
		tproducto = (TProducto)objeto ;
		
		if (new ValidarTProducto().productoCorrecto(tproducto)){		
		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
			EntityManager em = emf.createEntityManager();
			
			
			try {
				
				em.getTransaction().begin();
				// Este producto encontrado, me sirve para saber que no esta vacio, que existe
				//y que tipo de producto es
				Producto productoNuevo = em.find(Producto.class, tproducto.getId_producto());
					
				if (productoNuevo != null){
					
					if(productoNuevo.getTipoProducto().equals("Perecedero")){
						
						//ProductoPerecedero producto = new ProductoPerecedero((TProductoPerecedero) tproducto);
						ProductoPerecedero producto = em.find(Producto.class, tproducto.getId_producto());	
						producto.setAll(tproducto);
						
					}else{
						
						//ProductoNoPerecedero producto = new ProductoNoPerecedero((TProductoNoPerecedero) tproducto);
						ProductoNoPerecedero producto = em.find(Producto.class, tproducto.getId_producto());	
						producto.setAll(tproducto);
					}
						
					
					em.getTransaction().commit();		
					respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Se ha modificado el Producto.");
				}									
					
			} catch(OptimisticLockException oe) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
			}			
			catch (Exception e) {
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar producto. Error al insertar los datos.");
				
			} finally {
				 
				em.close();
				emf.close();
				
			 }		
			
		} else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al modificar producto. Los datos no son válidos.");
		
		return respuestaComando;
		
	}
	
	public RespuestaCMD bajaProducto(int ID)  throws Exception {
						
		RespuestaCMD respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error dando de baja un producto.");
		
		if (ID >= 0){				
		
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("ServicioProducto");		
			EntityManager em = emf.createEntityManager();
			
			try {
				
				em.getTransaction().begin();
				
				Producto producto = em.find(Producto.class, ID);
					
				if (producto != null){
					producto.setDisponible(false);
					respuestaComando = new RespuestaCMD(EnumComandos.CORRECTO_PRODUCTO, "Exito dando de baja un Producto.");
					em.getTransaction().commit();		
				}
					
			} catch(OptimisticLockException oe) {
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al acceder los datos de forma concurrente.");
			}			
			catch (Exception e) {
				
				respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "Error al dar de baja un producto. Debe seleccionar un Producto.");
				
			} finally {
				 
				em.close();
				emf.close();
				
			}	
			
		} else
			respuestaComando = new RespuestaCMD(EnumComandos.ERROR, "El ID debe ser entero positivo.");
			
		return respuestaComando;
		
	}

}

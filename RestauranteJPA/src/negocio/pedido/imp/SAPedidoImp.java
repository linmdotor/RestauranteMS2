package negocio.pedido.imp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;

import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;
import negocio.pedido.SAPedido;
import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedido;
import negocio.producto.businessobject.Producto;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.proveedor.businessobject.Proveedor;
import negocio.proveedor.imp.SAProveedorImp;
import negocio.proveedor.transfer.TProveedor;
import negocio.pedido.businessobject.Pedido;

public class SAPedidoImp implements SAPedido {
	protected EntityManager em;

	///
	public TPedido obtenerPedido(int ID) throws Exception {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		TPedido tPedido;
		Pedido pedidoobtenido = null;
		TypedQuery<Pedido> query = null;

		try {
			em.getTransaction().begin();

			query = em.createNamedQuery(Pedido.QUERY_OBTENER_PEDIDO,
					Pedido.class);

			query.setParameter("arg", ID);

			pedidoobtenido = query.getSingleResult();

			em.lock(pedidoobtenido, LockModeType.OPTIMISTIC);

			em.getTransaction().commit();

		} catch (NoResultException ex) {

			em.getTransaction().rollback();

			throw new Exception("No existe el pedido con ID: " + ID);

		} catch (Exception ex) {
			
			em.getTransaction().rollback();
			throw ex;

		} finally {

			em.close();
			emf.close();

		}

		tPedido = new TPedido(pedidoobtenido);

		return tPedido;

	}

	public List<TProductoDePedido> obtenerPedidoProductos(int IDPedido) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM ProductoDePedido e",
				Pedido.class);

		@SuppressWarnings("unchecked")
		List<TProductoDePedido> listaPedido = query.getResultList();

		em.close();
		emf.close();

		return listaPedido;
	}

	@SuppressWarnings("unchecked")
	public List<TPedido> obtenerPedidos() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Pedido e",
				Pedido.class);

		List<TPedido> listaPedido = query.getResultList();

		em.close();
		emf.close();

		return listaPedido;
	}

	public boolean altaPedido(int IDprov) throws Exception {

		boolean respuestaComando = false;;
		Calendar fecha = new GregorianCalendar();

		if (IDprov >= 0) {

			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
			EntityManager em = emf.createEntityManager();

			try {

				em.getTransaction().begin();

				// sacamos el pedido con id mayor
				List<TPedido> lista_pedidos = obtenerPedidos();

				int ultimo_id_usado = -1;
				if (lista_pedidos != null)
					for (int i = 0; i < lista_pedidos.size(); i++) {
						if (lista_pedidos.get(i).getId_pedido() >= ultimo_id_usado)
							ultimo_id_usado = lista_pedidos.get(i)
									.getId_pedido();
					}

				// creamos un pedido con el último id usado + 1
				Pedido ped = new Pedido();
				TProveedor tprov = new SAProveedorImp()
						.obtenerProveedor(IDprov);
				ped.setProveedor(new Proveedor(tprov));
				
				ped.setId_pedido(ultimo_id_usado);

				em.persist(ped);

				em.getTransaction().commit();
				respuestaComando = true;

			} catch (OptimisticLockException oe) {
				throw new Exception("Error al acceder los datos de forma concurrente.");
			} catch (Exception e) {
				throw new Exception("Error al crear pedido.");

			} finally {

				em.close();
				emf.close();

			}

		}

		return respuestaComando;

	}

	@SuppressWarnings("unused")
	public boolean cerrarPedido(List<TProductoDePedido> lista) throws Exception {
		boolean respuestaComando = false;
		Calendar fecha = new GregorianCalendar();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		// lo almacenamos Todo (el pedido y productos)
		em.getTransaction().begin();
		try {

			for (int i = 0; i < lista.size(); i++)
				em.persist(lista.get(i));

			em.getTransaction().commit();
			
			respuestaComando = true;

		} catch (OptimisticLockException oe) {
			throw new Exception( "Error al acceder los datos de forma concurrente.");
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				try {
					em.getTransaction().rollback();
					throw new Exception("Error en la trasacción..");
				} catch (RuntimeException re) {
					throw new Exception("Error al hacer rollback en la transacción.");
				}
			}
		} finally {

			em.close();
			emf.close();
		}

		return respuestaComando;
	}


	public boolean cancelarPedido(int ID) throws Exception {

		boolean respuestaComando = false;
		Calendar fecha = new GregorianCalendar();
		
		if (ID >= 0){

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
			EntityManager em = emf.createEntityManager();
	
			try {
				em.getTransaction().begin();
	
				Pedido pedido = em.find(Pedido.class, ID);
	
				if (pedido != null) {
					
					
					em.getTransaction().commit();
					respuestaComando = true;
	
				}else
				{
					em.getTransaction().rollback();
				}
					
				
			} catch (OptimisticLockException oe) {
				em.getTransaction().rollback();
				throw new Exception(
						"Error al acceder los datos de forma concurrente.");
			} catch (Exception e) {
				em.getTransaction().rollback();
				throw new Exception(
						"Error al cancelar un pedido. Debe seleccionar un Pedido.");
			} finally {
				em.close();
				emf.close();
			}
		} else
			throw new Exception("No se pudo eliminar el pedido, el ID debe ser entero positivo.");

		return respuestaComando;
	}

	
	
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
	
	
	public boolean almacenarPedido(TPedido tpedido) throws Exception {

		TypedQuery<Proveedor> queryProveedor = null;
		Proveedor proveedorObtenido = null;
		
		boolean respuesta = true;

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		try {
			
			//obtenemos proveedor
			
			em.getTransaction().begin();
			
			queryProveedor = em.createNamedQuery(Proveedor.QUERY_OBTENER_PROVEEDOR, Proveedor.class);
			
			queryProveedor.setParameter("arg", tpedido.getId_proveedor());
			
			proveedorObtenido = queryProveedor.getSingleResult();
			
			em.lock(proveedorObtenido, LockModeType.OPTIMISTIC);
			
			em.getTransaction().commit();			
			
			//añadimos pedido
			
			em.getTransaction().begin();

			// No deberia encontrar un pedido con el mismo ID
			Pedido pedido = em.find(Pedido.class, tpedido.getId_pedido());
			
			
			// Si lo encuentra no deberia almacenarlo
			if (pedido == null) {
							
				//Pedido nuevoPedido = new Pedido(proveedorObtenido,tpedido.getPrecio());
				Pedido nuevoPedido = new Pedido();
				em.persist(nuevoPedido);

				em.getTransaction().commit();

				respuesta = true;

			} else
				respuesta = false;
			
			
			
			//añadimos los productos de pedido		
			
			/*for ( ProductoDePedido productoPedido: tpedido.getListaProductosPedido()){
				
				//obtenemos producto
				
				em.getTransaction().begin();
				
				TypedQuery<Producto> queryProducto = em.createNamedQuery(Producto.QUERY_OBTENER_PRODUCTO, Producto.class);
				
				queryProducto.setParameter("arg", productoPedido.getProducto().getId_producto());
				
				Producto productoObtenido = queryProducto.getSingleResult();
				
				em.lock(productoObtenido, LockModeType.OPTIMISTIC);
				
				em.getTransaction().commit();	
				
				//obtenemos pedido
				
				
				@SuppressWarnings("rawtypes")
				TypedQuery query = em.createQuery("SELECT e FROM Pedido e",
						Pedido.class);

				@SuppressWarnings("unchecked")
				List<TProductoDePedido> listaPedido = query.getResultList();				
				
				
				em.getTransaction().begin();
				
				TypedQuery<Pedido> queryPedido = em.createNamedQuery(Pedido.QUERY_OBTENER_PEDIDO, Pedido.class);
				System.out.println(listaPedido.size());
				queryPedido.setParameter("arg", listaPedido.size());
				
				Pedido pedidoObtenido = queryPedido.getSingleResult();
				
				em.lock(pedidoObtenido, LockModeType.OPTIMISTIC);
				
				em.getTransaction().commit();
				
				// añadimos producto
				
				em.getTransaction().begin();
				
				em.persist(new ProductoDePedido(productoObtenido, pedidoObtenido, productoPedido.getCantidad()));
				
				em.getTransaction().commit();								
				
			}*/

		} catch (OptimisticLockException oe) {
			throw new Exception(
					"No se almacenar el pedido, porque está bloqueado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new Exception("No se pudo almacenar el pedido.");
		} finally {
			em.close();
			emf.close();
		}

		return respuesta;
	}

}
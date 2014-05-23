package negocio.pedido.imp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;

import presentacion.controlador.EnumComandos;
import presentacion.controlador.RespuestaCMD;
import negocio.pedido.Pedido;
import negocio.pedido.PedidoProducto;
import negocio.pedido.SAPedido;
import negocio.pedido.TPedido;
import negocio.pedido.TPedidoProducto;
import negocio.proveedor.Proveedor;
import negocio.proveedor.TProveedor;
import negocio.proveedor.imp.SAProveedorImp;

public class SAPedidoImp implements SAPedido {
	protected EntityManager em;

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

			if (ex instanceof Exception) {

				em.getTransaction().rollback();

				throw ex;

			} else {

				em.getTransaction().rollback();

				throw new Exception(ex.getLocalizedMessage());

			}

		} finally {

			if (pedidoobtenido != null)
				em.detach(pedidoobtenido);

			em.close();

		}

		tPedido = new TPedido(pedidoobtenido);

		return tPedido;

	}

	public List<TPedidoProducto> obtenerPedidoProductos(int IDPedido) {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM PedidoProducto e",
				Pedido.class);

		@SuppressWarnings("unchecked")
		List<TPedidoProducto> listaPedido = query.getResultList();

		em.close();
		emf.close();

		return listaPedido;
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> obtenerPedidos() {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		@SuppressWarnings("rawtypes")
		TypedQuery query = em.createQuery("SELECT e FROM Pedido e",
				Pedido.class);

		List<Pedido> listaPedido = query.getResultList();

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
				List<Pedido> lista_pedidos = obtenerPedidos();

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
				ped.setFechaRealizado(fecha.toString());
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
	public boolean cerrarPedido(List<TPedidoProducto> lista) throws Exception {
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
		}

		return respuestaComando;
	}


	public boolean cancelarPedido(int ID) throws Exception {

		boolean respuestaComando = false;
		Calendar fecha = new GregorianCalendar();

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();

			Pedido pedido = em.find(Pedido.class, ID);

			if (pedido != null) {
				
				pedido.setFechaCancelado(fecha.getTime().toString());
				em.getTransaction().commit();
				respuestaComando = true;

			}
			
		} catch (OptimisticLockException oe) {
			throw new Exception(
					"Error al acceder los datos de forma concurrente.");
		} catch (Exception e) {
			throw new Exception(
					"Error al cancelar un pedido. Debe seleccionar un Pedido.");
		} finally {
			em.close();
			emf.close();
		}

		return respuestaComando;
	}

	public boolean almacenarPedido(TPedido tpedido) throws Exception {

		boolean respuesta = true;

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();

			// No deberia encontrar un pedido con el mismo ID
			Pedido pedido = em.find(Pedido.class, tpedido.getId_pedido());

			// Si lo encuentra no deberia almacenarlo
			if (pedido == null) {
				Pedido nuevoPedido = new Pedido(tpedido);

				em.persist(nuevoPedido);

				em.getTransaction().commit();

				respuesta = true;

			} else
				respuesta = false;

		} catch (OptimisticLockException oe) {
			throw new Exception(
					"No se almacenar el pedido, porque está bloqueado");
		} catch (Exception e) {
			throw new Exception("No se pudo almacenar el pedido.");
		} finally {
			em.close();
			emf.close();
		}

		return respuesta;
	}

}
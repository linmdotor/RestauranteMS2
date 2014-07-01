package negocio.pedido.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.*;

import negocio.pedido.SAPedido;
import negocio.pedido.businessobject.Pedido;
import negocio.pedido.transfer.TPedido;
import negocio.producto.businessobject.Producto;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.productosdepedido.transfer.TProductoDePedido;
import negocio.productosdeproveedor.businessobject.ProductoDeProveedor;
import negocio.proveedor.businessobject.Proveedor;

public class SAPedidoImp implements SAPedido {
	
	@Override
	public TPedido obtenerPedido(int ID) throws Exception {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		Pedido pedidoobtenido = null;
		TypedQuery<Pedido> query = null;

		try {
			em.getTransaction().begin();

			query = em.createNamedQuery(Pedido.QUERY_OBTENER_PEDIDO, Pedido.class);

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

		return new TPedido(pedidoobtenido);
	}

	@Override
	public List<TPedido> obtenerPedidos() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		TypedQuery query = em.createQuery("SELECT e FROM Pedido e",	Pedido.class);

		List<Pedido> listaPedido = query.getResultList();

		em.close();
		emf.close();
		
		List<TPedido> listatPedido = new ArrayList<TPedido>();		
		for(Pedido p : listaPedido)
		{
			listatPedido.add(new TPedido(p));
		}

		return listatPedido;
	}
	
	@Override
	public boolean altaPedido(TPedido tPedido) throws Exception {
		
		TypedQuery<Proveedor> queryProveedor = null;
		Proveedor proveedorObtenido = null;
		
		boolean respuesta = true;

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		
		try {
			//Realiza todas las validaciones del transfer
			if(tPedido == null)
			{
				throw new Exception("Pedido nulo");
			}
			
			//no validamos el IDPedido pues vamos a crearlo
			
			if(tPedido.getId_proveedor() < 0)
			{
				throw new Exception("El ID Proveedor debe ser >= 0");			
			}
			
			//obtenemos proveedor			
			queryProveedor = em.createNamedQuery(Proveedor.QUERY_OBTENER_PROVEEDOR, Proveedor.class);
			
			queryProveedor.setParameter("arg", tPedido.getId_proveedor());
			
			proveedorObtenido = queryProveedor.getSingleResult();			
			em.lock(proveedorObtenido, LockModeType.OPTIMISTIC);
			
			//Verifica que sigue existinedo el proveedor y está disponible
			if(proveedorObtenido == null || !proveedorObtenido.isDisponible())
			{
				throw new Exception("El proveedor con ID: " + tPedido.getId_proveedor() + " ya no existe o no está disponible");
			}
			
			// sacamos el pedido con id mayor
			List<TPedido> lista_pedidos = obtenerPedidos();

			int ultimo_id_usado = -1;
			if (lista_pedidos != null)
			{
				for (int i = 0; i < lista_pedidos.size(); i++)
				{
					if (lista_pedidos.get(i).getId_pedido() >= ultimo_id_usado)
					{
						ultimo_id_usado = lista_pedidos.get(i).getId_pedido();
					}
				}
			}
			
			//verifica que no existe un Pedido con el ID que vamos a usar
			if(em.find(Producto.class, (tPedido.getId_pedido())) != null)
			{
				throw new Exception("El pedido con el ID que se ha autogenerado: " + tPedido.getId_proveedor() + " ya existe");
			}

			// creamos un pedido con el último id usado + 1
			Pedido pedido = new Pedido();

			pedido.setId_pedido(ultimo_id_usado+1);
			pedido.setProveedor(proveedorObtenido);	
			pedido.setFechaRealizado(getFecha());
			pedido.setFechaEntregado(tPedido.getFechaEntregado());
			pedido.setFechaCancelado(tPedido.getFechaCancelado());			

			em.persist(pedido);
			
			em.lock(pedido, LockModeType.OPTIMISTIC);
			
			//añadimos los productos de pedido				
			for ( TProductoDePedido tproductoPedido: tPedido.getListaProductosPedido()){
				
				//obtenemos cada producto				
				TypedQuery<Producto> queryProducto = em.createNamedQuery(Producto.QUERY_OBTENER_PRODUCTO, Producto.class);
				
				queryProducto.setParameter("arg", tproductoPedido.getProducto());
				
				Producto productoObtenido = queryProducto.getSingleResult();				
				em.lock(productoObtenido, LockModeType.OPTIMISTIC);
				
				//validamos que el producto sigue existiendo, estando disponible
				if(productoObtenido == null || !productoObtenido.isDisponible())
				{
					throw new Exception("El producto con ID: " + tproductoPedido.getProducto() + " ya no existe o no está disponible");
				}
				
				//validamos que el producto nos lo ofrece nuestro proveedor
				boolean encontrado = false;
				for(ProductoDeProveedor listaproveedores : productoObtenido.getListaProductosProveedor())
				{
					if(listaproveedores.getProveedor().getId_proveedor() == tPedido.getId_proveedor())
						encontrado = true;
				}
				if(!encontrado)
				{
					throw new Exception("El producto con ID: " + tproductoPedido.getProducto() + " ya no es ofrecido por el proveedor " + tPedido.getId_proveedor());
				}
				
				//persistimos el producto-pedido
				em.persist(new ProductoDePedido(productoObtenido, pedido, tproductoPedido.getCantidad(), tproductoPedido.getPrecio()));
				
			}
			
			em.getTransaction().commit();

		} catch (OptimisticLockException oe) {
			em.getTransaction().rollback();
			throw new Exception("No se almacenar el pedido, porque algún recurso está bloqueado");
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
			emf.close();
		}

		return respuesta;
	}

	@Override
	public boolean almacenarPedido(TPedido tPedido) throws Exception {
		
		boolean resultado = false;
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		Pedido pedidoobtenido = null;
		TypedQuery<Pedido> query = null;

		em.getTransaction().begin();
		
		try {
			//Realiza todas las validaciones del transfer
			if(tPedido == null)
			{
				throw new Exception("Pedido nulo");
			}
			
			if(tPedido.getId_pedido() < 0)
			{
				throw new Exception("El ID Pedido debe ser >= 0");			
			}
			
			//Como en el almacenaje no se va a hacer nada con el proveedor y además
				//sólo se tiene en cuenta el estado de los productos CUANDO SE HIZO EL PEDIDO, no estado actual
				//no es necesario validar nada más
			
			//Como todo es correcto, lo modifica
			query = em.createNamedQuery(Pedido.QUERY_OBTENER_PEDIDO, Pedido.class);

			query.setParameter("arg", tPedido.getId_pedido());

			pedidoobtenido = query.getSingleResult();
			em.lock(pedidoobtenido, LockModeType.OPTIMISTIC);

			//Verifica que sigue existinedo el pedido
			if(pedidoobtenido == null)
			{
				throw new Exception("El pedido con ID: " + tPedido.getId_pedido() + " ya no existe");
			}
			
			pedidoobtenido.setFechaEntregado(getFecha());
			
			em.persist(pedidoobtenido);
			
			for(ProductoDePedido prod_ped: pedidoobtenido.getListaProductosPedido())
			{
				Producto p = prod_ped.getProducto();
				em.lock(p, LockModeType.OPTIMISTIC);
				
				//Verifica que cada producto sigue existiendo
				if (p == null)
				{
					throw new Exception("El producto del pedido con ID " + p.getId_producto() + " ya no existe, no se puede almacenar el pedido");
				}
				
				p.setStock(p.getStock() + prod_ped.getCantidad());
				em.persist(p);
			}
			
			em.getTransaction().commit();
			resultado = true;

		} catch (NoResultException ex) {

			em.getTransaction().rollback();
			throw new Exception("No existe el pedido con ID: " + tPedido.getId_pedido());

		} catch (Exception ex) {
			
			em.getTransaction().rollback();
			throw ex;

		} finally {

			em.close();
			emf.close();

		}

		return resultado;
	}

	@Override
	public boolean cancelarPedido(TPedido tPedido) throws Exception {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");
		EntityManager em = emf.createEntityManager();

		Pedido pedidoobtenido = null;
		TypedQuery<Pedido> query = null;

		em.getTransaction().begin();
		
		try {
			//Realiza todas las validaciones del transfer
			if(tPedido == null)
			{
				throw new Exception("Pedido nulo");
			}
			
			if(tPedido.getId_pedido() < 0)
			{
				throw new Exception("El ID Pedido debe ser >= 0");			
			}
			
			//Como en la cancelación no se va a hacer nada con los productos ni proveedor
				//no es necesario validar nada más
			
			//Como todo es correcto, lo modifica
			query = em.createNamedQuery(Pedido.QUERY_OBTENER_PEDIDO, Pedido.class);

			query.setParameter("arg", tPedido.getId_pedido());

			pedidoobtenido = query.getSingleResult();
			em.lock(pedidoobtenido, LockModeType.OPTIMISTIC);

			//Verifica que sigue existinedo el pedido
			if(pedidoobtenido == null)
			{
				throw new Exception("El pedido con ID: " + tPedido.getId_pedido() + " ya no existe");
			}
					
			pedidoobtenido.setFechaCancelado(getFecha());
			
			em.persist(pedidoobtenido);
			
			em.getTransaction().commit();

		} catch (NoResultException ex) {

			em.getTransaction().rollback();
			throw new Exception("No existe el pedido con ID: " + tPedido.getId_pedido());

		} catch (Exception ex) {
			
			em.getTransaction().rollback();
			throw ex;

		} finally {

			em.close();
			emf.close();

		}

		return true;
	}
	
	private String getFecha()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY  HH:mm");
		return(sdf.format( new GregorianCalendar().getTime()));
	}
}
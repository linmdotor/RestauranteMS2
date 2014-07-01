package negocio.productosdepedido.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import negocio.pedido.businessobject.Pedido;
import negocio.productosdepedido.SAProductoDePedido;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.productosdepedido.transfer.TProductoDePedido;

public class SAProductoDePedidoImp implements SAProductoDePedido {

	@Override
	public List<TProductoDePedido> obtenerProductosPedido(int ID) throws Exception
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("UNIDAD_PERSISTENCIA_RESTAURANTE");		
		EntityManager em = emf.createEntityManager();
		
		Pedido pedidoObtenido = em.find(Pedido.class, ID);
		TypedQuery<Pedido> query = null;
		
		try{
			
			em.getTransaction().begin();
			
			query = em.createNamedQuery(Pedido.QUERY_OBTENER_PEDIDO, Pedido.class);
			
			query.setParameter("arg", ID);
			
			pedidoObtenido = query.getSingleResult();			
			em.lock(pedidoObtenido, LockModeType.OPTIMISTIC);
			
			em.getTransaction().commit();
							
		} catch(NoResultException ex){
			
			em.getTransaction().rollback();			
			throw new Exception("No existe el pedido con ID: " + ID);
			
		} catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
			
		} finally {

			em.close();
			emf.close();
	
		}		
		
		List<ProductoDePedido> listaproductos = pedidoObtenido.getListaProductosPedido();
		
		List<TProductoDePedido> listatProd = new ArrayList<TProductoDePedido>();
		
		for(ProductoDePedido p : listaproductos)
		{
			listatProd.add(new TProductoDePedido(p));
		}
		
		return listatProd;
		
	}

}

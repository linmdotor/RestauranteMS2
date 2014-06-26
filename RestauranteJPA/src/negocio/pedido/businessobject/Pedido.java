package negocio.pedido.businessobject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import negocio.pedido.transfer.TPedido;
import negocio.productosdepedido.businessobject.PedidoProducto;
import negocio.proveedor.businessobject.Proveedor;



@Entity
@Table(name = "pedidos", uniqueConstraints = { @UniqueConstraint(columnNames = "id_pedido") })
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name = "obtenerPedido", query = "select e from Pedido e where e.id_pedido = :arg"),
	@NamedQuery(name = "obtenerTodosPedidos", query = "select obj from Pedido obj order by obj.id_pedido"),
})
public class Pedido{

	// Atributos
	
	public static final String QUERY_OBTENER_PEDIDO = "obtenerPedido";
	public static final String QUERY_OBTENER_TODOS_PEDIDOS = "obtenerTodosPedidos";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id_pedido;
	protected String fecha_realizado;
	protected String fecha_entregado;
	protected String fecha_cancelado;

	@Version
	private int version;
		
	// Relaciones JPA	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Proveedor proveedor;
	
    @OneToMany(mappedBy="pedido", cascade = CascadeType.PERSIST)
    private List<PedidoProducto> listaProductosPedido;

	public Pedido() {
		super();
	}
	
	public Pedido(Object objeto){
		TPedido tpedido= (TPedido) objeto;
		
		this.id_pedido=tpedido.getId_pedido();
		this.fecha_realizado=tpedido.getFechaRealizado();
		this.fecha_entregado=tpedido.getFechaEntregado();
		this.fecha_cancelado=tpedido.getFechaCancelado();
		
	}
	
	public Pedido(int ID_Pedido, String fecha_realizado, String fecha_entregado, String fecha_cancelado){
		
		this.id_pedido=ID_Pedido;
		this.fecha_realizado=fecha_realizado;
		this.fecha_entregado=fecha_entregado;
		this.fecha_cancelado=fecha_cancelado;
		
	}
	
	public String getFecha_realizado() {
		return fecha_realizado;
	}

	public void setFecha_realizado(String fecha_realizado) {
		this.fecha_realizado = fecha_realizado;
	}

	public String getFecha_entregado() {
		return fecha_entregado;
	}

	public void setFecha_entregado(String fecha_entregado) {
		this.fecha_entregado = fecha_entregado;
	}

	public String getFecha_cancelado() {
		return fecha_cancelado;
	}

	public void setFecha_cancelado(String fecha_cancelado) {
		this.fecha_cancelado = fecha_cancelado;
	}

	public List<PedidoProducto> getListaProductosPedido() {
		return listaProductosPedido;
	}

	public void setListaProductosPedido(List<PedidoProducto> listaProductosPedido) {
		this.listaProductosPedido = listaProductosPedido;
	}

	public int getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(int id_pedido) {
		this.id_pedido = id_pedido;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getFechaRealizado(){
		return this.fecha_realizado;
	}
	
	public void setFechaRealizado(String fecha_realizado){
		this.fecha_realizado=fecha_realizado;
	}
	
	public String getFechaEntregado(){
		return this.fecha_entregado;
	}
	
	public void setFechaEntregado(String fecha_entregado){
		this.fecha_entregado=fecha_entregado;
	}
	
	public String getFechaCancelado(){
		return this.fecha_cancelado;
	}
	
	public void setFechaCancelado(String fecha_cancelado){
		this.fecha_cancelado=fecha_cancelado;
	}
}

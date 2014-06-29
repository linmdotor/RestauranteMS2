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
import negocio.productosdepedido.businessobject.ProductoDePedido;
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

	@Version
	private int version;
	
	private double precio;
		
	// Relaciones JPA	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Proveedor proveedor;
	
    @OneToMany(mappedBy="pedido", cascade = CascadeType.PERSIST)
    private List<ProductoDePedido> listaProductosPedido;

	public Pedido() {
		super();
	}
	
	public Pedido(Proveedor proveedor, double precio){
		
		this.proveedor = proveedor;
		this.setPrecio(precio);
		
	}
	
	public Pedido(int ID_Pedido, String fecha_realizado, String fecha_entregado, String fecha_cancelado){
		
		this.id_pedido=ID_Pedido;
		
	}

	public Pedido(Proveedor proveedorObtenido, List<ProductoDePedido> listaProductosPedido) {
		this.proveedor = proveedorObtenido;
		this.listaProductosPedido = listaProductosPedido;
	}

	public List<ProductoDePedido> getListaProductosPedido() {
		return listaProductosPedido;
	}

	public void setListaProductosPedido(List<ProductoDePedido> listaProductosPedido) {
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

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
}

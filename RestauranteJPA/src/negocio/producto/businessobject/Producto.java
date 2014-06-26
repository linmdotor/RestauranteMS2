package negocio.producto.businessobject;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import negocio.producto.transfer.TProducto;
import negocio.productosdepedido.businessobject.ProductoDePedido;
import negocio.productosdeproveedor.*;
import negocio.productosdeproveedor.businessobject.ProductoDeProveedor;
import negocio.proveedor.businessobject.Proveedor;


/**
 * Entity implementation class for Entity: Pedido
 *
 */
@Entity
@Table(name = "productos", uniqueConstraints = { @UniqueConstraint(columnNames = "id_producto") })
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name = "negocio.producto.objetoNegocio.Producto.findProductoById_producto", query = "select e from Producto e where e.id_producto = :arg"),
	@NamedQuery(name = "negocio.producto.objetoNegocio.Producto.buscarTodosProductos", query = "select obj from Producto obj order by obj.id_producto"),
})

public class Producto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Atributos
	public static final String QUERY_OBTENER_PRODUCTO = "negocio.producto.objetoNegocio.Producto.findProductoById_producto";
	public static final String QUERY_OBTENER_TODOS_PRODUCTOS = "negocio.producto.objetoNegocio.Producto.buscarTodosProductos";
	
	/*@Version
	 private long version;*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id_producto;
	protected boolean disponible;
	protected int stock;
	protected String nombre;
	@Version
    private int version;
	
	public enum TipoProducto {
    	PERECEDERO, NO_PERECEDERO /* En la base de datos, seran 0 y 1 respectivamente */
    };
    private TipoProducto tipoProducto;
    
	// Relaciones JPA
    @OneToMany(mappedBy="producto")
    private List<ProductoDeProveedor> listaProductosProveedor;
    
    @OneToMany(mappedBy="producto")
    private List<ProductoDePedido> listaProductosPedido;

	
	// Constructores
	
	public Producto() {
		super();
	}
   	
	public Producto(int ID_Plato, boolean disponible, int stock, String nombre, TipoProducto tipoProducto) {

		this.id_producto = ID_Plato;
		this.disponible = disponible;
		this.stock = stock;
		this.nombre = nombre;
		this.tipoProducto = tipoProducto;
	}
	
	public Producto(Object objeto) {
		
		TProducto tProducto = (TProducto) objeto;
		
		this.id_producto = tProducto.getId_producto();
		this.disponible = tProducto.isDisponible();
		this.stock = tProducto.getStock();
		this.nombre = tProducto.getNombre();
		
	}
	
	// Mutadores y Accedentes
	
	public List<ProductoDeProveedor> getListaProductosProveedor() {
		return listaProductosProveedor;
	}

	public List<ProductoDePedido> getListaProductosPedido() {
		return listaProductosPedido;
	}
	
	public void setListaProductosPedido(List<ProductoDePedido> listaProductosPedido) {
		this.listaProductosPedido = listaProductosPedido;
	}

	public void setListaProductosProveedor(
			List<ProductoDeProveedor> listaProductosProveedor) {
		this.listaProductosProveedor = listaProductosProveedor;
	}
	
	public int getId_producto() {
		return id_producto;
	}

	public void setId_producto(int id_producto) {
		this.id_producto = id_producto;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
		
}

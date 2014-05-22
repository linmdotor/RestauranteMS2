package negocio.proveedor;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import negocio.pedido.Pedido;
import negocio.producto.Producto;
import negocio.productosdeproveedor.ProductosDeProveedor;

/**
 * Entity implementation class for Entity: Pedido
 *
 */
@Entity
@Table(name = "proveedores", uniqueConstraints = { @UniqueConstraint(columnNames = "id_proveedor") })
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
	@NamedQuery(name = "negocio.proveedor.objetoNegocio.Proveedor.findProveedor", query = "select e from Proveedor e where e.id_proveedor = :arg"),
	@NamedQuery(name = "negocio.proveedor.objetoNegocio.Proveedor.buscarTodosProveedores", query = "select obj from Proveedor obj order by obj.id_proveedor"),
})

public class Proveedor implements Serializable {
	
	// Atributos
	
	/*@Version
	 private long version;*/

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String QUERY_OBTENER_PROVEEDOR = "negocio.proveedor.objetoNegocio.Proveedor.findProveedor";
	public static final String QUERY_OBTENER_TODOS_PROVEEDORES = "negocio.proveedor.objetoNegocio.Proveedor.buscarTodosProveedores";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id_proveedor;
	protected String nombre;
	protected String NIF;
	protected String telefono;
	protected boolean disponible;

	@Version
    private int version;
	// Relaciones JPA
	
    //@ManyToMany(mappedBy = "listaProveedores")
    //private List<Producto> listaProductos;

	@OneToMany(mappedBy="proveedor", cascade = CascadeType.PERSIST)
    private List<ProductosDeProveedor> listaProductosProveedor;
	
	@OneToMany(mappedBy="proveedor", cascade = CascadeType.PERSIST)
	private List<Pedido> listaPedidos;
	
	// Constructores
	
	public Proveedor() {
		super();
	}
   	
	public Proveedor(int ID_Proveedor, boolean disponible, String NIF, String nombre, String telefono) {

		this.id_proveedor = ID_Proveedor;
		this.disponible = disponible;
		this.NIF = NIF;
		this.nombre = nombre;
		this.telefono = telefono;
		
	}
	
	public Proveedor(Object objeto) {
		
		TProveedor tProveedor = (TProveedor) objeto;
		
		this.id_proveedor = tProveedor.getId_proveedor();
		this.disponible = tProveedor.isDisponible();
		this.NIF = tProveedor.getNIF();
		this.nombre = tProveedor.getNombre();
		this.telefono = tProveedor.getTelefono();
		
	}
	
	// Mutadores y Accedentes	
	public List<ProductosDeProveedor> getListaProductosProveedor() {
		return listaProductosProveedor;
	}

	public void setListaProductosProveedor(List<ProductosDeProveedor> listaProductosProveedor) {
		this.listaProductosProveedor = listaProductosProveedor;
	}

    /*public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}*/
	

	public int getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nIF) {
		NIF = nIF;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public void setAll(TProveedor tProveedor) {
		
		this.id_proveedor = tProveedor.getId_proveedor();
		this.disponible = tProveedor.isDisponible();
		this.NIF = tProveedor.getNIF();
		this.nombre = tProveedor.getNombre();
		this.telefono = tProveedor.getTelefono();
		
	}
	
}

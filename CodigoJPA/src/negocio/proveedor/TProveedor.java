package negocio.proveedor;

public class TProveedor  {

	protected int id_proveedor;
	protected String nombre;
	protected String NIF;
	protected String telefono;
	public boolean disponible;
	
	// Constructores
   	
	public TProveedor(){
		
	}
	
	public TProveedor(int ID_Proveedor, boolean disponible, String NIF, String nombre, String telefono) {

		this.id_proveedor = ID_Proveedor;
		this.disponible = disponible;
		this.NIF = NIF;
		this.nombre = nombre;
		this.telefono = telefono;
		
	}
	
	public TProveedor(Proveedor proveedor) {
		
		this.id_proveedor = proveedor.getId_proveedor();
		this.disponible = proveedor.isDisponible();
		this.NIF = proveedor.getNIF();
		this.nombre = proveedor.getNombre();
		this.telefono = proveedor.getNombre();
		
	}

	// Mutadores y Accedentes	

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
	
}
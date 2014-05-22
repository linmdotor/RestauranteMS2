package negocio.productosdeproveedor;

import java.io.Serializable;

import javax.persistence.Version;

public class ProductosDeProveedorId implements Serializable{
	
private static final long serialVersionUID = -4916020350547929977L;
	
	private int proveedor;
	private int producto;
	
	@Override
    public int hashCode() {
        int hash = 1;
        return hash;
    }
    
    public boolean equals(Object o) {

		boolean iguales = false;
	
		return iguales;
		
	}

	public int getProveedor() {
		return proveedor;
	}

	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}
	
}

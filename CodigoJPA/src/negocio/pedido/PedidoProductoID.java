package negocio.pedido;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import negocio.producto.Producto;

public class PedidoProductoID implements Serializable{

		private static final long serialVersionUID = -4916020350547929977L;

		private int producto;
	
		private int pedido;
		 
		 @Override
		    public int hashCode() {
		        int hash = 1;
		        return hash;
		    }
		    
		    public boolean equals(Object o) {

				boolean iguales = false;
			
				return iguales;
				
			}

			public int getProducto() {
				return producto;
			}

			public void setProducto(int producto) {
				this.producto = producto;
			}

			public int getPedido() {
				return pedido;
			}

			public void setPedido(int pedido) {
				this.pedido = pedido;
			}
			
		}

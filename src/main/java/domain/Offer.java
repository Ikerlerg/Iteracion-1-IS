package domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Offer {
	@Id @GeneratedValue
	private long id;
	
	private double precio;
	private String email_vendedor;
	private boolean estado; //true = activa.
	public Offer(double precio, String email,boolean e) {
		this.precio= precio;
		this.email_vendedor= email;
		this.estado= e;
	}
	public double getPrecio() {
		return precio;
	}
	public String getEmail_vendedor() {
		return email_vendedor;
	}
	public boolean isEstado() {
		return estado;
	}
	public Long getId() {
        return id;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Precio: " + precio + "€ | Vendedor: " + email_vendedor;
    }
	
}

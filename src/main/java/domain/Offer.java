package domain;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Offer {
	@Id @GeneratedValue
	private long id;
	
	private double precio;
	private String email_vendedor;
	private boolean estado; //true = activa.
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Solicitud> pendientes= new ArrayList<>();;
	
	public Offer() {
	}
	
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
    public List<Solicitud> getPendientes(){
    	return pendientes;
    }
    public boolean addPendientes(Solicitud pendiente){
    	if(!pendientes.contains(pendiente)) {
    		pendientes.add(pendiente);
    		return true;
    	}
    	return false;
    }
    public boolean deletePendientes(String pendiente){
    	Solicitud solicitud= new Solicitud(pendiente);
    	if(pendientes.contains(solicitud)) {
    		pendientes.remove(solicitud);
    		return true;
    	}
    	return false;
    }


    @Override
    public String toString() {
        return "Precio: " + precio + "€ | Vendedor: " + email_vendedor;
    }
	
}

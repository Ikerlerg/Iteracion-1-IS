package domain;

import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class Solicitud {
	private String buyerMail;
	private int estado; //-1=rechazado|0=pendiente|1=aceptada
	
	public Solicitud() {}
	
	public Solicitud(String buyerMail) {
		super();
		this.buyerMail = buyerMail;
		this.estado = 0;
	}
	public String getBuyerMail() {
		return buyerMail;
	}
	public void setBuyerMail(String buyerMail) {
		this.buyerMail = buyerMail;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solicitud solicitud = (Solicitud) o;
        return Objects.equals(buyerMail, solicitud.buyerMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buyerMail);
    }

	@Override
	public String toString() {
		if(this.estado==0) {
			return this.buyerMail + " oferta pendiente";
		}
		else if(this.estado==-1) {
			return this.buyerMail + " oferta rechazada";
		}
		else {
			return this.buyerMail + " oferta comprada";
		}
	}
	

}

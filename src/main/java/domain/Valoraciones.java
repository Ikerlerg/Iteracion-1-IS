package domain;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Valoraciones {
	@Id @GeneratedValue
	private long idRes;
	
	private long idProd;
	private String eVendedor;
	private String eComprador;
	private String valoracion;
	private String descripcion;
	
	public Valoraciones() {
		
	}
	public Valoraciones(long idProd, String eVendedor, String eComprador,  String val, String desc) {
		this.idProd = idProd;
		this.eVendedor = eVendedor;
		this.eComprador = eComprador;
		this.valoracion = val;
		this.descripcion = desc;
	}
	
	public long getIdProd() {
		return idProd;
	}
	public String geteVendedor() {
		return eVendedor;
	}
	public String geteComprador() {
		return eComprador;
	}
	public String getValoracion() {
		return valoracion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public String toString() {
		return "Valoraciones [id de la reseña=" + idRes + ", id del producto=" + idProd + ", email del vendedor=" + eVendedor + ", email del comprador="
				+ eComprador + ", valoracion=" + valoracion + "]";
	}

	
}

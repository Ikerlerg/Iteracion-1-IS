package domain;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;

@Entity
public class Valoraciones {
	@Id @GeneratedValue
	private long idRes;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Offer productoResena;
	private String eVendedor;
	private String eComprador;
	private String valoracion;
	private String descripcion;
	
	public Valoraciones() {
		
	}
	public Valoraciones(Offer productoResena, String eVendedor, String eComprador,  String val, String desc) {
		
		this.productoResena = productoResena;
		this.eVendedor = eVendedor;
		this.eComprador = eComprador;
		this.valoracion = val;
		this.descripcion = desc;
	}
	
	public Offer getProductoResena() {
		return productoResena;
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
		return "Feedback del comprador: " + valoracion;
	}

	
}

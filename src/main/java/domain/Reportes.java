package domain;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Reportes {
	@Id @GeneratedValue
	private long idRep;
	
	private Offer productoReport;
	private String eVendedor;
	private String eComprador;
	private String reporte;
	
	public Reportes() {
		
	}
	public Reportes(Offer productoReport, String eComprador,String reporte) {
		this.productoReport = productoReport;
		this.eVendedor = productoReport.getEmail_vendedor();
		this.eComprador = eComprador;
		this.reporte = reporte;
	}
	
	public Offer getProductoReport() {
		return productoReport;
	}
	public String geteVendedor() {
		return eVendedor;
	}
	public String geteComprador() {
		return eComprador;
	}
	public String getReporte() {
		return reporte;
	}

	
	@Override
	public String toString() {
		return "Motivo de reporte: "+ reporte;
	}

	
}

package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;

@Entity
public class Reportes {
	@Id
	@GeneratedValue
	private long idRep;
	@ManyToOne(cascade = CascadeType.MERGE)
	private Offer productoReport;
	private String eVendedor;
	private String eComprador;
	private String reporte;
	private int estado;//0=pendiente, 1=aceptado, -1=denegado
    @Lob
    private String fotoBase64;

	public Reportes() {

	}

	public Reportes(Offer productoReport, String eComprador, String reporte,String foto) {
		this.productoReport = productoReport;
		this.eVendedor = productoReport.getEmail_vendedor();
		this.eComprador = eComprador;
		this.reporte = reporte;
		this.estado=0;
		this.fotoBase64=foto;
	}
	public long getIdRep() {
		return idRep;
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
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getFotoBase64() {
		return fotoBase64;
	}


	@Override
	public String toString() {
		if(estado==1) {
			return "Producto: " + productoReport.getSale().getTitle()+ "estado del reporte: Aceptado";
		}
		else if(estado==-1) {
			return "Producto: " + productoReport.getSale().getTitle()+ "estado del reporte: Rechazado";
			
		}
		else {
			return "Producto: " + productoReport.getSale().getTitle()+ "estado del reporte: pendiente";
		}
	}

}

package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cupon {
    @Id
    private String codigo;       
    private double porcentaje;   
    private boolean usado;       

    public Cupon(String codigo, double porcentaje) {
        this.codigo = codigo;
        this.porcentaje = porcentaje;
        this.usado = false;
    }

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public boolean isUsado() {
		return usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}
    
}
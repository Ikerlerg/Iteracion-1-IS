package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cupon {
    @Id
    private String codigo;       
    private double porcentaje;   
    private boolean usado; 
    private String creador;

    public Cupon(String codigo, double porcentaje, String creador) {
        this.codigo = codigo;
        this.porcentaje = porcentaje;
        this.creador = creador;
        this.usado = false;
    }
    public String getCreador() {
        return creador;
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
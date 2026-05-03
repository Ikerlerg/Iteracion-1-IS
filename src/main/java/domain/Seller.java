package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

/*@XmlAccessorType(XmlAccessType.FIELD)
@Entity*/

@Entity
@DiscriminatorValue("SELLER")
public class Seller extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@XmlID
	//@Id 
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Sale> sales=new ArrayList<Sale>();
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Offer> offers = new ArrayList<>();
    private List<Valoraciones> valoracion = new ArrayList<>();

    public Offer addOffer(double precio, String emailVendedor, Sale sell ,boolean estado) {
        Offer nuevaOferta = new Offer(precio, emailVendedor, sell , estado);
        this.offers.add(nuevaOferta);
        return nuevaOferta;
    }
	public Seller() {
		super();
	}

	public Seller(String email, String password, String name, int tipo, String fotoBase64){
		super(email, password, name, tipo, fotoBase64);
	}
	 public Valoraciones addValoracion(Valoraciones val) {
	    	this.valoracion.add(val);
	    	return val;
	    }
	
	public String toString(){
		return (super.getEmail() +";"+ super.getName() + sales);
	}
	
	/**
	 * This method creates/adds a sale to a seller
	 * 
	 * @param title of the sale
	 * @param description of the sale
	 * @param status 
	 * @param selling price
	 * @param publicationDate
	 * @return Sale
	 */
	
	


	public Sale addSale(String title, String description, int status, float price,  Date pubDate, File file)  {
		
		Sale sale=new Sale(title, description, status, price,  pubDate, file, this);
        sales.add(sale);
        return sale;
	}
	

	public void addValor(Valoraciones val)  {	
		valoracion.add(val);
	}
	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesSaleExist(String title)  {	
		for (Sale s:sales)
			if ( s.getTitle().compareTo(title)==0 )
			 return true;
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		//Seller other = (Seller) obj;
		//if ()
			//return false;
		return true;
	}

	
}

package domain;


import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public class User implements Serializable {
 
	private static final long serialVersionUID = 1L;
	@XmlID
	@Id 
    private String email;    
    private String password;
    private String name; 
    private Boolean vendedor;   //True=vendedor False=Comprador   
    
    public User() {
    }

    public User(String email, String password, String name, boolean vendedor) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.vendedor = vendedor;
    }
    public String getEmail() { 
    	return email; 
    	}
    public String getPassword() { 
    	return password; 
    	}
    public String getName() { 
    	return name; 
    	}
    
    public boolean isVendedor() { 
    	return vendedor; 
    	}
}
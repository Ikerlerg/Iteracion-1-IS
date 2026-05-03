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
    private int tipo;   //1=vendedor 2=Comprador -1=admin 
    @Lob
    private String fotoBase64;
    
    public User() {
    }

    public User(String email, String password, String name, int tipo, String fotoBase64) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.tipo = tipo;
        this.fotoBase64 = fotoBase64;
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
    
    public int getTipo() { 
    	return tipo; 
    	}
    
    public void setPassword(String password) { 
    	this.password = password; 
    	}
    
    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
}
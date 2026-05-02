package businessLogic;

import java.io.File;
import java.util.Date;
import java.util.List;
import dataAccess.*;
import domain.*;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.awt.image.BufferedImage;
import java.awt.Image;

import gui.*;
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Sale
	 */
   @WebMethod
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException;
	
	
	/**
	 * This method retrieves the products that contain desc
	 * 
	 * @param desc the text to search
	 * @return collection of sales that contain desc 
	 */
	@WebMethod public List<Sale> getSales(String desc);
	
	/**
	 * 	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @param pubDate the date  of the publication date
	 * @return collection of sales that contain desc and published before pubDate
	 */
	@WebMethod public List<Sale> getPublishedSales(String desc, Date pubDate);

	
	/**
	 * This method calls the data access to initialize the database with some sellers and products.
	 * It is only invoked  when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	@WebMethod public User Login(String email, String password);
	
	@WebMethod  public boolean Registro(String email, String password, String name, int tipo);
		
	@WebMethod public Image downloadImage(String imageName);
	@WebMethod public int obtUser(String email);
	@WebMethod public boolean buscarContraseña(String email, String pwsd);
	@WebMethod public boolean eliminarCuenta(String email);
	@WebMethod public List<Offer> getActiveOffers();
	@WebMethod public boolean acceptOffer(Long offerId);
	@WebMethod public boolean proposeOffer(Long offerId, String buyer);
	@WebMethod public List<Offer> getAcceptedOffers(String sellerEmail);
	@WebMethod public List<Valoraciones> getReseñasPublicadas(String email, String busq);
	@WebMethod public boolean cancelOffer(Long offerId, String buyerMail);
	@WebMethod public List<Offer> getUserOffers(String mail);
	@WebMethod public boolean terminarSolicitud(Long offerId, String buyerMail);
	@WebMethod public boolean publicarValoracion(Valoraciones val1);
	@WebMethod public List<String> getAllSellers();
	@WebMethod public List<Offer> getReseValid(String buyerMail);
	
}

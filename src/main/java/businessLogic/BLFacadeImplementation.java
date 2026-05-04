package businessLogic;

import java.io.File;
import domain.*;
import dataAccess.*;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	private static final int baseSize = 160;

	private static final String basePath = "src/main/resources/images/";
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager = new DataAccess();
	}

	public BLFacadeImplementation(DataAccess da) {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail,
			File file) throws FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		dbManager.open();
		Sale product = dbManager.createSale(title, description, status, price, pubDate, sellerEmail, file);
		dbManager.close();
		return product;
	};

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Sale> getSales(String desc) {
		dbManager.open();
		List<Sale> rides = dbManager.getSales(desc);
		dbManager.close();
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		dbManager.open();
		List<Sale> rides = dbManager.getPublishedSales(desc, pubDate);
		dbManager.close();
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public BufferedImage getFile(String fileName) {
		return dbManager.getFile(fileName);
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();
		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Image downloadImage(String imageName) {
		File image = new File(basePath + imageName);
		try {
			return ImageIO.read(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@WebMethod
	public User Login(String email, String password) {
		dbManager.open();
		User u = dbManager.Login(email, password);
		dbManager.close();
		return u;
	}

	@WebMethod
	public boolean Registro(String email, String password, String name, int tipo) {
		dbManager.open();
		boolean resultado = dbManager.Registro(email, password, name, tipo);
		dbManager.close();
		return resultado;
	}

	@WebMethod
	public int obtUser(String nombre) {
		dbManager.open();
		int u = dbManager.obtUser(nombre);
		dbManager.close();
		return u;
	}

	@WebMethod
	public User devolverUser(String mail) {
		dbManager.open();
		User u = dbManager.devolverUser(mail);
		dbManager.close();
		return u;
	}

	@WebMethod
	public boolean eliminarCuenta(String email) {
		dbManager.open();
		boolean u = dbManager.eliminarCuenta(email);
		dbManager.close();
		return u;
	}

	@WebMethod
	public boolean buscarContraseña(String email, String pwsd) {
		dbManager.open();
		boolean u = dbManager.buscarContraseña(email, pwsd);
		dbManager.close();
		return u;
	}

	@WebMethod
	public List<Offer> getActiveOffers() {
		dbManager.open();
		List<Offer> res = dbManager.getActiveOffers();
		dbManager.close();
		return res;
	}

	@WebMethod
	public boolean acceptOffer(Long offerId) {
		dbManager.open();
		boolean res = dbManager.acceptOffer(offerId);
		dbManager.close();
		return res;
	}

	@WebMethod
	public List<Offer> getAcceptedOffers(String sellerEmail) {
		dbManager.open();
		List<Offer> res = dbManager.getAcceptedOffers(sellerEmail);
		dbManager.close();
		return res;
	}
    
    @WebMethod
    public List<Valoraciones> getReseñasPublicadas(String email, String busq, int tipo) {
		dbManager.open();
		List<Valoraciones> res = dbManager.getReseñasPublicadas(email, busq, tipo);
		dbManager.close();
		return res;
	}

	@WebMethod
	public boolean proposeOffer(Long offerId, String buyer) {
		dbManager.open();
		boolean res = dbManager.proposeOffer(offerId, buyer);
		dbManager.close();
		return res;
	}

	@WebMethod
	public boolean cancelOffer(Long offerId, String buyerMail) {
		dbManager.open();
		boolean res = dbManager.cancelOffer(offerId, buyerMail);
		dbManager.close();
		return res;
	}

	@WebMethod
	public List<Offer> getUserOffers(String mail) {
		dbManager.open();
		List<Offer> res = dbManager.getUserOffers(mail);
		dbManager.close();
		return res;
	}

	@WebMethod
	public boolean terminarSolicitud(Long offerId, String buyerMail) {
		dbManager.open();
		boolean res = dbManager.terminarSolicitud(offerId, buyerMail);
		dbManager.close();
		return res;
	}

	@WebMethod
	public boolean publicarValoracion(Valoraciones val1) {
		dbManager.open();
		boolean res = dbManager.publicarVal(val1);
		dbManager.close();
		return res;
	}

	@WebMethod
	public List<String> getAllSellers() {
		dbManager.open();
		List<String> res = dbManager.getAllSellers();
		dbManager.close();
		return res;

	}

	@WebMethod
	public List<Offer> getReseValid(String buyerMail) {
		dbManager.open();
		List<Offer> res = dbManager.getReseValid(buyerMail);
		dbManager.close();
		return res;
	}

	@WebMethod public boolean guardarImagen(String email, String fotoBase64){
		dbManager.open();
		boolean res = dbManager.guardarImagen(email, fotoBase64);
		dbManager.close();
		return res;
	}
	
	@WebMethod public String obtenerImagen(String email){
		dbManager.open();
		String res = dbManager.obtenerImagen(email);
		dbManager.close();
		return res;
	}

	@WebMethod public boolean reportar(Reportes reporte) {
        dbManager.open();
        boolean res = dbManager.reportar(reporte);
        dbManager.close();
        return res;
	
	}
	@WebMethod public List<Reportes> getReportesEnviados(String sellerMail){
		dbManager.open();
		List<Reportes> res = dbManager.getReportesEnviados(sellerMail);
		dbManager.close();
		return res;
		
	}
	@WebMethod public List<Reportes> getReportesRecibidos(){
		dbManager.open();
		List<Reportes> res = dbManager.getReportesRecibidos();
		dbManager.close();
		return res;
	}

	@WebMethod
	public boolean hayRese(String eVend, String eComp, long idP) {
		dbManager.open();
		boolean res = dbManager.hayRese(eVend, eComp, idP);
		dbManager.close();
		return res;
	}
	@WebMethod 
	public boolean actualizarEstado(long idRep, int estado){
		dbManager.open();
		boolean res = dbManager.actualizarEstado(idRep, estado);
		dbManager.close();
		return res;
	}

}

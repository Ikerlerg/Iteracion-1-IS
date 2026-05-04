package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;
import gui.MainGUI;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	private EntityManager db;
	private EntityManagerFactory emf;
	private static final int baseSize = 160;

	private static final String basePath = "src/main/resources/images/";
	private static final String dbServerDir = "src/main/resources/db/";

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess() {
		if (c.isDatabaseInitialized()) {
			String fileName = c.getDbFilename();

			if (!c.isDatabaseLocal())
				fileName = dbServerDir + fileName;

			File fileToDelete = new File(fileName);
			if (fileToDelete.delete()) {
				File fileToDeleteTemp = new File(fileName + "$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if (c.isDatabaseInitialized())
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "
				+ c.isDatabaseInitialized());

		close();

	}

	public DataAccess(EntityManager db) {
		this.db = db;
	}

	/**
	 * This method initializes the database with some products and sellers. This
	 * method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		try {
			db.getTransaction().begin();

			// Create sellers y admin
			Seller admin = new Seller("admin@gmail.com", "admin", "admin", -1, null);
			Seller seller1 = new Seller("seller1@gmail.com", "12345", "Aitor Fernandez", 1, null);
			Seller seller2 = new Seller("seller22@gmail.com", "54321", "Ane Gaztañaga", 1, null);
			Seller seller3 = new Seller("seller3@gmail.com", "12121", "Test Seller", 1, null);
			User comp1 = new User("comprador1@gmail.com", "12345", "Comprador1", 2, null);
			User comp2 = new User("comprador2@gmail.com", "12345", "Comprador2", 2, null);

			// Create products
			Date today = UtilDate.trim(new Date());

			Sale s1 = seller1.addSale("futbol baloia", "oso polita, gutxi erabilita", 2, 10, today, null);
			Sale s2 = seller1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi", 2, 20, today, null);
			Sale s3 = seller1.addSale("samsung 42\" telebista", "berria, erabili gabe", 2, 175, today, null);

			Sale s4 = seller2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200, today, null);
			Sale s5 = seller2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null);
			Sale s6 = seller2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3, 225, today,
					null);
			Sale s7 = seller2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null);

			Sale s8 = seller3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3, 45, today, null);

			// Offers para seller 1
			seller1.addOffer(10.0, "seller1@gmail.com", s1, true);
			seller1.addOffer(20.0, "seller1@gmail.com", s2, true);
			seller1.addOffer(175.0, "seller1@gmail.com", s3, true);

			// Offers para seller 2
			seller2.addOffer(200.0, "seller22@gmail.com", s4, true);
			seller2.addOffer(400.0, "seller22@gmail.com", s5, true);
			seller2.addOffer(225.0, "seller22@gmail.com", s6, true);
			seller2.addOffer(30.0, "seller22@gmail.com", s7, true);

			// Create offers para seller 3
			seller3.addOffer(45.0, "seller3@gmail.com",s8,true);

			
			db.persist(seller1);
			db.persist(seller2);
			db.persist(seller3);
			db.persist(comp1);
			db.persist(comp2);
			db.persist(admin);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
			if (db.getTransaction().isActive()) {
				db.getTransaction().rollback();
			}
		}
	}

	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title           of the product
	 * @param description     of the product
	 * @param status
	 * @param selling         price
	 * @param category        of a product
	 * @param publicationDate
	 * @return Product
	 * @throws SaleAlreadyExistException if the same product already exists for the
	 *                                   seller
	 */
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail,
			File file) throws FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {

		System.out.println(">> DataAccess: createProduct=> title= " + title + " seller=" + sellerEmail);
		try {

			if (pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file == null)
				throw new FileNotUploadedException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();

			Seller seller = db.find(Seller.class, sellerEmail);
			if (seller.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = seller.addSale(title, description, status, price, pubDate, file);
			// next instruction can be obviated
			seller.addOffer((double) price, sellerEmail, sale, true);

			db.persist(seller);
			db.getTransaction().commit();
			System.out.println("sale stored " + sale + " " + seller);

			return sale;
		} catch (NullPointerException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}

	}

	/**
	 * This method retrieves all the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getSales(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= " + desc);

		List<Sale> res = new ArrayList<Sale>();
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1", Sale.class);
		query.setParameter(1, "%" + desc + "%");

		List<Sale> sales = query.getResultList();
		for (Sale sale : sales) {
			res.add(sale);
		}
		return res;
	}

	/**
	 * This method retrieves the products that contain a desc text in a title and
	 * the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= " + desc);

		List<Sale> res = new ArrayList<Sale>();
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2",
				Sale.class);
		query.setParameter(1, "%" + desc + "%");
		query.setParameter(2, pubDate);

		List<Sale> sales = query.getResultList();
		for (Sale sale : sales) {
			res.add(sale);
		}
		return res;
	}

	public List<Valoraciones> getReseñasPublicadas(String mail, String busq, int tipo) {
		System.out.println(">> DataAccess: getReseñas=> from= " + busq);

		List<Valoraciones> res = new ArrayList<Valoraciones>();
		TypedQuery<Valoraciones> query = null;
		
		if (tipo == 2) {
			query = db.createQuery(
	                "SELECT v FROM Valoraciones v, User u " +
	                "WHERE v.eVendedor = :mail AND v.eComprador = u.email AND u.name LIKE :busq",
	                Valoraciones.class);
	        query.setParameter("mail", mail);
	        query.setParameter("busq", "%" + busq + "%");
		} else if (tipo == 1) {
			query = db.createQuery(
	                "SELECT v FROM Valoraciones v, User u " +
	                "WHERE v.eComprador = :mail AND v.eVendedor = u.email AND u.name LIKE :busq",
	                Valoraciones.class);
	        query.setParameter("mail", mail);
	        query.setParameter("busq", "%" + busq + "%");
		} else {
			query = db.createQuery("SELECT v FROM Valoraciones v", Valoraciones.class);
		}
		List<Valoraciones> reseñas = query.getResultList();
		for (Valoraciones r : reseñas) {
			res.add(r);
		}
		return res;

	}

	public void open() {

		String fileName = c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
			db = emf.createEntityManager();
		}
		System.out.println("DataAccess opened => isDatabaseLocal: " + c.isDatabaseLocal());

	}

	public BufferedImage getFile(String fileName) {
		File file = new File(basePath + fileName);
		BufferedImage targetImg = null;
		try {
			targetImg = rescale(ImageIO.read(file));
		} catch (IOException ex) {
			// Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
		return targetImg;

	}

	public BufferedImage rescale(BufferedImage originalImage) {
		System.out.println("rescale " + originalImage);
		BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
		g.dispose();
		return resizedImage;
	}

	// Hacer Login
	public User Login(String email, String password) {

		User intentoLog = db.find(User.class, email);
		// System.out.println(intentoLog.toString());
		if (intentoLog == null || !intentoLog.getPassword().equals(password)) {
			return null;
		} else {
			// System.out.println(intentoLog.toString());
			return intentoLog;
		}
	}

	// Registro
	public boolean Registro(String email, String password, String name, int tipo) {
		db.getTransaction().begin();
		if (db.find(User.class, email) == null) {
			User registrar;
			if (tipo == 1) {
				registrar = new Seller(email, password, name, tipo, null);
			} else {
				registrar = new User(email, password, name, tipo, null);
			}

			db.persist(registrar);
			db.getTransaction().commit();
			return true;
		} else {
			db.getTransaction().rollback();
			return false;
		}

	}

	public int obtUser(String email) {
		User usuario = db.find(User.class, email);
		if (usuario != null) {
			return usuario.getTipo();
		} else {
			return 0;
		}
	}

	public User devolverUser(String email) {
		try {
			db.getTransaction().begin();
			User usuario = db.find(User.class, email);
			return usuario;
		} catch (Exception e) {
			if (db.getTransaction().isActive()) {
				db.getTransaction().rollback();
			}
			e.printStackTrace();
			return null;
		}
	}

	public boolean buscarContraseña(String email, String pwsd) {
		try {
			db.getTransaction().begin();
			User u = db.find(User.class, email);
			if (u != null) {
				u.setPassword(pwsd);
				db.getTransaction().commit();
				return true;
			} else {
				db.getTransaction().rollback();
				return false;
			}
		} catch (Exception e) {
			if (db.getTransaction().isActive()) {
				db.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarCuenta(String email) {
		try {
			db.getTransaction().begin();
			User u = db.find(User.class, email);
			if (u != null) {
				db.remove(u);
				db.getTransaction().commit();
				return true;
			} else {
				db.getTransaction().rollback();
				return false;
			}
		} catch (Exception e) {
			if (db.getTransaction().isActive()) {
				db.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public List<Offer> getActiveOffers() {
		TypedQuery<Offer> query = db.createQuery("SELECT o FROM Offer o WHERE o.estado = true", Offer.class);
		return query.getResultList();
	}

	public List<Offer> getUserOffers(String mail) {
		TypedQuery<Offer> query = db.createQuery("SELECT o FROM Offer o WHERE o.email_vendedor =:mail", Offer.class);
		query.setParameter("mail", mail);
		List<Offer> UserOffers = query.getResultList();

		if (UserOffers != null) {
			return UserOffers;
		} else {
			return new ArrayList<Offer>();
		}
	}

	public boolean acceptOffer(Long offerId) {
		try {
			db.getTransaction().begin();
			Offer oferta = db.find(Offer.class, offerId);

			if (oferta != null && oferta.isEstado()) {
				oferta.setEstado(false); // Solo cambiamos el estado
				db.persist(oferta);
				db.getTransaction().commit();
				return true;
			}
			db.getTransaction().rollback();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			db.getTransaction().rollback();
			return false;
		}
	}

	public List<Offer> getAcceptedOffers(String email) {
		System.out.println(">> DataAccess: getAcceptedOffers => user=" + email);
		User u = db.find(User.class, email);
		int tipo = (u != null) ? u.getTipo() : 0;
		TypedQuery<Offer> query;

		if (tipo == -1) {
			query = db.createQuery("SELECT o FROM Offer o WHERE o.estado = false", Offer.class);
		} else {
			query = db.createQuery("SELECT o FROM Offer o WHERE o.estado = false AND o.email_vendedor = :email",
					Offer.class);
			query.setParameter("email", email);
		}
		return query.getResultList();
	}

	public boolean proposeOffer(Long offerId, String buyerMail) {
		try {
			db.getTransaction().begin();
			Offer oferta = db.find(Offer.class, offerId);

			if (oferta != null && oferta.isEstado()) {
				Solicitud solicitud = new Solicitud(buyerMail);
				oferta.addPendientes(solicitud); // Solo cambiamos el estado
				db.persist(oferta);
				db.getTransaction().commit();
				return true;
			}
			db.getTransaction().rollback();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			db.getTransaction().rollback();
			return false;
		}
	}

	public boolean cancelOffer(Long offerId, String buyerMail) {
		try {
			db.getTransaction().begin();
			Offer oferta = db.find(Offer.class, offerId);

			if (oferta != null && oferta.isEstado() && oferta.getPendientes().contains(new Solicitud(buyerMail))) {
				oferta.deletePendientes(buyerMail); // Solo cambiamos el estado
				db.persist(oferta);
				db.getTransaction().commit();
				return true;
			}
			db.getTransaction().rollback();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			db.getTransaction().rollback();
			return false;
		}
	}

	public boolean terminarSolicitud(Long offerId, String buyerMail) {
		try {
			db.getTransaction().begin();

			// 1. Buscamos la oferta
			Offer ofertaDB = db.find(Offer.class, offerId);

			if (ofertaDB != null && ofertaDB.isEstado()) {

				for (Solicitud s : ofertaDB.getPendientes()) {

					// 3. Comparamos directamente los emails (Strings).
					if (s.getBuyerMail().equals(buyerMail)) {
						s.setEstado(1); // Aceptada
					} else {
						s.setEstado(-1); // Rechazada
					}
				}

				// la oferta debe dejar de estar activa (estado = false).
				ofertaDB.setEstado(false);
				db.persist(ofertaDB);
				db.getTransaction().commit();
				return true;
			}
			// Si no se encuentra la oferta o ya estaba cerrada
			if (db.getTransaction().isActive())
				db.getTransaction().rollback();
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			if (db.getTransaction().isActive())
				db.getTransaction().rollback();
			return false;
		}
	}

	// Meter reseña en la base de datos
	public boolean publicarVal(Valoraciones val) {
		try {

			db.getTransaction().begin();
			Seller vend = db.find(Seller.class, val.geteVendedor());

			if (vend != null) {
				Valoraciones valGuardada = db.merge(val);
				vend.addValoracion(valGuardada);
				db.persist(vend);
				db.getTransaction().commit();
				return true;

			} else {
				db.getTransaction().rollback();
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			if (db.getTransaction().isActive()) {
				db.getTransaction().rollback();
			}
			return false;
		}
	}
	
	//Este método devuelve true si no hay reseñas asociadas a un email de vendedor, email comprador e id de producto dados como paramétros. En caso contrario, devuelve false.
	public boolean hayRese(String eVend, String eComp, long idP) {
	    try {
	        TypedQuery<Long> query = db.createQuery(
	            "SELECT COUNT(v) FROM Valoraciones v WHERE v.eVendedor = :email AND v.eComprador = :emailcom AND v.productoResena.id = :idProd", 
	            Long.class
	        );
	        
	        query.setParameter("email", eVend);
	        query.setParameter("emailcom", eComp);
	        query.setParameter("idProd", idP);
	        Long numResenas = query.getSingleResult();
	        return numResenas == 0;
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	

	public List<String> getAllSellers() {
		TypedQuery<String> query = db.createQuery("SELECT o.email FROM Seller o", String.class);
		return query.getResultList();
	}

	public List<Offer> getReseValid(String buyerMail) {
		TypedQuery<Offer> query = db.createQuery(
				"SELECT DISTINCT o FROM Offer o JOIN o.pendientes p WHERE o.estado = false AND p.buyerMail = :buyerMail AND p.estado = 1",
				Offer.class);
		query.setParameter("buyerMail", buyerMail);
		return query.getResultList();
	}
	
	public boolean guardarImagen(String email, String fotoBase64) {
		try {
			db.getTransaction().begin();
				
				User usuario = db.find(User.class, email);
				
			if (usuario != null) {
				usuario.setFotoBase64(fotoBase64);
					
				db.getTransaction().commit();
				return true;
			} else {
				db.getTransaction().rollback();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (db.getTransaction().isActive()) {
				db.getTransaction().rollback();
			}
			return false;
		}
	}

	public boolean reportar(Reportes reporte) {
		try {       
	    	db.getTransaction().begin();
	    	db.persist(reporte); 	        
	    	db.getTransaction().commit();
	    	return true; 
		} catch (Exception e) {
	    	e.printStackTrace();
	    	if (db.getTransaction().isActive()) {
	        	db.getTransaction().rollback();
	    	}
	    	return false;
		}
	}

	public String obtenerImagen(String email) {
		try {
		    User usuario = db.find(User.class, email);
	        
	 	    if (usuario != null) {
	  	    	return usuario.getFotoBase64();
	  	    }
	   		return null;
	        
		} catch (Exception e) {
		    e.printStackTrace();
		    return null;
		}
	}

	public List<Reportes> getReportesRecibidos() {
		TypedQuery<Reportes> query = db.createQuery("SELECT o FROM Reportes o", Reportes.class);
		return query.getResultList();
	}

	public List<Reportes> getReportesEnviados(String bullerMail) {
		TypedQuery<Reportes> query = db.createQuery("SELECT o FROM Reportes o WHERE o.eComprador = :bullerMail", Reportes.class);
		query.setParameter("bullerMail", bullerMail);
		return query.getResultList();
	}
	public boolean actualizarEstado(long idRep, int estado) {
		try {
			db.getTransaction().begin();

				Reportes reporte = db.find(Reportes.class, idRep);		
			if (reporte != null&& Math.abs(estado)==1) {				
				reporte.setEstado(estado);
				db.getTransaction().commit();
				return true;
			} else {
				db.getTransaction().rollback();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (db.getTransaction().isActive()) {
				db.getTransaction().rollback();
			}
			return false;
		}
	}
	
	public void close() {
		db.close();
		System.out.println("DataAcess closed");
	}
	

}

package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import businessLogic.BLFacade;
import domain.Offer;
import domain.Solicitud;



public class AceptarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private String buyerMail;
	private JFrame parentFrame;
	
	private JComboBox<Offer> comboBoxOffers;
	private DefaultComboBoxModel<Offer> offerModel;
	
	private JButton jButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Close"));
	protected JLabel jLabelMsg = new JLabel();
	private final JButton CarritoButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.carrito")); 

	public AceptarGUI(MainGUI parent, String mail) {
		this.parentFrame = parent;
		this.buyerMail = mail;
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 250));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
		this.setLocationRelativeTo(parentFrame); // Centra la ventana sobre el MainGUI

		JLabel lblSelect = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.ofertas"));
		lblSelect.setBounds(new Rectangle(30, 20, 250, 20));
		this.getContentPane().add(lblSelect);

		offerModel = new DefaultComboBoxModel<Offer>();
		comboBoxOffers = new JComboBox<Offer>(offerModel);
		comboBoxOffers.setBounds(new Rectangle(30, 50, 420, 30));
		this.getContentPane().add(comboBoxOffers);

		// Cargar datos
		loadOffers();

		jButtonAccept.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		jButtonAccept.setBounds(new Rectangle(90, 110, 180, 40));
		
		jButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelMsg.setText("");
				Offer selectedOffer = (Offer) comboBoxOffers.getSelectedItem();
				
				if (selectedOffer == null) {
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.ofertas"));
					return;
				}
				JFrame ventanaPago = new PagoGUI(AceptarGUI.this, selectedOffer, buyerMail);
				ventanaPago.setVisible(true);

			}
		});

		jButtonClose.setBounds(new Rectangle(290, 115, 100, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra esta ventana
			}
		});

		jLabelMsg.setBounds(new Rectangle(30, 170, 400, 20));

		this.getContentPane().add(lblSelect);
		this.getContentPane().add(comboBoxOffers);
		this.getContentPane().add(jButtonAccept);
		this.getContentPane().add(jButtonClose);
		this.getContentPane().add(jLabelMsg);
		CarritoButton.setBounds(366, 20, 84, 20);
		
		getContentPane().add(CarritoButton);
		CarritoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame carrito = new CarritoGUI(AceptarGUI.this, buyerMail);
				carrito.setVisible(true);
			}
		});
	}

	protected void loadOffers() {
		try {
			offerModel.removeAllElements();
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Offer> offers = facade.getActiveOffers();
			int posibles=0;
			for (Offer o : offers) {
				Solicitud solicitud=new Solicitud(buyerMail);
				if(!o.getPendientes().contains(solicitud)) {//si el usuario no ha comprado la oferta
				offerModel.addElement(o);
				posibles++;
				}
			}
			
			if (offers.isEmpty()||posibles==0) {
				jLabelMsg.setForeground(Color.BLUE);
				jLabelMsg.setText("No hay ofertas activas en este momento.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

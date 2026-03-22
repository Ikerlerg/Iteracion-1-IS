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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AceptarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private String buyerMail;
	private JFrame parentFrame;
	
	private JComboBox<Offer> comboBoxOffers;
	private DefaultComboBoxModel<Offer> offerModel;
	
	private JButton jButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Close"));
	private JLabel jLabelMsg = new JLabel();

	public AceptarGUI(MainGUI parent, String mail) {
		this.parentFrame = parent;
		this.buyerMail = mail;
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 250));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
		this.setLocationRelativeTo(parentFrame); // Centra la ventana sobre el MainGUI

		JLabel lblSelect = new JLabel("Selecciona una oferta disponible:");
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
					jLabelMsg.setText("Por favor, selecciona una oferta.");
					return;
				}

				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					
					// Solo pasamos el ID, tal y como querías
					boolean success = facade.acceptOffer(selectedOffer.getId());
					
					if (success) {
						jLabelMsg.setForeground(Color.GREEN);
						jLabelMsg.setText("¡Oferta aceptada! El estado ha cambiado.");
						loadOffers(); // Refrescar la lista
					} else {
						jLabelMsg.setForeground(Color.RED);
						jLabelMsg.setText("Error al aceptar la oferta.");
					}
				} catch (Exception ex) {
					jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(ex.getMessage());
				}
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
	}

	private void loadOffers() {
		try {
			offerModel.removeAllElements();
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Offer> offers = facade.getActiveOffers();
			
			for (Offer o : offers) {
				offerModel.addElement(o);
			}
			
			if (offers.isEmpty()) {
				jLabelMsg.setForeground(Color.BLUE);
				jLabelMsg.setText("No hay ofertas activas en este momento.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package gui;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import businessLogic.BLFacade;
import domain.Offer;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class SolicitudesGUI extends JFrame {

	private String sellerMail;
	private DefaultListModel<Offer> modeloOffer= new DefaultListModel<Offer>();
	protected JList <Offer>OfferList = new JList<>(modeloOffer);
	private JLabel OffersLabel = new JLabel("New label");
	private JComboBox<String> actionBox = new JComboBox<String>();
	private JButton aceptarButton = new JButton("New button");
	private JLabel actionLabel = new JLabel("New label");
	private final JLabel avisoLabel = new JLabel("");
	
	
	public SolicitudesGUI(VisualizarGUI parent, String sellerMail) {
		this.sellerMail=sellerMail;
		
		getContentPane().setLayout(null);
		
		
		OffersLabel.setBounds(68, 19, 283, 12);
		getContentPane().add(OffersLabel);
		
		
		
		actionBox.setBounds(68, 187, 283, 20);
		getContentPane().add(actionBox);
		actionBox.setEnabled(false);
		
		
		aceptarButton.setBounds(149, 233, 127, 20);
		getContentPane().add(aceptarButton);
		aceptarButton.setEnabled(false);
		
		
		actionLabel.setBounds(68, 171, 283, 12);
		getContentPane().add(actionLabel);
		
		
		OfferList.setBounds(68, 44, 283, 117);
		getContentPane().add(OfferList);
		avisoLabel.setBounds(68, 221, 283, 12);
		OfferList.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	if (!e.getValueIsAdjusting()) {
		    		
		    	Offer seleccion = (Offer) OfferList.getSelectedValue();
		    	if(seleccion !=null) {
		    		actionBox.setEnabled(true);
		    		actionBox.removeAllItems();
		    		for(int i=0; i<seleccion.getPendientes().size();i++) {
		    			actionBox.addItem(seleccion.getPendientes().get(i));//añade los elementos de la lista de solicitudes
		    		}
		    		actionBox.addItem(ResourceBundle.getBundle("Etiquetas").getString("SolicitudesGUI.borrar"));
		    		
		    	}
		    	else {
		    		actionBox.setEnabled(false);
		    	}
		    	}
		    }
		    });
		
		getContentPane().add(avisoLabel);
		loadUserOffers();
		
	}
	private void loadUserOffers() {
		try {
			modeloOffer.removeAllElements();
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Offer> offers = facade.getUserOffers(sellerMail);
			
			for (Offer o : offers) {
				if(o.isEstado()) {
					modeloOffer.addElement(o);
				}
			}
			
			if (modeloOffer.isEmpty()) {
				actionBox.setEnabled(false);
				//mensaje de que no hay ofertas publicas
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
 }
}


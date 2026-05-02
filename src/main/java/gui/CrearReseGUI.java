package gui;

import java.awt.Dimension;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import businessLogic.BLFacade;
import domain.Offer;
import domain.Solicitud;
import domain.Solicitud;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class CrearReseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String sellerMail;
	private String buyerMail;
	private long idProd;

	private DefaultListModel<Offer> modeloOffer= new DefaultListModel<Offer>();
	protected JList <Offer>OfferList = new JList<>(modeloOffer);
	private JLabel OffersLabel = new JLabel("Lista de ofertas compradas:");
	private JButton aceptarButton = new JButton("Crear valoración");

	private final JLabel avisoLabel = new JLabel("");
	public CrearReseGUI(JFrame MainGUI, String mailComp, String mailVend, long idPrd) {
		
		this.buyerMail=mailComp;
		this.sellerMail=mailVend;
		getContentPane().setLayout(null);
		this.setSize(new Dimension(420, 350));
		
		
		OffersLabel.setBounds(68, 19, 283, 12);
		getContentPane().add(OffersLabel);
		
		OfferList.setBounds(68, 44, 283, 117);
	
		avisoLabel.setBounds(68, 221, 283, 12);
		OfferList.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            if (OfferList.getSelectedIndex() != -1) {
		                // Hay un elemento seleccionado, habilitamos el botón
		                aceptarButton.setEnabled(true);
		            } else {
		                // No hay nada seleccionado (o se deseleccionó), apagamos el botón
		                aceptarButton.setEnabled(false);
		            }
		        }
		    }
		});
		
		aceptarButton.setBounds(149, 233, 127, 20);
		getContentPane().add(aceptarButton);
		aceptarButton.setEnabled(false);
		aceptarButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Offer ofertaSeleccionada = OfferList.getSelectedValue();
		        if (ofertaSeleccionada != null) {
		            String mailVendedor = ofertaSeleccionada.getEmail_vendedor();
		            idProd = ofertaSeleccionada.getId();
		            valoracionGUI valVentana = new valoracionGUI(null, buyerMail, mailVendedor, idProd);
		            valVentana.setVisible(true);
		            dispose(); 
		        }
		    }
		});
		
		
		getContentPane().add(avisoLabel);
		loadAceptSoli();

				JScrollPane scrollPane = new JScrollPane(OfferList);
				scrollPane.setBounds(68, 44, 283, 117);
				getContentPane().add(scrollPane);
	}
	
	private void loadAceptSoli() {
		try {
			modeloOffer.removeAllElements();
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Offer> offers = facade.getReseValid(this.buyerMail);
			if (!offers.isEmpty()) {
				modeloOffer.addAll(offers);
			}
			else {
				//mensaje de que no hay ofertas publicas
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
 }

}

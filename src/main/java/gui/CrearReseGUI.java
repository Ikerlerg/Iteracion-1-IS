package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Color;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import businessLogic.BLFacade;
import domain.Offer;
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
	
	// Usamos ResourceBundle para los textos
	private JLabel OffersLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CrearReseGUI.ListaOfertas"));
	private JButton aceptarButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CrearReseGUI.CrerRes"));
	private JButton reportButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CrearReseGUI.Reportar"));
	
	BLFacade bl = MainGUI.getBusinessLogic();

	private final JLabel avisoLabel = new JLabel("");
	
	public CrearReseGUI(JFrame MainGUI, String mailComp, String mailVend) {
		
		this.buyerMail=mailComp;
		this.sellerMail=mailVend;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		this.setSize(new Dimension(420, 350));
		
		// Añadimos el título a la ventana
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CrearReseGUI.Titulo"));
		
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
						reportButton.setEnabled(true);
					} else {
						// No hay nada seleccionado (o se deseleccionó), apagamos el botón
						aceptarButton.setEnabled(false);
						reportButton.setEnabled(false);
					}
				}
			}
		});
		
		aceptarButton.setBounds(224, 234, 127, 20);
		getContentPane().add(aceptarButton);
		aceptarButton.setEnabled(false);
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Offer ofertaSeleccionada = OfferList.getSelectedValue();
				if (ofertaSeleccionada != null) {
					String mailVendedor = ofertaSeleccionada.getEmail_vendedor();
					Offer productoResena = ofertaSeleccionada;
					valoracionGUI valVentana = new valoracionGUI(CrearReseGUI.this, buyerMail, mailVendedor, productoResena);
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
		
		
		reportButton.setEnabled(false);
		reportButton.setBounds(68, 234, 127, 20);
		getContentPane().add(reportButton);
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Offer ofertaSeleccionada = OfferList.getSelectedValue();
				if (ofertaSeleccionada != null) {
					String mailVendedor = ofertaSeleccionada.getEmail_vendedor();
					Offer productoReport = ofertaSeleccionada;
					ReportarGUI ventanaReporte = new ReportarGUI(CrearReseGUI.this, buyerMail, mailVendedor, productoReport);
					ventanaReporte.setVisible(true);
					dispose(); 
				}
			}
		});
				
	}
	
	private void loadAceptSoli() {
		try {
			modeloOffer.removeAllElements();
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Offer> offers = facade.getReseValid(this.buyerMail);
			if (!offers.isEmpty()) {
				for (Offer offer : offers) {
					if(bl.hayRese(offer.getEmail_vendedor(), buyerMail, offer.getId())&&bl.hayRepo(offer.getEmail_vendedor(), buyerMail, offer.getId())) {
						modeloOffer.addElement(offer);
					}
				}
			}
			else {
				OffersLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CrearReseGUI.NoOfertas"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
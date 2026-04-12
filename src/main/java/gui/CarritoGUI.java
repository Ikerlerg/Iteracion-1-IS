package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import businessLogic.BLFacade;
import domain.Offer;
import domain.Solicitud;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CarritoGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String buyerMail;
	
	private DefaultListModel<Offer> modeloSolicitud= new DefaultListModel<Offer>();
	private JList<Offer> listaSolicitud= new JList<>(modeloSolicitud);
	
	
	private JLabel SolicitudLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.solicitudLabel"));
	private JButton CancelButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.cancelar"));
	private JLabel CarritoerrorLabel = new JLabel("");
	private final JButton SalirButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.exit"));

	public CarritoGUI(AceptarGUI parent, String buyerMail) {
		super();
		this.buyerMail = buyerMail;
		
		getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 325));
		
		JScrollPane scrollPane = new JScrollPane(listaSolicitud);
		scrollPane.setBounds(72, 58, 279, 151); 
		getContentPane().add(scrollPane);
		
		
		listaSolicitud.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaSolicitud.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		    	if (!e.getValueIsAdjusting()) {
		    	Offer seleccion = (Offer) listaSolicitud.getSelectedValue();
		    	if(seleccion !=null) {
		    		CancelButton.setEnabled(true);
		    	}
		    	else {
		    		CancelButton.setEnabled(false);
		    	}
		    	}
		    }
		    });
		
		
		
		SolicitudLabel.setBounds(72, 36, 279, 12);
		getContentPane().add(SolicitudLabel);
		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Offer selectedOffer = (Offer) listaSolicitud.getSelectedValue();
				try {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean success=facade.cancelOffer(selectedOffer.getId(), buyerMail);
				
				if (success) {
					CarritoerrorLabel.setForeground(Color.GREEN);
					CarritoerrorLabel.setText( ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.exito"));
					loadOffers();
					parent.loadOffers();
					 
				} else {
					CarritoerrorLabel.setForeground(Color.RED);
					CarritoerrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.fracaso"));
					
				}
			} catch (Exception ex) {
				CarritoerrorLabel.setForeground(Color.RED);
				CarritoerrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.fracaso"));
				
				
			}
				
				
				
			}
		});
		
		
		CancelButton.setBounds(115, 233, 207, 20);
		getContentPane().add(CancelButton);
		CancelButton.setEnabled(false);
		
		CarritoerrorLabel.setBounds(72, 217, 279, 12);
		getContentPane().add(CarritoerrorLabel);
		SalirButton.setBounds(325, 10, 84, 20);
		SalirButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra esta ventana
			}
		});
		getContentPane().add(SalirButton);
		
		
		loadOffers();
		
	}
	private void loadOffers() {
		try {
			modeloSolicitud.removeAllElements();
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Offer> offers = facade.getActiveOffers();
			
			for (Offer o : offers) {
				Solicitud solicitud=new Solicitud(buyerMail);
				if(o.getPendientes().contains(solicitud)) {
					modeloSolicitud.addElement(o);
			
				}
			}
			
			if (modeloSolicitud.isEmpty()) {
				CarritoerrorLabel.setForeground(Color.BLUE);
				CarritoerrorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.vacio"));
			}
			else {
				CarritoerrorLabel.setText("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

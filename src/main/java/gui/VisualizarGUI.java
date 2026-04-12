package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import businessLogic.BLFacade;
import domain.Offer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VisualizarGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private String sellerMail;
	private JFrame parentFrame;
	
	private JList<Offer> listOffers;
	private DefaultListModel<Offer> listModel;
	
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private final JButton SoliButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("VisualizarGUI.Solicitudes"));
	

	public VisualizarGUI(MainGUI parent, String mail) {
		this.parentFrame = parent;
		this.sellerMail = mail;
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 300));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Ofertas")); // Título de la ventana
		this.setLocationRelativeTo(parentFrame);

		BLFacade facade = MainGUI.getBusinessLogic();
		JLabel lblTitle = new JLabel();
		int tipo = facade.obtUser(sellerMail);
		if (tipo == -1) {
			lblTitle.setText("Todas las ofertas aceptadas en el sistema (Modo Admin):");
		} else {
			lblTitle.setText("Tus ofertas que han sido aceptadas:");
		}
		lblTitle.setBounds(new Rectangle(30, 20, 300, 20));
		this.getContentPane().add(lblTitle);

		// Usamos un JList dentro de un JScrollPane para poder hacer scroll si hay muchas
		listModel = new DefaultListModel<Offer>();
		listOffers = new JList<Offer>(listModel);
		
		JScrollPane scrollPane = new JScrollPane(listOffers);
		scrollPane.setBounds(new Rectangle(30, 50, 420, 130));
		this.getContentPane().add(scrollPane);

		// Cargar datos
		loadAcceptedOffers();

		// Botón de Cerrar centrado
		jButtonClose.setBounds(new Rectangle(129, 200, 100, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra esta ventana
			}
		});

		jLabelMsg.setBounds(new Rectangle(30, 240, 400, 20));

		this.getContentPane().add(jButtonClose);
		this.getContentPane().add(jLabelMsg);
		
		SoliButton.setBounds(new Rectangle(129, 200, 100, 30));
		SoliButton.setBounds(269, 200, 100, 30);
		
		getContentPane().add(SoliButton);
		SoliButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame Solicitudes = new SolicitudesGUI(VisualizarGUI.this, sellerMail);
				Solicitudes.setVisible(true);
			}
		});
		
	}

	private void loadAcceptedOffers() {
		try {
			listModel.removeAllElements();
			BLFacade facade = MainGUI.getBusinessLogic();
			
			// Llamamos al nuevo método pasándole el correo del vendedor
			List<Offer> offers = facade.getAcceptedOffers(sellerMail);
			
			
			for (Offer o : offers) {
				listModel.addElement(o);
			}

			
			if (offers.isEmpty()) {
				jLabelMsg.setForeground(java.awt.Color.BLUE);
				jLabelMsg.setText("Aún no tienes ninguna oferta aceptada.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

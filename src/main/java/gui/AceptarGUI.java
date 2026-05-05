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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Offer;
import domain.Sale;
import domain.Solicitud;
import javax.swing.JPanel;



public class AceptarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private String buyerMail;
	private String SellerMail;
	private long prodId;
	private JFrame parentFrame;

	
	private JButton jButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Close"));
	protected JLabel jLabelMsg = new JLabel();
	private final JButton CarritoButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.carrito")); 
	private final JButton crearRese = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.rese")); //$NON-NLS-1$ //$NON-NLS-2$
	
	private final JScrollPane scrollPanelOffers = new JScrollPane();
	private JTable tableOffers= new JTable();
	private DefaultTableModel tableModelOffers;
	private String[] columnNamesOffers = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"), 
			ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.mail"),    
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price") 
	};
	

	public AceptarGUI(MainGUI parent, String mail) {
		this.parentFrame = parent;
		this.buyerMail = mail;
		this.setSize(new Dimension(500, 353));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
		this.setLocationRelativeTo(parentFrame); // Centra la ventana sobre el MainGUI
		getContentPane().setLayout(null);

		JLabel lblSelect = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.ofertas"));
		lblSelect.setBounds(30, 20, 250, 20);
		this.getContentPane().add(lblSelect);



		// Cargar datos
		
		jButtonAccept.setBounds(100, 233, 180, 40);

		jButtonAccept.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		
		jButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelMsg.setText("");
				int fila = tableOffers.getSelectedRow();//pilla el índice de la fila seleccionada
		        if (fila == -1) {
		        	jLabelMsg.setForeground(Color.RED);
					jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.ofertas"));
					return;
		        }
		        int columnaOffer = tableOffers.convertRowIndexToModel(fila);
				Offer selectedOffer = (Offer) tableModelOffers.getValueAt(columnaOffer, 3);
				

				SellerMail = selectedOffer.getEmail_vendedor();
				prodId = selectedOffer.getId();
				JFrame ventanaPago = new PagoGUI(AceptarGUI.this, selectedOffer, buyerMail);
				ventanaPago.setVisible(true);
				
			}
			
		});
		jButtonClose.setBounds(309, 240, 100, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra esta ventana
			}
		});
		crearRese.setBounds(309, 211, 165, 23);
		crearRese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaRese = new CrearReseGUI(AceptarGUI.this,buyerMail,SellerMail);
				ventanaRese.setVisible(true);
			}});

		this.getContentPane().add(lblSelect);
		this.getContentPane().add(jButtonAccept);
		this.getContentPane().add(jButtonClose);
		jLabelMsg.setBounds(47, 286, 400, 20);
		this.getContentPane().add(jLabelMsg);
		CarritoButton.setBounds(309, 20, 165, 20);
		
		getContentPane().add(CarritoButton);
		
		
		getContentPane().add(crearRese);
		
		
		scrollPanelOffers.setBounds(new Rectangle(52, 137, 459, 150));
		scrollPanelOffers.setBounds(10, 50, 459, 150);
		
		scrollPanelOffers.setViewportView(tableOffers);
		tableModelOffers = new DefaultTableModel(null, columnNamesOffers);

		
		tableModelOffers = new DefaultTableModel(null, columnNamesOffers);
		tableModelOffers.setColumnCount(4); 
		tableOffers.setModel(tableModelOffers);
		tableOffers.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableOffers.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableOffers.getColumnModel().getColumn(2).setPreferredWidth(5);
		tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(3));
		getContentPane().add(scrollPanelOffers);
		
		
		CarritoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame carrito = new CarritoGUI(AceptarGUI.this, buyerMail);
				carrito.setVisible(true);
			}
		});
		loadOffers();
	}

	protected void loadOffers() {
		try {
			tableModelOffers.setRowCount(0);
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Offer> offers = facade.getActiveOffers();
			int posibles=0;
			for (Offer o : offers) {
				Solicitud solicitud=new Solicitud(buyerMail);
				if(!o.getPendientes().contains(solicitud)) {//si el usuario no ha comprado la oferta
					Object[] dataOfertas = new Object[4]; 
					dataOfertas[0]=o.getSale().getTitle();//Columna 1
					dataOfertas[1]=o.getEmail_vendedor();//Columna 2
					dataOfertas[2]=o.getPrecio()  + "€";//Columna 3
					dataOfertas[3]= o;//para poder seleccionar
					tableModelOffers.addRow(dataOfertas);
				       

					posibles++;
				}
			}
			
			if (offers.isEmpty()||posibles==0) {
				jLabelMsg.setForeground(Color.BLUE);
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.noOfertas"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
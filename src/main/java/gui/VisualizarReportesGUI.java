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
import domain.Reportes;
import domain.Sale;
import domain.Solicitud;
import javax.swing.JPanel;



public class VisualizarReportesGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private String buyerMail;
	private JFrame parentFrame;
	private int tipo;
	
	private JButton jButtonAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Close"));
	protected JLabel jLabelMsg = new JLabel();
	
	private final JScrollPane scrollPanelOffers = new JScrollPane();
	private JTable tableOffers= new JTable();
	private DefaultTableModel tableModelOffers;
	private String[] columnNamesReports = new String[] {
			"Correo:", 
			"estado",    
			
	};
	

	public VisualizarReportesGUI(MainGUI parent, String mail) {
		BLFacade facade = MainGUI.getBusinessLogic();
		this.parentFrame = parent;
		this.buyerMail = mail;
		this.tipo= facade.obtUser(mail);
		
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
			}
		});
		jButtonClose.setBounds(309, 240, 100, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra esta ventana
			}
		});

		this.getContentPane().add(lblSelect);
		this.getContentPane().add(jButtonAccept);
		this.getContentPane().add(jButtonClose);
		jLabelMsg.setBounds(47, 286, 400, 20);
		this.getContentPane().add(jLabelMsg);
		
		
		scrollPanelOffers.setBounds(new Rectangle(52, 137, 459, 150));
		scrollPanelOffers.setBounds(10, 50, 459, 150);
		
		scrollPanelOffers.setViewportView(tableOffers);
		tableModelOffers = new DefaultTableModel(null, columnNamesReports);

		
		tableModelOffers = new DefaultTableModel(null, columnNamesReports);
		tableModelOffers.setColumnCount(3); 
		tableOffers.setModel(tableModelOffers);
		tableOffers.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableOffers.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableOffers.getColumnModel().removeColumn(tableOffers.getColumnModel().getColumn(2));
		getContentPane().add(scrollPanelOffers);
		loadOffers();
	}

	protected void loadOffers() {
		try {
			tableModelOffers.setRowCount(0);
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Reportes> reportes;
			if(tipo==-1) {
				reportes=facade.getReportesRecibidos();
			}
			else {
				reportes=facade.getReportesEnviados(buyerMail);
			}
			int posibles=0;
			for (Reportes r : reportes) {
					Object[] dataOfertas = new Object[3]; 
					dataOfertas[0]=r.geteComprador();//Columna 1
					if(r.getEstado()==-1) {
						dataOfertas[1]= "Denegado";
					}
					else if(r.getEstado()==1) {
						dataOfertas[1]= "Aceptado";
					}
					else {
						dataOfertas[1]= "Pendiente";
					}
					
					
					dataOfertas[2]=r;
			
					tableModelOffers.addRow(dataOfertas);
				       

					posibles++;
				}
			
			
			if (reportes.isEmpty()||posibles==0) {
				jLabelMsg.setForeground(Color.BLUE);
				jLabelMsg.setText("No hay ofertas activas en este momento.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

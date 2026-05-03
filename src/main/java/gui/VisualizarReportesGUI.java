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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

	private JButton jButtonVisualizar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Close"));
	protected JLabel jLabelMsg = new JLabel();

	private final JScrollPane scrollPanelReport = new JScrollPane();
	private JTable tableReportes = new JTable();
	private DefaultTableModel tableModelReport;
	private String[] columnNamesReports = new String[] { "Correo:", "estado",

	};

	public VisualizarReportesGUI(MainGUI parent, String mail) {
		BLFacade facade = MainGUI.getBusinessLogic();
		this.parentFrame = parent;
		this.buyerMail = mail;
		this.tipo = facade.obtUser(mail);

		this.setSize(new Dimension(500, 353));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
		this.setLocationRelativeTo(parentFrame); // Centra la ventana sobre el MainGUI
		getContentPane().setLayout(null);

		JLabel lblSelect = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.ofertas"));
		lblSelect.setBounds(30, 20, 250, 20);
		this.getContentPane().add(lblSelect);

		// Cargar datos

		jButtonVisualizar.setBounds(100, 233, 180, 40);
		jButtonVisualizar.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		jButtonVisualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tableReportes.getSelectedRow();
				if (fila != -1) { 
					int columnaOffer = tableReportes.convertRowIndexToModel(fila);
					Reportes selectedOffer = (Reportes) tableModelReport.getValueAt(columnaOffer, 2);
				}
			}
		});
		
		
		jButtonClose.setBounds(309, 240, 100, 30);
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra esta ventana
			}
		});

		this.getContentPane().add(lblSelect);
		this.getContentPane().add(jButtonVisualizar);
		this.getContentPane().add(jButtonClose);
		jLabelMsg.setBounds(47, 286, 400, 20);
		this.getContentPane().add(jLabelMsg);

		scrollPanelReport.setBounds(new Rectangle(52, 137, 459, 150));
		scrollPanelReport.setBounds(10, 50, 459, 150);

		scrollPanelReport.setViewportView(tableReportes);

		tableModelReport = new DefaultTableModel(null, columnNamesReports);
		tableModelReport.setColumnCount(3);
		tableReportes.setModel(tableModelReport);
		tableReportes.getColumnModel().getColumn(0).setPreferredWidth(150);
		tableReportes.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableReportes.getColumnModel().removeColumn(tableReportes.getColumnModel().getColumn(2));
		tableReportes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (tipo == -1 && !event.getValueIsAdjusting()) {
					int fila = tableReportes.getSelectedRow();
					if (fila != -1) { 
						int columnaOffer = tableReportes.convertRowIndexToModel(fila);
						Reportes selectedReport = (Reportes) tableModelReport.getValueAt(columnaOffer, 2);
						if (selectedReport.getEstado() == -1 || selectedReport.getEstado() == 1) {
							jButtonVisualizar.setEnabled(false);
						} else { 
							jButtonVisualizar.setEnabled(true);
						}
					} else { 
						jButtonVisualizar.setEnabled(false);
					}
				}
			}
		});
		
		
		getContentPane().add(scrollPanelReport);

		loadOffers();
	}

	protected void loadOffers() {
		try {
			tableModelReport.setRowCount(0);
			BLFacade facade = MainGUI.getBusinessLogic();
			List<Reportes> reportes;
			if (tipo == -1) {
				reportes = facade.getReportesRecibidos();
			} else {
				reportes = facade.getReportesEnviados(buyerMail);
			}
			for (Reportes r : reportes) {
				Object[] dataOfertas = new Object[3];
				dataOfertas[0] = r.geteComprador();
				if (r.getEstado() == -1) {
					dataOfertas[1] = "Denegado";
				} else if (r.getEstado() == 1) {
					dataOfertas[1] = "Aceptado";
				} else {
					dataOfertas[1] = "Pendiente";
				}

				dataOfertas[2] = r;

				tableModelReport.addRow(dataOfertas);

			}

			if (reportes.isEmpty()) {
				jLabelMsg.setForeground(Color.BLUE);
				jLabelMsg.setText("No hay ofertas activas en este momento.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tipo == -1) {
			jButtonVisualizar.setVisible(true);
			jButtonVisualizar.setEnabled(false); // Desactivado por defecto al cargar
		} else {
			jButtonVisualizar.setVisible(false);
		}

	}
}

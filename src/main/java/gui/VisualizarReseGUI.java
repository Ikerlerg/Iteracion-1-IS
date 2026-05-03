package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import domain.Valoraciones;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class VisualizarReseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JLabel jLabelReseñas = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.reseña"));

	private JButton jButtonSearch = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelReseñas = new JScrollPane();
	private JTable tableReseñas = new JTable();

	private DefaultTableModel tableModelReseñas;

	private JFrame thisFrame;

	private String[] columnNamesReseñas = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.vendedor"),
			ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.titulo"),
			ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.valoracion"),
			ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.descripcion")

	};
	private JTextField jTextFieldSearch;

	public VisualizarReseGUI(String email, int tipo) {
		tableReseñas.setEnabled(false);
		thisFrame = this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.ver") + ": " + email);

		jLabelReseñas.setBounds(52, 108, 427, 16);
		this.getContentPane().add(jLabelReseñas);

		jButtonClose.setBounds(new Rectangle(220, 379, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);

			}
		});

		this.getContentPane().add(jButtonClose, null);

		scrollPanelReseñas.setBounds(new Rectangle(52, 137, 459, 150));
		scrollPanelReseñas.setViewportView(tableReseñas);

		tableModelReseñas = new DefaultTableModel(null, columnNamesReseñas);
		tableReseñas.setModel(tableModelReseñas);

		tableModelReseñas.setColumnCount(5);

		tableReseñas.getColumnModel().getColumn(0).setPreferredWidth(142);
		tableReseñas.getColumnModel().getColumn(1).setPreferredWidth(142);
		tableReseñas.getColumnModel().getColumn(2).setPreferredWidth(71);
		tableReseñas.getColumnModel().getColumn(3).setPreferredWidth(205);

		tableReseñas.getColumnModel().removeColumn(tableReseñas.getColumnModel().getColumn(4));

		this.getContentPane().add(scrollPanelReseñas, null);

		jTextFieldSearch = new JTextField();
		jTextFieldSearch.setBounds(52, 56, 357, 26);
		getContentPane().add(jTextFieldSearch);
		jTextFieldSearch.setColumns(10);

		jButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tableModelReseñas.setDataVector(null, columnNamesReseñas);
					tableModelReseñas.setColumnCount(5); // another column added to allocate product object

					BLFacade facade = MainGUI.getBusinessLogic();

					List<Valoraciones> valoraciones = facade.getReseñasPublicadas(email, jTextFieldSearch.getText(),
							tipo);

					if (valoraciones.isEmpty())
						jLabelReseñas.setText(
								ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.noreseñas"));
					else
						jLabelReseñas.setText(
								ResourceBundle.getBundle("Etiquetas").getString("VisualizarReseGUI.sireseñas"));

					if (tipo == 2) {
						for (Valoraciones val : valoraciones) {
							Vector<Object> row = new Vector<Object>();
							row.add(val.geteComprador());
							row.add(val.getProductoResena().getSale().getTitle());
							row.add(val.getValoracion());
							row.add(val.getDescripcion());
							row.add(val);
							tableModelReseñas.addRow(row);
						}
					}
					else if(tipo == 1) {
						for (Valoraciones val : valoraciones) {
							Vector<Object> row = new Vector<Object>();
							row.add(val.geteVendedor());
							row.add(val.getProductoResena().getSale().getTitle());
							row.add(val.getValoracion());
							row.add(val.getDescripcion());
							row.add(val);
							tableModelReseñas.addRow(row);
						}
					} else {
						for (Valoraciones val : valoraciones) {
							Vector<Object> row = new Vector<Object>();
							row.add(val.geteComprador());
							row.add(val.getProductoResena().getSale().getTitle());
							row.add(val.getValoracion());
							row.add(val.getDescripcion());
							row.add(val);
							tableModelReseñas.addRow(row);
						}
					}

				} catch (Exception e1) {

					e1.printStackTrace();
				}
				tableReseñas.getColumnModel().getColumn(0).setPreferredWidth(142);
				tableReseñas.getColumnModel().getColumn(1).setPreferredWidth(142);
				tableReseñas.getColumnModel().getColumn(2).setPreferredWidth(71);
				tableReseñas.getColumnModel().getColumn(3).setPreferredWidth(205);

				tableReseñas.getColumnModel().removeColumn(tableReseñas.getColumnModel().getColumn(4));
			}
		});
		jButtonSearch.setBounds(427, 56, 117, 29);
		getContentPane().add(jButtonSearch);

		tableReseñas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent mouseEvent) {

				if (mouseEvent.getClickCount() == 2) {
					JTable table = (JTable) mouseEvent.getSource();
					Point point = mouseEvent.getPoint();
					int row = table.rowAtPoint(point);
					Valoraciones v = (Valoraciones) tableModelReseñas.getValueAt(row, 4);
					new ShowReseGUI(v);
				}
			}
		});
	}
}

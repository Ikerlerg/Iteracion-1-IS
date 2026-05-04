package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import businessLogic.BLFacade;
import domain.Reportes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
/*
 * Queda por añadir y cargar una foto en los reportes 
 */

public class AdminReportesGUI extends JFrame {
	private JTextField textField;
	private JButton denyButton = new JButton("Denegar");
	private 		JButton aceptButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SolicitudesGUI.aceptar"));

	public AdminReportesGUI(VisualizarReportesGUI parent, Reportes reporte) {
		this.setSize(new Dimension(500, 353));
		getContentPane().setLayout(null);

		JLabel ReporteLabel = new JLabel("New label");
		ReporteLabel.setBounds(70, 34, 204, 12);
		getContentPane().add(ReporteLabel);

		textField = new JTextField(reporte.getReporte());
		textField.setEditable(false);
		textField.setBounds(70, 91, 331, 87);
		getContentPane().add(textField);
		textField.setColumns(10);

		JButton close = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.exit"));
		close.setBounds(320, 30, 84, 20);
		getContentPane().add(close);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});


		aceptButton.setBounds(70, 212, 104, 28);
		getContentPane().add(aceptButton);
		aceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean success = facade.actualizarEstado(reporte.getIdRep(), 1);
				if (success) {
					ReporteLabel.setForeground(Color.GREEN);
					ReporteLabel.setText("Reporte aceptado con éxito");
					parent.loadOffers();
					aceptButton.setEnabled(false);
					denyButton.setEnabled(false);
					
					Timer timer = new Timer(1000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();
				} else {
					ReporteLabel.setForeground(Color.RED);
					ReporteLabel.setText("Error al actualizar el estado");;
				}
			}
		});

		
		denyButton.setBounds(297, 212, 104, 28);
		getContentPane().add(denyButton);
		denyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean success = facade.actualizarEstado(reporte.getIdRep(), -1);
				if (success) {
					parent.loadOffers();
					ReporteLabel.setForeground(Color.GREEN);
					ReporteLabel.setText("Reporte rechazado con éxito");
					aceptButton.setEnabled(false);
					denyButton.setEnabled(false);
					Timer timer = new Timer(1000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();

				} else {
					ReporteLabel.setForeground(Color.RED);
					ReporteLabel.setText("Error al actualizar el estado");
				}
			}
		});
	}
}

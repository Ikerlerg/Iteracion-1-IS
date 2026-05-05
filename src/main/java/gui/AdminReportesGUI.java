package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import businessLogic.BLFacade;
import domain.Reportes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;

public class AdminReportesGUI extends JFrame {
	private JTextField textField;
	private JButton denyButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.denegar"));
	private JButton aceptButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SolicitudesGUI.aceptar"));
	private JPanel panelImagen; 
	private int baseSize = 100;
	
	public AdminReportesGUI(VisualizarReportesGUI parent, Reportes reporte) {
		this.setSize(new Dimension(500, 353));
		// Se añade el título de la ventana
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.titulo"));
		getContentPane().setLayout(null);

		JLabel ReporteLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.descripcionProblema")); 
		ReporteLabel.setBounds(70, 34, 204, 12);
		getContentPane().add(ReporteLabel);

		textField = new JTextField(reporte.getReporte());
		textField.setEditable(false);
		textField.setBounds(70, 91, 214, 87);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		panelImagen = new JPanel();
		panelImagen.setBounds(317, 70, baseSize, baseSize);
		panelImagen.setLayout(new BorderLayout()); 
		getContentPane().add(panelImagen); 

		String fotoBase64 = reporte.getFotoBase64();
		if (fotoBase64 != null && !fotoBase64.isEmpty()) {
			try {
				byte[] imageBytes = Base64.getDecoder().decode(fotoBase64);
				ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
				BufferedImage bImage = ImageIO.read(bis);
				
				Image scaledImg = bImage.getScaledInstance(baseSize, baseSize, Image.SCALE_SMOOTH);
				panelImagen.add(new JLabel(new ImageIcon(scaledImg)));
				
			} catch (Exception ex) {
				panelImagen.add(new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.errorImagen")));
				ex.printStackTrace();
			}
		} else {
			panelImagen.add(new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.sinFoto")));
		}
		
		JButton close = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.exit"));
		close.setBounds(320, 30, 84, 20);
		getContentPane().add(close);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		aceptButton.setBounds(116, 212, 104, 28);
		getContentPane().add(aceptButton);
		aceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean success = facade.actualizarEstado(reporte.getIdRep(), 1);
				if (success) {
					ReporteLabel.setForeground(Color.GREEN);
					ReporteLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.exitoAceptar"));
					parent.loadOffers();
					aceptButton.setEnabled(false);
					denyButton.setEnabled(false);
					
					Timer timer = new Timer(1000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();
				} else {
					ReporteLabel.setForeground(Color.RED);
					ReporteLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.errorActualizar"));
				}
			}
		});

		
		denyButton.setBounds(300, 212, 104, 28);
		getContentPane().add(denyButton);
		denyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean success = facade.actualizarEstado(reporte.getIdRep(), -1);
				if (success) {
					parent.loadOffers();
					ReporteLabel.setForeground(Color.GREEN);
					ReporteLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.exitoRechazar"));
					aceptButton.setEnabled(false);
					denyButton.setEnabled(false);
					Timer timer = new Timer(1000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();

				} else {
					ReporteLabel.setForeground(Color.RED);
					ReporteLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("AdminReportesGUI.errorActualizar"));
				}
			}
		});
	}
}
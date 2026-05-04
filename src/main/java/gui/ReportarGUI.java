package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class ReportarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Descripcion;
	/*
	 * Queda por añadir y cargar una foto en los reportes 
	 */
	
	public ReportarGUI(CrearReseGUI ventanaPadre, String mailComp, String mailVend, Offer productoReporte) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Descripción:");
		lblNewLabel.setBounds(50, 44, 100, 16);
		contentPane.add(lblNewLabel);
		
		Descripcion = new JTextField();
		Descripcion.setBounds(60, 72, 321, 129);
		contentPane.add(Descripcion);
		Descripcion.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("REPORTE");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 28));
		lblNewLabel_1.setBounds(140, 10, 156, 35);
		contentPane.add(lblNewLabel_1);
		
		JLabel textoConf = new JLabel("");
		textoConf.setEnabled(false);
		textoConf.setToolTipText("");
		textoConf.setBounds(29, 29, 370, 16);
		contentPane.add(textoConf);

		
		JButton botonEnviar = new JButton("Enviar");
		botonEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desc = Descripcion.getText();
				BLFacade bl = MainGUI.getBusinessLogic();
				if(!desc.isBlank()) {	
					Reportes reporte = new Reportes(productoReporte, mailComp,desc);
					bl.reportar(reporte);
					textoConf.setText("Reporte enviado, espere respuesta pacientemente");
					Timer timer = new Timer(3000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();
				}
				else {
					textoConf.setText("Por favor, incluya el motivo de reporte");
				}
			}
		});
		botonEnviar.setVerticalAlignment(SwingConstants.TOP);
		botonEnviar.setBounds(170, 223, 98, 26);
		contentPane.add(botonEnviar);
	
	}
}

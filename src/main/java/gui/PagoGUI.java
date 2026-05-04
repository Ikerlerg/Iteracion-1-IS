package gui;
import javax.swing.JFrame;
import java.awt.Color;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;

import javax.swing.JLabel;

import businessLogic.BLFacade;
import domain.Offer;


import javax.swing.JTextField;

public class PagoGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField TitularField;
	private JTextField NumberField;
	private JTextField CvvField;
	private JTextField FechaField;
	
	public PagoGUI(AceptarGUI parent, Offer compra, String buyerMail) {
		super();
		
		getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 325));
		
		JButton PagoButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.cobro"));
		PagoButton.setBounds(282, 210, 95, 29);
		getContentPane().add(PagoButton);
		PagoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
				BLFacade facade = MainGUI.getBusinessLogic();//no se si usar aquí MainGUI dará problemas
				boolean success = facade.proposeOffer(compra.getId(),buyerMail);
				
				if (success) {
					parent.jLabelMsg.setForeground(Color.GREEN);
					parent.jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.aceptada"));
					parent.loadOffers(); // 
				} else {
					parent.jLabelMsg.setForeground(Color.RED);
					parent.jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.erroraceptar"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}finally {
				dispose();
			}
		}
	});
		
		
		JButton Cancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.exit"));
		Cancel.setBounds(63, 212, 95, 24);
		getContentPane().add(Cancel);
		Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            }
        });
		
		TitularField = new JTextField();
		TitularField.setBounds(63, 56, 314, 19);
		getContentPane().add(TitularField);
		TitularField.setColumns(10);
		
		NumberField = new JTextField();
		NumberField.setBounds(62, 109, 315, 18);
		getContentPane().add(NumberField);
		NumberField.setColumns(10);
		
		CvvField = new JTextField();
		CvvField.setBounds(63, 167, 96, 18);
		getContentPane().add(CvvField);
		CvvField.setColumns(10);
		
		FechaField = new JTextField();
		FechaField.setBounds(281, 167, 96, 18);
		getContentPane().add(FechaField);
		FechaField.setColumns(10);
		
		JLabel TitularLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.titular"));
		TitularLabel.setBounds(63, 40, 314, 12);
		getContentPane().add(TitularLabel);
		
		JLabel NumeroLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.numero"));
		NumeroLabel.setBounds(63, 96, 314, 12);
		getContentPane().add(NumeroLabel);
		
		JLabel CvvLabel = new JLabel("CVV:");
		CvvLabel.setBounds(63, 155, 44, 12);
		getContentPane().add(CvvLabel);
		
		JLabel FechaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.fecha"));
		FechaLabel.setBounds(282, 155, 116, 12);
		getContentPane().add(FechaLabel);
		
		JButton btnCupon = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.BotonCupon"));
		btnCupon.setBounds(63, 250, 150, 24);
		getContentPane().add(btnCupon);
		btnCupon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFrame cuponVentana = new AplicarCuponGUI(PagoGUI.this);
		        cuponVentana.setVisible(true);
		    }
		});
		
	}
}

package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import dataAccess.*;
import domain.*;
import businessLogic.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class RegisterGUI extends JFrame {
	private JTextField correoField;
	private JTextField nombreField;
	private JTextField pswField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public RegisterGUI(MainGUI ventanaPrincipal) {
		this.setSize(370, 290);
		getContentPane().setLayout(null);
		
		JLabel correoLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.correoLabel"));
		correoLabel.setBounds(47, 20, 164, 12);
		getContentPane().add(correoLabel);
		
		JLabel nombreLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.nombreLabel"));
		nombreLabel.setBounds(47, 70, 189, 12);
		getContentPane().add(nombreLabel);
		
		JLabel contraseñaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.contraseñaLabel"));
		contraseñaLabel.setBounds(47, 118, 164, 12);
		getContentPane().add(contraseñaLabel);
		
		correoField = new JTextField();
		correoField.setBounds(42, 42, 194, 18);
		getContentPane().add(correoField);
		correoField.setColumns(10);
		
		nombreField = new JTextField();
		nombreField.setBounds(47, 90, 190, 18);
		getContentPane().add(nombreField);
		nombreField.setColumns(10);
		
		pswField = new JTextField();
		pswField.setBounds(47, 146, 189, 18);
		getContentPane().add(pswField);
		pswField.setColumns(10);
		
		JRadioButton seller_t = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.seller_t"));
		seller_t.setSelected(true);
		buttonGroup.add(seller_t);
		seller_t.setBounds(47, 196, 102, 20);
		getContentPane().add(seller_t);
		
		
		JRadioButton seller_f = new JRadioButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.seller_F"));
		buttonGroup.add(seller_f);
		seller_f.setBounds(180, 196, 102, 20);
		getContentPane().add(seller_f);
		
		JLabel errorLabel = new JLabel(""); 
		errorLabel.setBounds(86, 231, 134, 12);
		getContentPane().add(errorLabel);
		
		JButton registerBt = new JButton(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.registerBt")); 
		registerBt.setBounds(105, 170, 84, 20);
		getContentPane().add(registerBt);
		registerBt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String correo= correoField.getText();
				String nombre= nombreField.getText();
				String psw= pswField.getText();
				boolean vendedor=seller_f.isSelected();
				BLFacade facade = MainGUI.getBusinessLogic();
				String formatoMail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
			  if(!correo.matches(formatoMail)) {
				  
					errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.errorLabel"));
				  
				}
				else if(facade.obtUser(correo) != 0) {
					errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.registrado"));
				}
				else {
					if(vendedor) {
						facade.Registro(correo, psw, nombre, 1);
					}
					else {
						facade.Registro(correo, psw, nombre, 2);
					}
					//MainGUI.mode = seller_f.isSelected();
					ventanaPrincipal.bLogin.setEnabled(false);
					ventanaPrincipal.bLogin.setVisible(false);
					ventanaPrincipal.bRegist.setEnabled(false);
					ventanaPrincipal.bRegist.setVisible(false);
					ventanaPrincipal.loged.setText(correo);
					ventanaPrincipal.jButtonCreateQuery.setEnabled(vendedor);
					//ventanaPrincipal.jButtonQueryQueries.setEnabled(!vendedor);
					ventanaPrincipal.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+": " + correo);
					errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.bienvenida")+" "+ nombre);
					Timer timer = new Timer(1000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();
				}
			}
			});

	
	}
}


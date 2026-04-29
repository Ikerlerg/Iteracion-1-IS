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

public class CambiarContraseñaGUI extends JFrame{
	private JPasswordField passwordField;
	private String userMail;
	private JPasswordField confirmationField;
	private static final long serialVersionUID = 1L;

	public CambiarContraseñaGUI(String mail) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.userMail=mail;
		this.getContentPane().setLayout(null);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.cambiar")+": "+userMail);

		getContentPane().setLayout(null);
		this.setSize(455, 210);
		passwordField = new JPasswordField();
		passwordField.setBounds(87, 82, 266, 18);
		getContentPane().add(passwordField);
		
		JLabel jLabelConfirmation = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CambiarContraseñaGUI.labelConfirmation"));
		jLabelConfirmation.setBounds(77, 61, 172, 12);
		getContentPane().add(jLabelConfirmation);
		
		JLabel jLabelPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CambiarContraseñaGUI.labelPassword"));
		jLabelPassword.setBounds(77, 11, 169, 12);
		getContentPane().add(jLabelPassword);
		
		JLabel errorLabel = new JLabel(""); 
		errorLabel.setBounds(54, 111, 338, 12);
		getContentPane().add(errorLabel);
		
		JButton btnCambio = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CambiarContraseñaGUI.cambio"));
		btnCambio.setBounds(127, 135, 169, 25);
		getContentPane().add(btnCambio);
		
		confirmationField = new JPasswordField();
		confirmationField.setBounds(87, 34, 266, 18);
		getContentPane().add(confirmationField);
		btnCambio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pswd= passwordField.getText();
				String conf= confirmationField.getText();
				BLFacade facade = MainGUI.getBusinessLogic();
				
				
				if(!pswd.equals(conf)) {
			        errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CambiarContraseñaGUI.FormatoError"));
			    }
			   
			    else if(facade.buscarContraseña(userMail ,conf) == false){ 
			        errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CambiarContraseñaGUI.UsuarioError"));
			    }
			    else {
			    	errorLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("CambiarContraseñaGUI.CambioConfirmado"));
			    }
			
			}
			
		});
		

	}

}

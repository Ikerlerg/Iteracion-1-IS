package gui;

import javax.swing.JFrame;
import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;
import dataAccess.*;
import domain.*;
import businessLogic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class LoginGUI extends JFrame{
	private JPasswordField passwordField;
	private JTextField mailField;
	public LoginGUI(MainGUI ventanaPrincipal) {
		getContentPane().setLayout(null);
		this.setSize(455, 210);
		passwordField = new JPasswordField();
		passwordField.setBounds(77, 82, 266, 27);
		getContentPane().add(passwordField);
	
		mailField = new JTextField();
		mailField.setBounds(77, 32, 266, 18);
		getContentPane().add(mailField);
		mailField.setColumns(10);
		
		JLabel labelPass = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.labelPass"));
		labelPass.setBounds(105, 60, 172, 12);
		getContentPane().add(labelPass);
		
		JLabel labelMail = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.labelMail"));
		labelMail.setBounds(105, 10, 169, 12);
		getContentPane().add(labelMail);
		
		JLabel Errorlabel = new JLabel(""); 
		Errorlabel.setBounds(87, 119, 290, 12);
		getContentPane().add(Errorlabel);
		
		JButton login = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.login"));
		login.setBounds(127, 133, 169, 20);
		getContentPane().add(login);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String correo= mailField.getText();
				String pswd= passwordField.getText();
				BLFacade facade = MainGUI.getBusinessLogic(); 
				//!correo.contains("@")||!correo.contains(".")||correo.indexOf(".")<correo.indexOf("@") + 2||correo.length()-correo.replace("@", "").length() != 1)
			if(!correo.contains("@gmail.com")) {
			        Errorlabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.FormatoError"));
			    }
			   
			    else if(facade.Login(correo, pswd) == null){ 

			        Errorlabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UsuarioError"));
			    }
			    else {
			        ventanaPrincipal.log=facade.Login(correo, pswd);
			        ventanaPrincipal.bLogin.setEnabled(false);
			        ventanaPrincipal.bLogin.setVisible(false);
			        ventanaPrincipal.bRegist.setEnabled(false);
			        ventanaPrincipal.bRegist.setVisible(false);
			        ventanaPrincipal.jButtonCreateQuery.setEnabled(facade.Login(correo, pswd).getTipo()==1||facade.Login(correo, pswd).getTipo()==-1);//activar si el usuario es vendedor o admin
			        ventanaPrincipal.jButtonQueryQueries.setEnabled(facade.Login(correo, pswd).getTipo()==2||facade.Login(correo, pswd).getTipo()==-1);//activar si el usuario es comprador o admin
			        ventanaPrincipal.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+": " + correo);
			        ventanaPrincipal.loged.setText(facade.Login(correo, pswd).getEmail());
			        Errorlabel.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.bienvenida")+ " " + facade.Login(correo, pswd).getName()); 
			        //cerrar ventana 
			        dispose();
			    }
			
			}
			
		});
		

	}

}

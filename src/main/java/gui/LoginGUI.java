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

public class LoginGUI extends JFrame{
	private JPasswordField passwordField;
	private JTextField mailField;

	public LoginGUI() {
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
				User us = new User();
				String correo= mailField.getText();
				String pswd= passwordField.getText();
				BLFacade facade = MainGUI.getBusinessLogic(); 
				String formatoMail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
				if(!correo.trim().matches(formatoMail)) {
			        Errorlabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.FormatoError"));
			    }
			   
			    else if(facade.Login(correo, pswd) == null){ 

			        Errorlabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.UsuarioError"));
			    }
			    else {
			    	us = facade.Login(correo, pswd);
			    	MainGUI.logEmail = correo;
			    	MainGUI ventanaPrincipal = new MainGUI();
			    	ventanaPrincipal.actualizarEstadoUsuario(correo);
			    	//ventanaPrincipal.log=facade.Login(correo, pswd);
			        /*ventanaPrincipal.bLogin.setEnabled(false);
			        ventanaPrincipal.bLogin.setVisible(false);
			        ventanaPrincipal.bRegist.setEnabled(false);
			        ventanaPrincipal.bRegist.setVisible(false);*/
			        ventanaPrincipal.jButtonCreateQuery.setEnabled(facade.Login(correo, pswd).getTipo()==1||facade.Login(correo, pswd).getTipo()==-1);//activar si el usuario es vendedor o admin
			        //ventanaPrincipal.jButtonQueryQueries.setEnabled(facade.Login(correo, pswd).getTipo()==2||facade.Login(correo, pswd).getTipo()==-1);//activar si el usuario es comprador o admin
			        ventanaPrincipal.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+": " + us.getName());
			        ventanaPrincipal.loged.setText(us.getName());
			        //ventanaPrincipal.close.setEnabled(true);
			        Errorlabel.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.bienvenida")+ " " + facade.Login(correo, pswd).getName()); 
			        ventanaPrincipal.setVisible(true);
			        //cerrar ventana 
					Timer timer = new Timer(1000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();
			    }
			
			}
			
		});
		

	}

}

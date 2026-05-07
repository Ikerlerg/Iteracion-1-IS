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

public class BorrarCuentaGUI extends JFrame{
	private JFrame uGUI;
	private static final long serialVersionUID = 1L;
	private JFrame thisFrame;

	public BorrarCuentaGUI(String mail, MainGUI mGUI, JFrame uGUI) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.uGUI = uGUI;
		thisFrame = this;
		getContentPane().setLayout(null);
		this.setSize(455, 210);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.cambiar")+": "+mail);
		
		JLabel jLabelText = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BorrarCuentaGUI.texto"));
		jLabelText.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelText.setBounds(40, 42, 359, 12);
		getContentPane().add(jLabelText);
		
		JButton btnAccept = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BorrarCuentaGUI.aceptar"));
		btnAccept.setBounds(40, 87, 169, 20);
		getContentPane().add(btnAccept);
		
		JButton btnCancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BorrarCuentaGUI.cancelar"));
		btnCancel.setBounds(230, 87, 169, 20);
		getContentPane().add(btnCancel);
		
		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String correo= mail;
				BLFacade facade = MainGUI.getBusinessLogic();
				
				mGUI.close.setVisible(false);
				mGUI.actualizarEstadoUsuario("Sin usuario");
				mGUI.actualizarFotoPerfil();
				//.loged.setText("Sin usuario");
				if(uGUI != null) {
					uGUI.dispose();
				}
				facade.eliminarCuenta(correo);
				dispose();
			}	
		});
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}	
		});

	}
}

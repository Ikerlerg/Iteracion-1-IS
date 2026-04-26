package gui;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;

import java.awt.Panel;
import javax.swing.JComboBox;

public class PrincipalGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	public PrincipalGUI() {
		setTitle("Market");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton bGuest = new JButton();
		bGuest.setBounds(59, 210, 315, 29);
		bGuest.setText(ResourceBundle.getBundle("Etiquetas").getString("Principal.Guest"));
		contentPane.add(bGuest);
		
		JButton bLogin = new JButton();
		bLogin.setBounds(126, 118, 178, 29);
		contentPane.add(bLogin);
		bLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
		
		JButton bRegistrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		bRegistrar.setBounds(126, 153, 178, 29);
		contentPane.add(bRegistrar);
		
		//Insercción de imagen
		ImageIcon im = new ImageIcon("icon.png");
		Image escalada = im.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(escalada);
		JLabel panel = new JLabel(icon);
		panel.setBounds(174, 32, 75, 75);
		contentPane.add(panel);
		
		//Lista del idioma
		String[] idi = {"es","eus","en"};
		JComboBox Idioma = new JComboBox(idi);
		Idioma.setToolTipText(ResourceBundle.getBundle("Etiquetas").getString("PrincipalGUI.Idioma")); 
		Idioma.setBounds(369, 11, 55, 20);
		contentPane.add(Idioma);
		

		
		
		//Abrir ventana de inicio
		bLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI ventanaLogin = new LoginGUI();
				ventanaLogin.setVisible(true);
				Timer timer = new Timer(1000, evt -> dispose());
				timer.setRepeats(false);
				timer.start();
			}
		});
		
		//invocación de la ventana de Registro
		bRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI ventanaRegister = new RegisterGUI();
				ventanaRegister.setVisible(true); 
				Timer timer = new Timer(1000, evt -> dispose());
				timer.setRepeats(false);
				timer.start();
			}
		});
		//Modo Invitado
		bGuest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainGUI ventanaLogin = new MainGUI();
				ventanaLogin.setVisible(true);
				ventanaLogin.bLogin.setVisible(true);
				ventanaLogin.bRegist.setVisible(true);
				Timer timer = new Timer(1000, evt -> dispose());
				timer.setRepeats(false);
				timer.start();
			}
		});
			
	}
}

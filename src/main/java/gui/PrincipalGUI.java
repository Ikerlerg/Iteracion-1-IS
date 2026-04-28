package gui;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

public class PrincipalGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;    
    
    private JButton bGuest;
    private JButton bLogin;
    private JButton bRegistrar;
    private JComboBox<String> Idioma;

		
	

    public PrincipalGUI() {
        setTitle("Market");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        bGuest = new JButton();
        bGuest.setBounds(59, 210, 315, 29);
        contentPane.add(bGuest);
        
        bLogin = new JButton();
        bLogin.setBounds(126, 118, 178, 29);
        contentPane.add(bLogin);
        
        bRegistrar = new JButton();
        bRegistrar.setBounds(126, 153, 178, 29);
        contentPane.add(bRegistrar);
        
        // Inserción de imagen
        ImageIcon im = new ImageIcon("icon.png");
        Image escalada = im.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(escalada);
        JLabel panel = new JLabel(icon);
        panel.setBounds(174, 32, 75, 75);
        contentPane.add(panel);
        
        // Lista del idioma
        String[] idi = {"es","eus","en"};
        Idioma = new JComboBox<>(idi);
        Idioma.setBounds(369, 11, 55, 20);
        contentPane.add(Idioma);
        
        // Cargamos los textos por primera vez
        paintAgain();
        
      //Abrir ventana de inicio
      		bLogin.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent e) {
      				LoginGUI ventanaLogin = new LoginGUI();
      				ventanaLogin.setVisible(true);
      				Timer timer = new Timer(1000, evt -> dispose());
      				//timer.setRepeats(false);
      				//timer.start();
      			}
      		});
      		
      		//invocación de la ventana de Registro
      		bRegistrar.addActionListener(new ActionListener() {
      			public void actionPerformed(ActionEvent e) {
      				RegisterGUI ventanaRegister = new RegisterGUI();
      				ventanaRegister.setVisible(true); 
      				Timer timer = new Timer(1000, evt -> dispose());
      				//timer.setRepeats(false);
      				//timer.start();
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
        // Cambio de idioma
        Idioma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(Idioma.getSelectedIndex() == 0) {
                    Locale.setDefault(new Locale("es"));
                } else if(Idioma.getSelectedIndex() == 1) {
                    Locale.setDefault(new Locale("eus"));
                } else if(Idioma.getSelectedIndex() == 2) {
                    Locale.setDefault(new Locale("en"));
                }
                paintAgain(); 
            }
        });
    }

    public void paintAgain() {
        ResourceBundle bundle = ResourceBundle.getBundle("Etiquetas");
        
        bGuest.setText(bundle.getString("Principal.Guest"));
        bLogin.setText(bundle.getString("MainGUI.Login"));
        bRegistrar.setText(bundle.getString("MainGUI.Register"));
        Idioma.setToolTipText(bundle.getString("PrincipalGUI.Idioma"));
    }
}


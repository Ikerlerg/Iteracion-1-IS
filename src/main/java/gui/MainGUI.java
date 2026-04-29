package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import businessLogic.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import domain.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame {
	
	private String sellerMail = "Sin usuario";
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	protected JButton jButtonCreateQuery = null;
	protected JButton jButtonQueryQueries = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade facade){
		appFacadeInterface=facade;
	}
	protected JLabel jLabelSelectOption;
	//private JRadioButton rdbtnNewRadioButton;
	//private JRadioButton rdbtnNewRadioButton_1;
	//private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	//private final ButtonGroup buttonGroup = new ButtonGroup();
	protected final JButton bLogin = new JButton(); 
	protected final JButton bRegist = new JButton();
	protected JButton close = new JButton(); 
	
	public static String logEmail = "Sin usuario";
	protected JLabel loged;
	protected JButton adminButton = new JButton();
	protected JButton aceptar_visuali = new JButton(""); 
	//variable para controlar si soy vendedor o comprador
	 //(0=nada,1=vendedor,2=comprador)
	private int mode = 0;

	public MainGUI() {
		this("");
	}

	public MainGUI(String mail) {
		super();
		
		this.sellerMail=mail;
		this.setSize(495, 290);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setBounds(0, 0, 481, 63);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.setBounds(188, 5, 78, 21);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				paintAgain();				}
		});

		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.setBounds(6, 5, 86, 21);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.setBounds(94, 5, 86, 21);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);*/
	
		panel = new JPanel();
		panel.setBounds(0, 189, 481, 63);
		panel.setLayout(null);
		//panel.add(rdbtnNewRadioButton_1);
		//panel.add(rdbtnNewRadioButton_2);
		//panel.add(rdbtnNewRadioButton);
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setBounds(0, 63, 481, 63);
		jButtonCreateQuery.setEnabled(false);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateSaleGUI(MainGUI.logEmail);
				a.setVisible(true);
			}
		});
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setBounds(0, 126, 481, 63);
		jButtonQueryQueries.setEnabled(true);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new QuerySalesGUI();
				a.setVisible(true);
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonCreateQuery);
		jContentPane.add(jButtonQueryQueries);
		jContentPane.add(panel);
		
		bLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaLogin = new LoginGUI();
				ventanaLogin.setVisible(true);
			}
		});
		bLogin.setBounds(328, 5, 143, 21);
		bLogin.setHorizontalAlignment(SwingConstants.LEADING);
		bLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login")); 
		panel.add(bLogin);
		bLogin.setVisible(false);
		
		bRegist.setHorizontalAlignment(SwingConstants.LEADING);
		bRegist.setBounds(328, 30, 143, 21);
		bRegist.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		panel.add(bRegist);
		bRegist.setVisible(false);
		bRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaRegister = new RegisterGUI();
				ventanaRegister.setVisible(true);
			}
		});
		
		aceptar_visuali.setBounds(276, 33, 186, 22);
		panel.add(aceptar_visuali);
		
		adminButton.setBounds(276, 5, 186, 20);
		panel.add(adminButton);
		

		String[] idi = {"es","eus","en"};
		JComboBox IdiomaBox = new JComboBox<>(idi);
		IdiomaBox.setBounds(10, 11, 64, 22);
		panel.add(IdiomaBox);
		
		// Cambio de idioma
        IdiomaBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(IdiomaBox.getSelectedIndex() == 0) {
                    Locale.setDefault(new Locale("es"));
                } else if(IdiomaBox.getSelectedIndex() == 1) {
                    Locale.setDefault(new Locale("eus"));
                } else if(IdiomaBox.getSelectedIndex() == 2) {
                    Locale.setDefault(new Locale("en"));
                }
                paintAgain(); 
            }
        });
		adminButton.setVisible(false);
		
		aceptar_visuali.setVisible(false);
		
		aceptar_visuali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==2) {
					JFrame ventanaAceptar= new AceptarGUI(MainGUI.this, MainGUI.logEmail);
					ventanaAceptar.setVisible(true);
				}else if(mode==1||mode==-1) {
					JFrame ventanaVisualizar= new VisualizarGUI(MainGUI.this, MainGUI.logEmail);
					ventanaVisualizar.setVisible(true);
				}
			}
		});
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==-1) {
					JFrame ventanaAceptar= new AceptarGUI(MainGUI.this, MainGUI.logEmail);
					ventanaAceptar.setVisible(true);
				}
			}
		});
		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle"));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
		
		close.setBounds(307, 22, 153, 20);
		jContentPane.add(close);
		close.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.close"));
		

		loged = new JLabel("Sin usuario");
		loged.setBounds(10, 0, 122, 24);
		jContentPane.add(loged);
		close.setVisible(false);
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarEstadoUsuario("Sin usuario");				
			}
		});
		
		
		//Llamada al paint para que se carguen por primera vez todo los botones.
		paintAgain();
		
	}
	
	// Metodo para gestionar los estados 
	public void actualizarEstadoUsuario(String email) {
		
		if(email.equals("Sin usuario")) {
			jButtonCreateQuery.setEnabled(false);
			bLogin.setEnabled(true);
			bLogin.setVisible(true);
			bRegist.setEnabled(true);
			bRegist.setVisible(true);
			adminButton.setVisible(false);
			aceptar_visuali.setVisible(false);
			close.setVisible(false);
			this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle"));
		}
		else {
			try {
				appFacadeInterface = getBusinessLogic();
				mode = appFacadeInterface.obtUser(email);
				this.sellerMail=email;
				
				bLogin.setVisible(false);
				bRegist.setVisible(false);
				close.setVisible(true);

				if (mode == 1) {
					aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
					aceptar_visuali.setVisible(true);
				} else if (mode == 2) {
					aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
					aceptar_visuali.setVisible(true);
				} 
				else if(mode==-1) {
					aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
					aceptar_visuali.setVisible(true);
					adminButton.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
					adminButton.setVisible(true);
				}
				
				this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+sellerMail);
				panel.revalidate();
				panel.repaint();
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		bLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
	    bRegist.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
	    close.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.close"));
	    
	}
}
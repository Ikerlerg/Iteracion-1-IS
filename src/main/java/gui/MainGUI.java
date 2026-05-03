package gui;

import javax.imageio.ImageIO;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import businessLogic.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.Locale;
import java.util.ResourceBundle;
import domain.*;
import java.awt.event.ActionListener;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private JPanel panel_1;
	//private final ButtonGroup buttonGroup = new ButtonGroup();
	protected final JButton bLogin = new JButton(); 
	protected final JButton bRegist = new JButton();
	protected JButton close = new JButton(); 
	
	//public static String logEmail = "Sin usuario";
	protected JLabel loged;
	protected JButton adminButton = new JButton();
	protected JButton aceptar_visuali = new JButton(""); 
	//variable para controlar si soy vendedor o comprador
	 //(0=nada,1=vendedor,2=comprador)
	private int mode = 0;
	
	/*public MainGUI() {
	}*/

	public MainGUI(String mail, PrincipalGUI ventPadre) {
		super();
		
		ventPadre.setVisible(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.sellerMail=mail;
		this.setSize(675, 429);
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setBounds(72, 9, 429, 63);
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
		panel.setBounds(0, 231, 654, 159);
		panel.setLayout(null);
		//panel.add(rdbtnNewRadioButton_1);
		//panel.add(rdbtnNewRadioButton_2);
		//panel.add(rdbtnNewRadioButton);
		
		panel_1 = new JPanel();
		panel_1.setBounds(540, 40, 86, 86);
		panel_1.setLayout(new java.awt.BorderLayout());
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setBounds(20, 83, 481, 63);
		jButtonCreateQuery.setEnabled(false);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateSaleGUI(sellerMail);
				a.setVisible(true);
			}
		});
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setBounds(20, 157, 481, 63);
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
		jContentPane.add(panel_1);
		bRegist.setBounds(513, 0, 143, 21);
		bRegist.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		panel.add(bRegist);
		bRegist.setVisible(false);
		bRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaRegister = new RegisterGUI(ventPadre);
				ventanaRegister.setVisible(true);
				MainGUI.this.setVisible(false);
			}
		});
		
		
		aceptar_visuali.setBounds(20, 0, 481, 63);
		panel.add(aceptar_visuali);
		
		adminButton.setBounds(20, 74, 481, 63);
		panel.add(adminButton);
		
		try {
		    BLFacade pFacade = getBusinessLogic();
		    String fotoGuardadaBase64 = pFacade.obtenerImagen(mail); 

		    if (fotoGuardadaBase64 != null && !fotoGuardadaBase64.isEmpty()) {
		        ImageIcon icon = decodeBase64ToImageIcon(fotoGuardadaBase64);
		        panel_1.add(new JLabel(icon));
		    } else {
		        ImageIcon im = new ImageIcon("userDefault.png");
		        
		        Image escalada = im.getImage().getScaledInstance(86,86,Image.SCALE_SMOOTH);
		        ImageIcon iconDefault = new ImageIcon(escalada);
		        
		        JLabel panelImagenDefault = new JLabel(iconDefault);
		        
		        panel_1.add(panelImagenDefault);
		    }
		    panel_1.revalidate();
		    panel_1.repaint();
		    	
		} catch (Exception ex) {
		    ex.printStackTrace();
		}

		String[] idi = {"es","eus","en"};
		close.setBounds(513, 32, 143, 20);
		panel.add(close);
		close.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.close"));
		close.setVisible(false);
		
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//actualizarEstadoUsuario("Sin usuario");
				ventPadre.setVisible(true);
  				Timer timer = new Timer(200, evt -> dispose());
  				timer.setRepeats(false);
  				timer.start();		
			}
		});
		JComboBox IdiomaBox = new JComboBox<>(idi);
		IdiomaBox.setBounds(10, 9, 64, 22);
		jContentPane.add(IdiomaBox);
		//Sincro de Idiomas
		String idiomaActual = Locale.getDefault().getLanguage();
		if (idiomaActual.equals("eus")) {
			IdiomaBox.setSelectedIndex(1);
		} else if (idiomaActual.equals("en")) {
			IdiomaBox.setSelectedIndex(2);
		} else {
			IdiomaBox.setSelectedIndex(0);
		}
		adminButton.setVisible(false);
		
		aceptar_visuali.setVisible(false);
		
		aceptar_visuali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==2) {
					JFrame ventanaAceptar= new AceptarGUI(MainGUI.this, sellerMail);
					ventanaAceptar.setVisible(true);
				}else if(mode==1||mode==-1) {
					JFrame ventanaVisualizar= new VisualizarGUI(MainGUI.this, sellerMail);
					ventanaVisualizar.setVisible(true);
				}
			}
		});
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==-1) {
					JFrame ventanaAceptar= new AceptarGUI(MainGUI.this, sellerMail);
					ventanaAceptar.setVisible(true);
				}
			}
		});
		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle"));
		

		loged = new JLabel("Sin usuario");
		loged.setHorizontalAlignment(SwingConstants.CENTER);
		loged.setBounds(511, 9, 143, 24);
		loged.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loged.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (!sellerMail.equals("Sin usuario") && !sellerMail.equals("admin@gmail.com")) {
		        	JFrame a = new UsuarioGUI(sellerMail, MainGUI.this);
					a.setVisible(true);
		        }
		    }
		});
		
		jContentPane.add(loged);
		bLogin.setBounds(513, 199, 143, 21);
		jContentPane.add(bLogin);
		bLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
		bLogin.setVisible(false);
		bLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaLogin = new LoginGUI(ventPadre);
				ventanaLogin.setVisible(true);
				MainGUI.this.setVisible(false);
			}
		});
		
		
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
			loged.setText("Sin usuario");
			this.sellerMail = "Sin usuario";
			this.setSize(675, 336);
		}
		else {
			try {
				appFacadeInterface = getBusinessLogic();
				mode = appFacadeInterface.obtUser(email);
				this.sellerMail=email;
				
				bLogin.setVisible(false);
				bRegist.setVisible(false);
				close.setVisible(true);

				jButtonCreateQuery.setEnabled(true);
				
				if (mode == 1) {
					aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
					aceptar_visuali.setVisible(true);
					adminButton.setVisible(false);
					this.setSize(675, 336);
				} else if (mode == 2) {
					aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
					aceptar_visuali.setVisible(true);
					adminButton.setVisible(false);
					this.setSize(675, 336);
				} 
				else if(mode==-1) {
					aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
					aceptar_visuali.setVisible(true);
					adminButton.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
					adminButton.setVisible(true);
					this.setSize(675, 429);
				}
				this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+sellerMail);
				
				loged.setText(sellerMail);
				
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
	
	public ImageIcon decodeBase64ToImageIcon(String base64String) {
	    try {
	        byte[] bytes = Base64.getDecoder().decode(base64String);
	        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(bytes);
	        BufferedImage img = ImageIO.read(bais);
	        return new ImageIcon(img);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}

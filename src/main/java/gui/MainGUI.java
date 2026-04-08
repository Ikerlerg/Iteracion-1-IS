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
	
	private String sellerMail = "default";
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
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	protected final JButton bLogin = new JButton(); 
	protected final JButton bRegist = new JButton();
	protected JButton close = new JButton(); 
	
	protected User log=null;
	protected JLabel loged;
	protected JButton adminButton = new JButton();
	protected JButton aceptar_visuali = new JButton(""); 
	//variable para controlar si soy vendedor o comprador
	 //(0=nada,1=vendedor,2=comprador)
	private int mode = 0;
	/**
	 * This is the default constructor
	 */

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
		
		rdbtnNewRadioButton = new JRadioButton("English");
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
		buttonGroup.add(rdbtnNewRadioButton_2);
	
		panel = new JPanel();
		panel.setBounds(0, 189, 481, 63);
		panel.setLayout(null);
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setBounds(0, 63, 481, 63);
		jButtonCreateQuery.setEnabled(false);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateSaleGUI(loged.getText());
				a.setVisible(true);
				
			}
		});
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setBounds(0, 126, 481, 63);
		jButtonQueryQueries.setEnabled(false);
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
		//Invocación de la ventana de Login
		bLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaLogin = new LoginGUI(MainGUI.this);
				ventanaLogin.setVisible(true);
				//System.out.println(mode);

			}
		});
		bLogin.setBounds(328, 5, 143, 21);
		bLogin.setHorizontalAlignment(SwingConstants.LEADING);
		bLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login")); //
		panel.add(bLogin);
		
		
		bRegist.setHorizontalAlignment(SwingConstants.LEADING);
		bRegist.setBounds(328, 30, 143, 21);
		bRegist.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		panel.add(bRegist);
		//invocación de la ventana de Registro
		bRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaRegister = new RegisterGUI(MainGUI.this);
				ventanaRegister.setVisible(true);
				//System.out.println(mode);

			}
		});
		loged = new JLabel("Sin usuario"); 
		loged.setBounds(0, 38, 92, 24);
		panel.add(loged);
		
		
		
		
		aceptar_visuali.setBounds(276, 33, 186, 22);
		panel.add(aceptar_visuali);
		
		
		adminButton.setBounds(276, 5, 186, 20);
		panel.add(adminButton);
		adminButton.setVisible(false);
		
		
		
		aceptar_visuali.setVisible(false);
		//Cambiar de modo, una vez se cambia ofrecemos la opción correspondiente
		loged.addPropertyChangeListener("text", new java.beans.PropertyChangeListener() {
		    public void propertyChange(java.beans.PropertyChangeEvent evt) {
		        
		        // Este código se ejecuta AUTOMÁTICAMENTE solo cuando el texto cambia
		        String nuevoTexto = loged.getText();
		        
		        if (nuevoTexto != null &&nuevoTexto!="Sin Usuario"&& !nuevoTexto.isEmpty()) {
		            try {
		                // Buscamos al usuario en la base de datos
		                appFacadeInterface = getBusinessLogic();
		                mode = appFacadeInterface.obtUser(nuevoTexto);
		                
		                // Mostramos el botón según el modo
		                if (mode == 1) {
		                    aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
		                    aceptar_visuali.setVisible(true);
		                    close.setVisible(true);
		                } else if (mode == 2) {
		                    aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
		                    aceptar_visuali.setVisible(true);
		                    close.setVisible(true);
		                    
		                } 
		                else if(mode==-1) {
		                    aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
		                    aceptar_visuali.setVisible(true);
		                    adminButton.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
		                    adminButton.setVisible(true);
		                    close.setVisible(true);
		                }
		                else {
		                    aceptar_visuali.setVisible(false);
		                }
		                
		                // Actualizamos la pantalla
		                panel.revalidate();
		                panel.repaint();
		                
		            } catch (Exception ex) {
		                ex.printStackTrace();
		            }
		        }
		    }
		});
		//Llamada a la interfaz de visualizar y aceptar
		aceptar_visuali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==2) {
					JFrame ventanaAceptar= new AceptarGUI(MainGUI.this, loged.getText().toString());
					ventanaAceptar.setVisible(true);
				}else if(mode==1||mode==-1) {
					JFrame ventanaVisualizar= new VisualizarGUI(MainGUI.this, loged.getText().toString());
					ventanaVisualizar.setVisible(true);
				}
				
				//System.out.println(mode);

			}
		});
		adminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mode==-1) {
					JFrame ventanaAceptar= new AceptarGUI(MainGUI.this, loged.getText().toString());
					ventanaAceptar.setVisible(true);
				}
				
				//System.out.println(mode);

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
		close.setVisible(false);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close.setVisible(false);
				loged.setText("Sin usuario");
				jButtonCreateQuery.setEnabled(false);
				jButtonQueryQueries.setEnabled(false);
				bLogin.setEnabled(true);
				bLogin.setVisible(true);
				bRegist.setEnabled(true);
				bRegist.setVisible(true);
				adminButton.setVisible(false);
				aceptar_visuali.setVisible(false);
				MainGUI.this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle"));
				
				
			}});
		
		}
	


	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		bLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
	    bRegist.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
	    
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+sellerMail);
		int tipo = appFacadeInterface.obtUser(loged.getText());
		 if (tipo == 1) {
             aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
          
         } else if (tipo == 2) {
             aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
            
             
         } 
         else if(tipo==-1) {
             aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Visualizar"));
           
             adminButton.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Aceptar"));
             
         }
	   
	    	
	    
	}
} // @jve:decl-index=0:visual-constraint="0,0"


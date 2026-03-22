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
	
	protected User log=null;
	protected JLabel loged;
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
		panel.setLayout(null);
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setEnabled(false);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateSaleGUI(sellerMail);
				a.setVisible(true);
				
			}
		});
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setEnabled(false);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new QuerySalesGUI();

				a.setVisible(true);
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(4, 1, 0, 0));
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
		bLogin.setText("Iniciar sesión");
		panel.add(bLogin);
		
		
		bRegist.setHorizontalAlignment(SwingConstants.LEADING);
		bRegist.setBounds(328, 30, 143, 21);
		bRegist.setText("Registrar usuario");
		panel.add(bRegist);
		//invocación de la ventana de Registro
		bRegist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame ventanaRegister = new RegisterGUI(MainGUI.this);
				ventanaRegister.setVisible(true);
				//System.out.println(mode);

			}
		});
		loged = new JLabel(""); 
		loged.setBounds(0, 38, 92, 24);
		panel.add(loged);
		
		
		
		JButton aceptar_visuali = new JButton(""); 
		aceptar_visuali.setBounds(198, 33, 264, 22);
		panel.add(aceptar_visuali);
		aceptar_visuali.setVisible(false);
		//Cambiar de modo, una vez se cambia ofrecemos la opción correspondiente
		loged.addPropertyChangeListener("text", new java.beans.PropertyChangeListener() {
		    public void propertyChange(java.beans.PropertyChangeEvent evt) {
		        
		        // Este código se ejecuta AUTOMÁTICAMENTE solo cuando el texto cambia
		        String nuevoTexto = loged.getText();
		        
		        if (nuevoTexto != null && !nuevoTexto.isEmpty()) {
		            try {
		                // Buscamos al usuario en la base de datos
		                appFacadeInterface = getBusinessLogic();
		                mode = appFacadeInterface.obtUser(nuevoTexto);
		                
		                // Mostramos el botón según el modo
		                if (mode == 1) {
		                    aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Ofertas"));
		                    aceptar_visuali.setVisible(true);
		                } else if (mode == 2) {
		                    aceptar_visuali.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.aceptar"));
		                    aceptar_visuali.setVisible(true);
		                    
		                } else {
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
				}else if(mode==1) {
					JFrame ventanaVisualizar= new VisualizarGUI(MainGUI.this, loged.getText().toString());
					ventanaVisualizar.setVisible(true);
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
	}
	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		bLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Login"));
	    bRegist.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Register"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+sellerMail);
	}
} // @jve:decl-index=0:visual-constraint="0,0"


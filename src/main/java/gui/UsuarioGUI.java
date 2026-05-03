package gui;

import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.User;


public class UsuarioGUI extends JFrame {
	
    File targetFile;
    BufferedImage targetImg;
    String encodedfile = null;
    
    private JFrame thisFrame;
    public JPanel panel_1;
    
    private static final int baseSize = 86;
	private static final String basePath="src/main/resources/images/";
	private static final long serialVersionUID = 1L;

	private String userMail;  
	
	private final JButton btnReseñas = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.reseñas"));
	private final JButton btnBorrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.borrar"));
	private final JButton btnFoto = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.foto"));

	public UsuarioGUI(String mail, MainGUI mGUI) {
		mGUI.setVisible(false);
		thisFrame=this;
		this.userMail=mail;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.titulo")+": "+this.userMail);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		User usuario = facade.devolverUser(userMail);
		
		JLabel jLabelCorreo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.correo") + usuario.getEmail());
		jLabelCorreo.setBounds(137, 60, 109, 16);
		getContentPane().add(jLabelCorreo);
		
		JButton btnCambiarPassword = new JButton(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.cambiar"));
		btnCambiarPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CambiarContraseñaGUI(userMail);
				a.setVisible(true);        
			}
		});
		btnCambiarPassword.setBounds(26, 173, 535, 29);
		getContentPane().add(btnCambiarPassword);
		
		panel_1 = new JPanel();
		panel_1.setBounds(26, 26, 86, 86);
		getContentPane().add(panel_1);
		
		try {
		    BLFacade pFacade = MainGUI.getBusinessLogic();
		    String fotoGuardadaBase64 = pFacade.obtenerImagen(userMail); 

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
		
		/*
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					BufferedImage img = ImageIO.read(targetFile);
					
				    File outputfile = new File(basePath+targetFile.getName());

				   ImageIO.write(img, "png", outputfile);  // ignore returned boolean
				   System.out.println("file stored "+img);
				} catch(IOException ex) {
				 //System.out.println("Write error for " + outputfile.getPath()  ": " + ex.getMessage());
				  }
				
			}
		});
		
		btnNewButton_2.setBounds(137, 350, 117, 29);
		
		getContentPane().add(btnNewButton_2);
		*/
		JLabel jLabelNombre = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.nombre") + usuario.getName());
		jLabelNombre.setBounds(new Rectangle(6, 24, 92, 20));
		jLabelNombre.setBounds(137, 26, 197, 20);
		getContentPane().add(jLabelNombre);
		
		btnReseñas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new VisualizarReseGUI(userMail, usuario.getTipo());
				a.setVisible(true);
			}
		});
		btnReseñas.setBounds(26, 213, 535, 29);
		getContentPane().add(btnReseñas);
		
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new BorrarCuentaGUI(userMail, mGUI, thisFrame);
				a.setVisible(true);
			}
		});
		btnBorrar.setBounds(26, 253, 535, 29);
		getContentPane().add(btnBorrar);
		
		btnFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
				fileChooser.setFileFilter(filter);
		        int result = fileChooser.showOpenDialog(null);  

		        fileChooser.setBounds(30, 148, 320, 80);

		        if (result == JFileChooser.APPROVE_OPTION) {
		            targetFile = fileChooser.getSelectedFile();
		            try {
		            	BufferedImage originalImg = ImageIO.read(targetFile);
		                targetImg = rescale(originalImg);
		                
		                String fotoBase64 = encodeImageToBase64(targetImg);
		                
		                BLFacade facade = MainGUI.getBusinessLogic();
		                facade.guardarImagen(userMail, fotoBase64);
		                
		                panel_1.removeAll();
                		panel_1.setLayout(new BorderLayout(0, 0));
                		panel_1.add(new JLabel(new ImageIcon(targetImg))); 
                		
                		panel_1.revalidate();
                		panel_1.repaint();
		                //encodeFileToBase64Binary(targetFile);
		            } catch (IOException ex) {
		                //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
		            	ex.printStackTrace();
		            }
		            
		            panel_1.setLayout(new BorderLayout(0, 0));
		            panel_1.add(new JLabel(new ImageIcon(targetImg))); 
		            setVisible(true);

		        }
		        /*
		        JFileChooser fileChooser = new JFileChooser();
        		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
        		fileChooser.setFileFilter(filter);
        		int result = fileChooser.showOpenDialog(null);  

        		if (result == JFileChooser.APPROVE_OPTION) {
            		targetFile = fileChooser.getSelectedFile();

            		try {
                		// 1. Leemos la imagen del archivo y la reescalamos
                		BufferedImage originalImg = ImageIO.read(targetFile);
                		targetImg = rescale(originalImg); // Tu método que la pone a 32x32

                		// 2. La convertimos a String Base64
                		String fotoBase64 = encodeImageToBase64(targetImg);

                		// 3. Guardamos en BD a través de la lógica de negocio
                		BLFacade facade = MainGUI.getBusinessLogic();
                		// OJO: Tendrás que adaptar guardarImagen para que reciba un String
                		facade.guardarImagen(userMail, fotoBase64); 
                
                		// 4. Actualizamos la interfaz
                		panel_1.removeAll();
                		panel_1.setLayout(new BorderLayout(0, 0));
                		panel_1.add(new JLabel(new ImageIcon(targetImg))); 
                		panel_1.revalidate(); // Usar revalidate + repaint asegura que se actualice visualmente
                		panel_1.repaint();

            		} catch (IOException ex) {
                		ex.printStackTrace();
            		}
        		}*/
			}
		});
		btnFoto.setBounds(26, 123, 86, 29);
		getContentPane().add(btnFoto);
		
		JLabel jLabelTipo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UsuarioGUI.tipo")+ usuario.getTipo());
		jLabelTipo.setBounds(137, 96, 109, 16);
		getContentPane().add(jLabelTipo);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		        // Al cerrarse esta ventana, la principal vuelve a ser visible
		        if (mGUI != null) {
		            mGUI.setVisible(true);
		        }
		    }
		});
	}	 

	public BufferedImage rescale(BufferedImage originalImage)
    {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	/*
	public  String encodeFileToBase64Binary(File file){
        try {
            @SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile=new String(Base64.getEncoder().encode(bytes));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }
	*/
	public String encodeImageToBase64(BufferedImage img) {
	    try {
	        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	        ImageIO.write(img, "png", baos);
	        byte[] bytes = baos.toByteArray();
	        return Base64.getEncoder().encodeToString(bytes);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
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

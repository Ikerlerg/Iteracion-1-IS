package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import businessLogic.BLFacade;
import domain.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class ReportarGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Descripcion;
	private JButton fotoBoton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.LoadPicture"));
	/*
	 * Queda por añadir y cargar una foto en los reportes 
	 */
	
	private File targetFile;
	private BufferedImage targetImg;
	private String encodedfile;
	private JPanel panel_1; 
	private int baseSize = 100;

	public ReportarGUI(CrearReseGUI ventanaPadre, String mailComp, String mailVend, Offer productoReporte) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 297);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Description"));
		lblNewLabel.setBounds(50, 44, 100, 16);
		contentPane.add(lblNewLabel);
		
		Descripcion = new JTextField();
		Descripcion.setBounds(9, 70, 298, 129);
		contentPane.add(Descripcion);
		Descripcion.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ReportarGUI.Titulo"));
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 28));
		lblNewLabel_1.setBounds(140, 10, 156, 35);
		contentPane.add(lblNewLabel_1);
		
		JLabel textoError = new JLabel("");
		textoError.setEnabled(false);
		textoError.setToolTipText("");
		textoError.setBounds(22, 234, 370, 16);
		contentPane.add(textoError);
	

		panel_1 = new JPanel();
		panel_1.setBounds(317, 70, baseSize, baseSize);
		contentPane.add(panel_1);

		JButton botonEnviar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ReportarGUI.Enviar"));
		botonEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desc = Descripcion.getText();
				BLFacade bl = MainGUI.getBusinessLogic();
				if(desc.isBlank()) {	
					textoError.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportarGUI.ErrorMotivo"));
				}
				else if(encodedfile==null) {
					textoError.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportarGUI.ErrorFoto"));
				}
				else {

					Reportes reporte = new Reportes(productoReporte, mailComp,desc,encodedfile);
					bl.reportar(reporte);
					textoError.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportarGUI.Exito"));
					Timer timer = new Timer(3000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();
				}
			}
		});
		
		botonEnviar.setVerticalAlignment(SwingConstants.TOP);
		botonEnviar.setBounds(152, 209, 98, 26);
		contentPane.add(botonEnviar);
		
		fotoBoton.setBounds(317, 180, 98, 19);
		contentPane.add(fotoBoton);
		
		fotoBoton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
				fileChooser.setFileFilter(filter);
		        int result = fileChooser.showOpenDialog(null);  

		        fileChooser.setBounds(30, 148, 320, 80);

		        if (result == JFileChooser.APPROVE_OPTION) {
		            targetFile = fileChooser.getSelectedFile();
		            panel_1.removeAll();
		            panel_1.repaint();

		            try {
		                targetImg = rescale(ImageIO.read(targetFile));
		                encodeFileToBase64Binary(targetFile);
		                textoError.setText("");
		            }
		            catch(NullPointerException ex) {
		            	textoError.setForeground(Color.RED);
		            	textoError.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportarGUI.ErrorInvalido"));
		            }
		            catch (IOException ex) {
		            	textoError.setForeground(Color.RED);
		            	textoError.setText(ResourceBundle.getBundle("Etiquetas").getString("ReportarGUI.ErrorLectura"));
		            }
		            
		            panel_1.setLayout(new BorderLayout(0, 0));
		            panel_1.add(new JLabel(new ImageIcon(targetImg))); 
		            panel_1.revalidate();
		            setVisible(true);
		        }
			}
		});
	}
	
	public BufferedImage rescale(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	public String encodeFileToBase64Binary(File file) {
        try {
            @SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile=new String(Base64.getEncoder().encode(bytes));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return encodedfile;
    }
}
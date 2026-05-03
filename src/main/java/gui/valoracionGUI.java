package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class valoracionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Descripcion;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public valoracionGUI(CrearReseGUI ventanaPadre, String mailComp, String mailVend, Offer prodRese) {
		

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JRadioButton radio1 = new JRadioButton("1");
		buttonGroup.add(radio1);
		radio1.setActionCommand("1");
		radio1.setBounds(122, 187, 40, 35);
		contentPane.add(radio1);
		
		JLabel lblNewLabel = new JLabel("Descripción:");
		lblNewLabel.setBounds(50, 44, 100, 16);
		contentPane.add(lblNewLabel);
		
		Descripcion = new JTextField();
		Descripcion.setBounds(60, 72, 321, 107);
		contentPane.add(Descripcion);
		Descripcion.setColumns(10);
		
		JRadioButton radio4 = new JRadioButton("4");
		buttonGroup.add(radio4);
		radio4.setActionCommand("4");
		radio4.setBounds(248, 187, 40, 35);
		contentPane.add(radio4);
		
		JRadioButton radio2 = new JRadioButton("2");
		buttonGroup.add(radio2);
		radio2.setBounds(160, 187, 40, 35);
		radio2.setActionCommand("2");
		contentPane.add(radio2);
		
		JRadioButton radio3 = new JRadioButton("3");
		buttonGroup.add(radio3);
		radio3.setBounds(204, 187, 40, 35);
		radio3.setActionCommand("3");
		contentPane.add(radio3);
		
		JRadioButton radio5 = new JRadioButton("5");
		buttonGroup.add(radio5);
		radio5.setBounds(292, 187, 40, 35);
		radio5.setActionCommand("5");
		contentPane.add(radio5);
		
		JLabel lblNewLabel_1 = new JLabel("Reseña ");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 28));
		lblNewLabel_1.setBounds(160, 0, 108, 35);
		contentPane.add(lblNewLabel_1);
		
		JLabel textoConf = new JLabel("");
		textoConf.setEnabled(false);
		textoConf.setToolTipText("");
		textoConf.setBounds(29, 29, 370, 16);
		contentPane.add(textoConf);

		
		JButton botonEnviar = new JButton("Enviar");
		botonEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String desc = Descripcion.getText();
				BLFacade bl = MainGUI.getBusinessLogic();
				if(buttonGroup.getSelection() != null) {
					String val = buttonGroup.getSelection().getActionCommand();
					Valoraciones valora = new Valoraciones(prodRese,mailVend,mailComp,val,desc);
					bl.publicarValoracion(valora);
					textoConf.setText("Reseña enviada correctamente");
					Timer timer = new Timer(1000, evt -> dispose());
					timer.setRepeats(false);
					timer.start();
				}
				else{
					textoConf.setText("Error: has hecho algo mal");
				}
			}
		});
		botonEnviar.setVerticalAlignment(SwingConstants.TOP);
		botonEnviar.setBounds(170, 223, 98, 26);
		contentPane.add(botonEnviar);
	
	}
}

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

public class ShowReseGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField JTextDescripcion;
	
	public ShowReseGUI(Valoraciones v) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCorreo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowReseGUI.correo")+": "+v.geteVendedor());
		lblCorreo.setBounds(62, 56, 100, 16);
		contentPane.add(lblCorreo);
		
		JLabel lblValoracion = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ShowReseGUI.valoracion")+": "+v.getValoracion());
		lblValoracion.setBounds(283, 56, 100, 16);
		contentPane.add(lblValoracion);
		
		JTextDescripcion = new JTextField();
		JTextDescripcion.setEditable(false);
		JTextDescripcion.setBounds(62, 83, 321, 130);
		JTextDescripcion.setText(v.getDescripcion());
		contentPane.add(JTextDescripcion);
		JTextDescripcion.setColumns(10);

		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setVerticalAlignment(SwingConstants.TOP);
		btnClose.setBounds(170, 224, 98, 26);
		contentPane.add(btnClose);
	}
}

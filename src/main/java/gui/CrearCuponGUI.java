package gui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;

public class CrearCuponGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JTextField txtPorcentaje;
	private JLabel lblMensaje;

	public CrearCuponGUI(MainGUI padre) {
		padre.setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CrearCuponGUI.Titulo"));
		this.setSize(400, 250);
		this.getContentPane().setLayout(null);
		
		JLabel lblCodigo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CrearCuponGUI.Codigo"));
		lblCodigo.setBounds(40, 30, 150, 20);
		getContentPane().add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(200, 30, 140, 20);
		getContentPane().add(txtCodigo);
		
		JLabel lblPorcentaje = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CrearCuponGUI.Porcentaje"));
		lblPorcentaje.setBounds(40, 80, 150, 20);
		getContentPane().add(lblPorcentaje);
		
		txtPorcentaje = new JTextField();
		txtPorcentaje.setBounds(200, 80, 140, 20);
		getContentPane().add(txtPorcentaje);
		
		lblMensaje = new JLabel("");
		lblMensaje.setBounds(40, 179, 300, 20);
		getContentPane().add(lblMensaje);
		
		JButton btnCrear = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CrearCuponGUI.Crear"));
		btnCrear.setBounds(118, 138, 140, 30);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String codigo = txtCodigo.getText();
					double porcentaje = Double.parseDouble(txtPorcentaje.getText());
					
					BLFacade facade = MainGUI.getBusinessLogic();
					
					boolean exito = facade.crearCupon(codigo, porcentaje);
					
					if(exito) {
						lblMensaje.setForeground(Color.GREEN);
						lblMensaje.setText(ResourceBundle.getBundle("Etiquetas").getString("CrearCuponGUI.Exito"));
					} else {
						lblMensaje.setForeground(Color.RED);
						lblMensaje.setText(ResourceBundle.getBundle("Etiquetas").getString("CrearCuponGUI.Error"));
					}
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
		});
		getContentPane().add(btnCrear);
		//Extra que evita problemas al cerrar
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				this.addWindowListener(new java.awt.event.WindowAdapter() {
				    @Override
				    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				        if (padre != null) {
				        	padre.setVisible(true);
				        }
				    }
				});

	}
}
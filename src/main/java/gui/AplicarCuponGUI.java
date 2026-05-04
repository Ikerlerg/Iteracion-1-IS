package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Offer;

public class AplicarCuponGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtCodigo;
	private JLabel lblMensaje;
	private Offer ofertaComprada;

	// Le pasamos el PagoGUI padre para poder comunicarle el éxito
	public AplicarCuponGUI(PagoGUI padre, Offer compra) {
		padre.setVisible(false);
		this.ofertaComprada = compra;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AplicarCuponGUI.Titulo"));
		this.setSize(400, 250);
		this.getContentPane().setLayout(null);
		
		JLabel lblCodigo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AplicarCuponGUI.Codigo"));
		lblCodigo.setBounds(30, 71, 150, 20);
		getContentPane().add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(190, 71, 140, 20);
		getContentPane().add(txtCodigo);
		
		lblMensaje = new JLabel("");
		lblMensaje.setBounds(50, 168, 280, 20);
		getContentPane().add(lblMensaje);
		
		JButton btnAplicar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AplicarCuponGUI.Aplicar"));
		btnAplicar.setBounds(113, 127, 140, 30);
		btnAplicar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String codigo = txtCodigo.getText();
		        BLFacade facade = MainGUI.getBusinessLogic();
		        
		        // validarCupon ahora nos devuelve el double (ej: 0.20)
		        String mailVendedorProducto = ofertaComprada.getEmail_vendedor();
		        double porcentaje = facade.validarCupon(codigo, mailVendedorProducto);
		        
		        if(porcentaje > 0) {
		            lblMensaje.setForeground(Color.GREEN);
		            lblMensaje.setText(ResourceBundle.getBundle("Etiquetas").getString("AplicarCuponGUI.Exito"));
		            
		            padre.aplicarDescuento(codigo, porcentaje);
		            padre.setVisible(true);
		            dispose();
		            
		        } else {
		            lblMensaje.setForeground(Color.RED);
		            lblMensaje.setText(ResourceBundle.getBundle("Etiquetas").getString("AplicarCuponGUI.Error"));
		        }
		    }
		});
		getContentPane().add(btnAplicar);
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

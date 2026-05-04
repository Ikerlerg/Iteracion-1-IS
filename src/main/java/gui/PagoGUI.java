package gui;
import javax.swing.JFrame;
import java.awt.Color;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import businessLogic.BLFacade;
import domain.Offer;


import javax.swing.JTextField;

public class PagoGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField TitularField;
	private JTextField NumberField;
	private JTextField CvvField;
	private JTextField FechaField;
	private JLabel lblCuponStatus;
	private double descuentoPorcentaje = 0.0;
	private String codigoAplicado = "";
	private double precioFinal;
	private Offer compra;
	
	public PagoGUI(AceptarGUI parent, Offer compra, String buyerMail) {
		super();
		this.compra = compra;
		getContentPane().setLayout(null);
		this.setSize(new Dimension(500, 325));
		
		JButton PagoButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.cobro"));
		PagoButton.setBounds(282, 210, 95, 29);
		getContentPane().add(PagoButton);
		PagoButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            BLFacade facade = MainGUI.getBusinessLogic();
		            boolean success = facade.proposeOffer(compra.getId(), buyerMail);
		            
		            if (success) {
		                if (!codigoAplicado.isEmpty()) {
		                    facade.usarCupon(codigoAplicado);
		                }
		                PagoGUI.this.setVisible(false);
		                
		                // GENERAR FACTURA
		                double precioOriginal = compra.getPrecio();
		                double totalDescuento = precioOriginal * descuentoPorcentaje;
		                double totalAPagar = precioOriginal - totalDescuento;

		                String factura = String.format(
		                    "---------------------------------------------------------\n" +
		                    "      %s\n" +
		                    "---------------------------------------------------------\n" +
		                    "%s %s\n" +
		                    "%s %.2f€\n" +
		                    "%s -%.2f€ (%s)\n" +
		                    "---------------------------------------------------------\n" +
		                    "%s %.2f€\n" +
		                    "---------------------------------------------------------",
		                    ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.facturaTitulo"),
		                    ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.facturaProducto"), compra.getSale().getTitle(),
		                    ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.facturaPrecioOrig"), precioOriginal,
		                    ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.facturaDescuento"), totalDescuento, codigoAplicado,
		                    ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.facturaTotal"), totalAPagar
		                );

		                JOptionPane.showMessageDialog(null, factura, "Invoice", JOptionPane.INFORMATION_MESSAGE);

		                parent.jLabelMsg.setForeground(Color.GREEN);
		                parent.jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("AceptarGUI.aceptada"));
		                parent.loadOffers();
		                dispose();
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});
		
		
		JButton Cancel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CarritoGUI.exit"));
		Cancel.setBounds(63, 212, 95, 24);
		getContentPane().add(Cancel);
		Cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	dispose();
            }
        });
		
		TitularField = new JTextField();
		TitularField.setBounds(63, 56, 314, 19);
		getContentPane().add(TitularField);
		TitularField.setColumns(10);
		
		NumberField = new JTextField();
		NumberField.setBounds(62, 109, 315, 18);
		getContentPane().add(NumberField);
		NumberField.setColumns(10);
		
		CvvField = new JTextField();
		CvvField.setBounds(63, 167, 96, 18);
		getContentPane().add(CvvField);
		CvvField.setColumns(10);
		
		FechaField = new JTextField();
		FechaField.setBounds(281, 167, 96, 18);
		getContentPane().add(FechaField);
		FechaField.setColumns(10);
		
		JLabel TitularLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.titular"));
		TitularLabel.setBounds(63, 40, 314, 12);
		getContentPane().add(TitularLabel);
		
		JLabel NumeroLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.numero"));
		NumeroLabel.setBounds(63, 96, 314, 12);
		getContentPane().add(NumeroLabel);
		
		JLabel CvvLabel = new JLabel("CVV:");
		CvvLabel.setBounds(63, 155, 44, 12);
		getContentPane().add(CvvLabel);
		
		JLabel FechaLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.fecha"));
		FechaLabel.setBounds(282, 155, 116, 12);
		getContentPane().add(FechaLabel);
		
		JButton btnCupon = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.BotonCupon"));
		btnCupon.setBounds(63, 250, 150, 24);
		getContentPane().add(btnCupon);
		btnCupon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JFrame cuponVentana = new AplicarCuponGUI(PagoGUI.this, compra);
		        cuponVentana.setVisible(true);
		    }
		});
		lblCuponStatus = new JLabel("");
		lblCuponStatus.setForeground(new Color(0, 128, 0)); // Color verde
		lblCuponStatus.setBounds(63, 185, 314, 20);
		getContentPane().add(lblCuponStatus);
		
	}
	public void aplicarDescuento(String codigo, double porcentaje) {
	    this.codigoAplicado = codigo;
	    this.descuentoPorcentaje = porcentaje;
	    int descEntero = (int)(porcentaje*100.0);
	    
	    this.precioFinal = compra.getPrecio() - (compra.getPrecio() * porcentaje);
	    
	    // Actualizamos el texto de la interfaz
	    String msg = String.format(ResourceBundle.getBundle("Etiquetas").getString("PagoGUI.cuponAplicado"), codigo, descEntero);
	    lblCuponStatus.setText(msg);
	}
}

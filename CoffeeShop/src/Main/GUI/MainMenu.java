package Main.GUI;

import Main.Controller.Dompet;
import Main.Controller.User;
import Main.Controller.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class MainMenu extends JFrame {
	JFrame window 		= new JFrame("G-met Coffee");
	JLabel halo		= new JLabel();
	JLabel saldo		= new JLabel();
	JButton bKopi 		= new JButton("Pesan");
	JButton bDompet 	= new JButton("Dompet Digital");
	JButton bBayar 		= new JButton("Bayar");
	JButton bAdmin 		= new JButton("Menu Admin");
	JButton bLogout 	= new JButton("Log Out");

	NumberFormat nf = NumberFormat.getInstance(new Locale("da", "DK"));

	public MainMenu(){
		if(UserSession.getId_user() == null){
			JOptionPane.showMessageDialog(null, "Silahkan login terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
			window.setVisible(false);
			new Login();
		}else {
			initComponents();
			initListeners();
		}
	}

	private void initComponents(){
		window.getContentPane().setBackground(new Color(234,236,229));
                
		window.add(halo);
			halo.setText("Haloo, "+UserSession.getNama()+ "!!");
			halo.setFont(new Font("Cambria", Font.BOLD,20));
			halo.setForeground(new Color(0,0,0));
			halo.setBounds(25,30,220,30);

		window.add(saldo);
			saldo.setText("Saldo anda Rp."+ nf.format(Dompet.getSaldo()));
			saldo.setFont(new Font("Courier",Font.PLAIN,14));
			saldo.setForeground(new Color(0,0,0));
			saldo.setBounds(25,55,220,30);

		window.add(bKopi);
			bKopi.setBounds(10, 120, 220, 30);
			bKopi.setForeground(new Color(255, 255, 255));
			bKopi.setBackground(new Color(88,140,126));

		window.add(bBayar);
			bBayar.setBounds(250, 120, 220, 30);
			bBayar.setForeground(new Color(255,255,255));
			bBayar.setBackground(new Color(88,140,126));

		window.add(bDompet);
			bDompet.setBounds(10, 160, 220, 30);
			bDompet.setForeground(new Color(255,255,255));
			bDompet.setBackground(new Color(88,140,126));

		if(UserSession.getRole() == 1) {
			window.add(bAdmin);
			bAdmin.setBounds(250, 160, 220, 30);
			bAdmin.setForeground(new Color(255, 255, 255));
			bAdmin.setBackground(new Color(88,140,126));

			window.add(bLogout);
			bLogout.setBackground(new Color(35,56,50));
			bLogout.setForeground(new Color(208,221,217));
			bLogout.setBounds(250, 200, 220, 30);
		}else{
			window.add(bLogout);
			bLogout.setBackground(new Color(35,56,50));
			bLogout.setForeground(new Color(208,221,217));
			bLogout.setBounds(250, 160, 220, 30);
		}
		window.setLayout(null);
		window.setSize(500, 370);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
	}

	private void initListeners(){
		bKopi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
				new Order();
			}
		});

		bBayar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
				new Bayar();
			}
		});

		bDompet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
				new DompetDigital();
			}
		});
		if (UserSession.getRole() == 1) {
			bAdmin.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					window.setVisible(false);
					new Admin();
				}
			});
		}

		bLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Berhasil keluar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
				UserSession.setIdPemesanan(null);
				UserSession.setId_user(null);
				window.setVisible(false);
				new Login();
			}
		});
	}
}
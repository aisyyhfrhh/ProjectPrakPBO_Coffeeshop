package Main.GUI;

import Main.Controller.User;
import Main.Controller.Koneksi;
import Main.Controller.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class SignIn extends JFrame{
	Statement statement;
	private static final SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	// Label
	JFrame window 	= new JFrame("Daftar Akun");
	JLabel lNama	= new JLabel("Nama :");
	JLabel lEmail	= new JLabel("Email : ");
	JLabel lSandi	= new JLabel("Sandi :");
	JLabel lKSandi	= new JLabel("Konfirmasi Sandi : ");
	// Text Field
	JTextField fNama  	= new JTextField();
	JTextField fEmail	= new JTextField();
	JPasswordField fSandi	= new JPasswordField();
	JPasswordField fKSandi 	= new JPasswordField();
	// Button
	JButton bDaftar 	= new JButton("Daftar");
	JButton bKembali 	= new JButton("Back");
	JCheckBox cLihat	= new JCheckBox("Lihat");
	JCheckBox cLihat2	= new JCheckBox("Lihat");

	public SignIn(){
		if(UserSession.getId_user() != null){
			window.setVisible(true);
			new MainMenu();
		}else {
			initComponents();
			initListeners();
		}
	}

	private void initComponents(){
		window.getContentPane().setBackground(new Color(234,236,229));

		window.add(lNama);
			lNama.setBounds(50, 15, 50, 60);
			lNama.setForeground(new Color(0,0,0));
				window.add(fNama);
				fNama.setBounds(190, 30,270,30);

		window.add(lEmail);
			lEmail.setBounds(50, 65, 50, 60);
			lEmail.setForeground(new Color(0,0,0));
				window.add(fEmail);
				fEmail.setBounds(190, 80, 270, 30);

		window.add(lSandi);
			lSandi.setBounds(50, 115, 50, 60);
			lSandi.setForeground(new Color(0,0,0));
				window.add(fSandi);
				fSandi.setBounds(190,130,200,30);

		window.add(cLihat);
			cLihat.setBounds(400,130,70,30);
			cLihat.setBackground(new Color(88,140,126));
			cLihat.setForeground(new Color(255, 255, 255));

		window.add(lKSandi);
			lKSandi.setBounds(50, 165, 140, 60);
			lKSandi.setForeground(new Color(0,0,0));
				window.add(fKSandi);
				fKSandi.setBounds(190,180,200,30);

		window.add(cLihat2);
			cLihat2.setBounds(400,180,70,30);
			cLihat2.setBackground(new Color(88,140,126));
			cLihat2.setForeground(new Color(255, 255, 255));

		window.add(bDaftar);
			bDaftar.setBounds(190, 230, 165, 30);
			bDaftar.setForeground(new Color(255,255,255));
			bDaftar.setBackground(new Color(88,140,126));

		window.add(bKembali);
			bKembali.setBounds(370, 230, 90,30);
			bKembali.setForeground(new Color(255,255,255));
			bKembali.setBackground(new Color(88,140,126));

		window.setLayout(null);
		window.setSize(520, 350);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
	}

	private void initListeners(){
		bDaftar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Koneksi koneksi = new Koneksi();
				String nama 	= fNama.getText();
				String email 	= fEmail.getText();
				char[] getSandi = fSandi.getPassword();
				char[] getKsandi = fKSandi.getPassword();
				String sandi 	= String.valueOf(getSandi);
				String sandiK 	= String.valueOf(getKsandi);

				if(nama.isEmpty() || email.isEmpty() || sandi.isEmpty() || sandiK.isEmpty()){
					if(nama.isEmpty()){
						JOptionPane.showMessageDialog(null, "Nama Tidak boleh kosong!","Peringatan",JOptionPane.WARNING_MESSAGE);
					}else if(email.isEmpty()){
						JOptionPane.showMessageDialog(null, "Email Tidak boleh kosong!","Peringatan",JOptionPane.WARNING_MESSAGE);
					} else if(sandi.isEmpty()){
						JOptionPane.showMessageDialog(null, "Sandi boleh kosong!","Peringatan",JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Konfirmasi Sandi Tidak boleh kosong!","Peringatan",JOptionPane.WARNING_MESSAGE);
					}
				}else if(!User.validasiEmail(email)){
					JOptionPane.showMessageDialog(null, "Maaf email tidak valid!");
					fEmail.setText("");
				}else if(!sandi.equals(sandiK)){
					JOptionPane.showMessageDialog(null, "Sandi tidak sama!","Peringatan",JOptionPane.WARNING_MESSAGE);
					fSandi.setText("");
					fKSandi.setText("");
				} else {
					try {
						ResultSet resultSet;
						
						statement = koneksi.getConnection().createStatement();
						String q = "INSERT INTO user VALUES(default,'" + nama + "','" + email + "','" + sandi + "','" + 2 + "','" + time.format(timestamp) + "','" + time.format(timestamp) + "' )";
						int disimpan = statement.executeUpdate(q);
						String qs = "SELECT * FROM user where email='"+email+"'";
						resultSet = statement.executeQuery(qs);
						resultSet.next();
						String idUser = resultSet.getString("id");
						String is = "INSERT INTO wallet VALUES(default,'"+idUser+"',0)";
						int disimpan2 = statement.executeUpdate(is);
						if (disimpan == 1 && disimpan2 == 1) { JOptionPane.showMessageDialog(null, "Selamat anda berhasil daftar!", "Informasi", JOptionPane.WARNING_MESSAGE); statement.close(); window.setVisible(false); new Login(); }
					} catch (SQLException SqlError) { JOptionPane.showMessageDialog(null, "Gagal mendaftar! error : " + SqlError); } catch (ClassNotFoundException classError) { JOptionPane.showMessageDialog(null, "Driver tidak ditemukan !!"); }
				}
			}
		});

		bKembali.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
				new Login();
			}
		});
		cLihat.addActionListener (ae -> {
			JCheckBox c = (JCheckBox) ae.getSource();
			fSandi.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
			if(c.isSelected()) {
				cLihat.setText("Tutup");
			}else{
				cLihat.setText("Lihat");
			}
		});
		cLihat2.addActionListener (ae -> {
			JCheckBox c = (JCheckBox) ae.getSource();
			fKSandi.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
			if(c.isSelected()) {
				cLihat2.setText("Tutup");
			}else{
				cLihat2.setText("Lihat");
			}
		});
	}
}
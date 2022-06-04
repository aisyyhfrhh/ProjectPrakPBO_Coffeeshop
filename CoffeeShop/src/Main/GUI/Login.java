package Main.GUI;

import Main.Controller.Dompet;
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

public class Login extends JFrame{
	ResultSet resultSet;
	Statement statement;
	String id,nama,idDompet= null;
	int role, saldo	= 0;

	JFrame window 		= new JFrame("Login");
	JLabel lEmail		= new JLabel("Email : ");
	JLabel lsandi		= new JLabel("Sandi :");

	JTextField fEmail	= new JTextField();
	JPasswordField fSandi	= new JPasswordField();

	JButton bMasuk = new JButton("Masuk");
	JButton bDaftar = new JButton("Daftar");
	JCheckBox cLihat= new JCheckBox("Lihat");

	public Login(){
		if(UserSession.getId_user() != null){
			window.setVisible(false);
			new MainMenu();
		}else {
			initComponents();
			initListeners();
		}
	}

	private void initComponents(){
		window.getContentPane().setBackground(new Color(234,236,229));
		window.add(lEmail);
		lEmail.setBounds(50, 15, 50, 60);
		lEmail.setForeground(new Color(0,0,0));
			window.add(fEmail);
			fEmail.setBounds(130, 30,270,30);

		window.add(lsandi);
		lsandi.setBounds(50, 65, 50, 60);
		lsandi.setForeground(new Color(0,0,0));
			window.add(fSandi);
			fSandi.setBounds(130, 80, 200, 30);

		window.add(cLihat);
		cLihat.setBounds(342,80,70,30);
		cLihat.setForeground(new Color(0,0,0));

		window.add(bMasuk);
		bMasuk.setBounds(130, 130, 130, 30);
		bMasuk.setForeground(new Color(255,255,255));
		bMasuk.setBackground(new Color(97,134,133));

		window.add(bDaftar);
		bDaftar.setBounds(280, 130, 130,30);
		bDaftar.setForeground(new Color(255,255,255));
		bDaftar.setBackground(new Color(97,134,133));

		window.setLayout(null);
		window.setSize(460, 250);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
	}

	private void initListeners(){
		bMasuk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Koneksi koneksi = new Koneksi();
				String email = fEmail.getText();
				char[] getpass = fSandi.getPassword();
				String sandi = String.valueOf(getpass);
				if (email.isEmpty() || sandi.isEmpty()) {
					if (email.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Silahkan Masukkan Email anda!");
					} else {
						JOptionPane.showMessageDialog(null, "Silahkan Masukkan Sandi anda!");
						fEmail.setText(email);
					}
				} else if (!User.validasiEmail(email)) {
					JOptionPane.showMessageDialog(null, "Maaf email tidak valid!");
					fEmail.setText("");
					fSandi.setText("");
				} else {
					try {
//						String MD5 = User.getMd5(sandi);
						statement = koneksi.getConnection().createStatement();
						String sql = "SELECT * FROM user a INNER JOIN wallet b ON a.id=b.user_id WHERE email='" + email + "' AND password='" + sandi + "'";
						resultSet = statement.executeQuery(sql);

						if (resultSet.next()) {
							id = resultSet.getString("id");
							UserSession.setId_user(id);
							nama = resultSet.getString("name");
							UserSession.setNama(nama);
							role = resultSet.getInt("role");
							UserSession.setRole(role);
							idDompet = resultSet.getString("wallet_id");
							Dompet.setIdDompet(idDompet);
							saldo = resultSet.getInt("jumlah");
							Dompet.setSaldo(saldo);

							window.setVisible(false);
							new MainMenu();
							statement.close();

						} else {
							JOptionPane.showMessageDialog(null, "Email atau sandi salah!");
							fEmail.setText("");
							fSandi.setText("");
						}
					} catch (SQLException sqlError) {
						JOptionPane.showMessageDialog(rootPane, "Data Gagal Ditampilkan" + sqlError);
					} catch (ClassNotFoundException classError) {
						JOptionPane.showMessageDialog(rootPane, "Driver tidak ditemukan !!");
					}

				}
			}
		});
		bDaftar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
				new SignIn();
			}
		});
		cLihat.addActionListener(ae -> {
			JCheckBox c = (JCheckBox) ae.getSource();
			fSandi.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
			if (c.isSelected()) {
				cLihat.setText("Tutup");
			} else {
				cLihat.setText("Lihat");
			}
		});
	}
}
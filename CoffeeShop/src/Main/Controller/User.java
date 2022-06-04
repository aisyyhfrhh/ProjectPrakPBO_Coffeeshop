package Main.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
	int idRole;
	String nama, email, sandi, namaRole;

	public int getIdRole() {
			return idRole;
	}
	public String getNamaRole(){
			return namaRole;
	}
	public String getNama(){
			return nama;
	}
	public String getSandi(){
			return sandi;
	}
	public String getEmail(){
			return email;
	}

	public void setIdRole(int vIdRole){
			idRole = vIdRole;
	}
	public void setNamaRole(String vNamaRole){namaRole = vNamaRole; }
	public void setNama(String vNama){
			nama = vNama;
	}
	public void setEmail(String vEmail){
			email = vEmail;
	}
	public void setSandi(String vSandi){
			sandi = vSandi;
	}

	public static String getMd5(String input)
	{
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
					hashtext = "0" + hashtext;
			}
			return hashtext;
		}
		catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan" + e);
			throw new RuntimeException(e);
		}
	}


	public static boolean validasiEmail(String email) {
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			return email.matches(regex);
	}
}

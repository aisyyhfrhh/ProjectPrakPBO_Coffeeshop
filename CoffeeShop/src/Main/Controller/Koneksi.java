package Main.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
		public Connection getConnection() throws ClassNotFoundException, SQLException {
				Class.forName("com.mysql.cj.jdbc.Driver");

				String url = "jdbc:mysql://localhost/kopii";
				Connection koneksi = DriverManager.getConnection(url, "root", "");
				return koneksi;
		}
}
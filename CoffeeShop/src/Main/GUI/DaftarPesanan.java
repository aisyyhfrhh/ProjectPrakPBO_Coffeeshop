package Main.GUI;

import Main.Controller.Koneksi;
import Main.Controller.UserSession;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaftarPesanan {
    JFrame window 	    = new JFrame("Daftar Pesanan");
    String[][] datas        = new String[500][3];
    String[] kolom          = {"Menu", "Jumlah", "Harga"};
    JTable tTable           = new JTable(datas, kolom);
    JScrollPane scrollPane  = new JScrollPane(tTable);

    ResultSet resultSet;
    Statement statement;

    public DaftarPesanan(){
        initComponents();
        loadData();
    }

    private void initComponents() {
        window.getContentPane().setBackground(new Color(28, 27, 27));
        tTable.setBackground(new Color(234,236,229));
            window.add(scrollPane);
            scrollPane.setBounds(70, 70, 400, 400);
            scrollPane.setBackground(new Color(247, 252, 255));

        window.setSize(470, 420);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
    }

    private void loadData(){
        Koneksi koneksi = new Koneksi();
        try{
            statement = koneksi.getConnection().createStatement();
            String sql = "SELECT * FROM pemesanan a INNER JOIN products b ON a.menu_code = b.menu_code WHERE a.id_pemesanan='" + UserSession.getIdPemesanan() + "' AND a.user_id='" + UserSession.getId_user() + "'";
            resultSet = statement.executeQuery(sql);

            int row = 0;
            while (resultSet.next()){
                resultSet.getString("id_pemesanan");
                datas[row][0] = resultSet.getString("menu");
                datas[row][1] = resultSet.getString("jumlah");
                datas[row][2] = resultSet.getString("price");
                row++;
            }
            statement.close();

        } catch (SQLException sqlError) {
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan" + sqlError);
        } catch (ClassNotFoundException classError) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan !!");
        }
    }
}
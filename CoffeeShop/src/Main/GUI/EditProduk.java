package Main.GUI;

import Main.Controller.User;
import Main.Controller.Koneksi;
import Main.Controller.UserSession;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EditProduk {
    JFrame window 	= new JFrame("Kelola Menu");
    Koneksi koneksi = new Koneksi();
    ResultSet resultSet;
    Statement statement;

    // Tabel
    String[][] datas    = new String[500][4];
    String[] kolom      = {"Kode Menu","Menu","Harga","Stok"};
    JTable tTable       = new JTable(datas, kolom);
    JScrollPane pane    = new JScrollPane(tTable);
    // Label
    JLabel lId          = new JLabel("Kode Menu");
    JLabel lNama        = new JLabel("Menu");
    JLabel lHarga       = new JLabel("Harga");
    JLabel lStok        = new JLabel("Stok");
    // Text Field
    JTextField fId      = new JTextField();
    JTextField fNama    = new JTextField();
    JTextField fHarga   = new JTextField();
    JTextField fStok    = new JTextField();
    // Button
    JButton bTambah     = new JButton("Insert");
    JButton bUpdate 	= new JButton("Update");
    JButton bHapus   	= new JButton("Delete");
    JButton bKembali 	= new JButton("Back");

    public EditProduk(){
        if(UserSession.getId_user() == null){
            JOptionPane.showMessageDialog(null, "Silahkan login terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            window.setVisible(false);
            new Login();
        }else if(UserSession.getRole()!=1){
            JOptionPane.showMessageDialog(null, "Akses tidak diberikan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            window.setVisible(false);
            new Login();
        }else {
            initComponents();
            loadData();
            initListeners();
        }
    }

    private void initComponents(){
        window.getContentPane().setBackground(new Color(234,236,229));

        window.add(lId);
            lId.setBounds(20, 20, 150, 20);
            lId.setForeground(new Color(0, 0, 0));
            window.add(fId);
                fId.setBounds(130, 20, 170, 25);
                fId.setEditable(false);

        window.add(lNama);
            lNama.setBounds(20, 63, 150, 20);
            lNama.setForeground(new Color(0, 0, 0));
            window.add(fNama);
                fNama.setBounds(130, 60, 170, 25);

        window.add(lHarga);
            lHarga.setBounds(340, 23, 150, 20);
            lHarga.setForeground(new Color(0, 0, 0));
            window.add(fHarga);
                fHarga.setBounds(450, 20, 200, 25);

        window.add(lStok);
            lStok.setBounds(340, 63, 150, 20);
            lStok.setForeground(new Color(0, 0, 0));
            window.add(fStok);
                fStok.setBounds(450, 60, 200, 25);

        window.add(bTambah);
            bTambah.setBounds(20, 480, 140, 30);
            bTambah.setForeground(new Color(255, 255, 255));
            bTambah.setBackground(new Color(88,140,126));

        window.add(bUpdate);
            bUpdate.setBounds(185, 480, 140, 30);
            bUpdate.setForeground(new Color(255,255,255));
            bUpdate.setBackground(new Color(88,140,126));

        window.add(bHapus);
            bHapus.setBounds(345, 480, 140, 30);
            bHapus.setForeground(new Color(255,255,255));
            bHapus.setBackground(new Color(88,140,126));

        window.add(bKembali);
            bKembali.setBounds(510, 480, 140, 30);
            bKembali.setForeground(new Color(255,255,255));
            bKembali.setBackground(new Color(88,140,126));

        tTable.setBackground(new Color(247, 252, 255));
        TableColumnModel columnModel = tTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(2);
        columnModel.getColumn(1).setPreferredWidth(60);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(30);
        window.add(pane);
        pane.setBounds(20, 120, 630, 340);
        pane.setBackground(new Color(247, 252, 255));

        window.setLayout(null);
        window.setSize(690, 570);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
    }

    private void initListeners(){
        tTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int baris = tTable.rowAtPoint(e.getPoint());
                    String id = tTable.getValueAt(baris, 0).toString();
                    fId.setText(id);
                    String nama = tTable.getValueAt(baris, 1).toString();
                    fNama.setText(nama);
                    String harga = tTable.getValueAt(baris, 2).toString();
                    fHarga.setText(harga);
                    String stok = tTable.getValueAt(baris, 3).toString();
                    fStok.setText(stok);


                } catch (Exception ea) {
                    JOptionPane.showMessageDialog(null, "Mohon Maaf Data " + ea.getMessage());
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) { }
        });

        bTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    statement = koneksi.getConnection().createStatement();
                    String harga = fHarga.getText().toString();
                    String stok = fStok.getText().toString();
                    String sql = "INSERT INTO products (`menu`, `price`, `stock`) VALUES('" + fNama.getText() + "','" + harga + "','" + stok + "')";
                    System.out.println("tessssstt");
                    int disimpan = statement.executeUpdate(sql);
                    if (disimpan == 1) {
                        JOptionPane.showMessageDialog(null, "Berhasil tambah data!", "Informasi", JOptionPane.WARNING_MESSAGE);
                        statement.close();
                        window.setVisible(false);
                        new EditProduk();
                    }
                } catch (SQLException sqlError) {
                    System.out.println(sqlError.getMessage());
                    JOptionPane.showMessageDialog(null, "Gagal mendaftar! error : " + sqlError);
                } catch (ClassNotFoundException classError) {
                    JOptionPane.showMessageDialog(null, "Driver tidak ditemukan !!");
                }
            }
        });

        bUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    statement = koneksi.getConnection().createStatement();
                    String sql = "UPDATE products set menu='" + fNama.getText() + "',price='" + fHarga.getText() + "',stock='" + fStok.getText() + "' WHERE menu_code='" + fId.getText() + "'";
                    int disimpan = statement.executeUpdate(sql);
                    if (disimpan == 1) {
                        JOptionPane.showMessageDialog(null, "Berhasil Diubah!", "Informasi", JOptionPane.WARNING_MESSAGE);
                        statement.close();
                        window.setVisible(false);
                        new EditProduk();
                    }

                } catch (SQLException sqlError) {
                    JOptionPane.showMessageDialog(null, "Gagal mendaftar! error : " + sqlError);
                } catch (ClassNotFoundException classError) {
                    JOptionPane.showMessageDialog(null, "Driver tidak ditemukan !!");
                }
            }
        });

        bHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    statement = koneksi.getConnection().createStatement();
                    String sql = "DELETE FROM products WHERE menu_code='" + fId.getText() + "'";
                    statement.execute(sql);
                    JOptionPane.showMessageDialog(null, "Berhasil Hapus Data!", "Informasi", JOptionPane.WARNING_MESSAGE);
                    statement.close();
                    window.setVisible(false);
                    new EditProduk();
                } catch (HeadlessException | SQLException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });

        bKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                new Admin();
            }
        });
    }

    private void loadData(){
        try{
            statement = koneksi.getConnection().createStatement();
            String sql = "SELECT * FROM products";
            resultSet = statement.executeQuery(sql);

            int row = 0;
            while (resultSet.next()){
                datas[row][0] = resultSet.getString("menu_code");
                datas[row][1] = resultSet.getString("menu");
                datas[row][2] = resultSet.getString("price");
                datas[row][3] = resultSet.getString("stock");
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
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EditUser {
    private static final SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp timestamp     = new Timestamp(System.currentTimeMillis());
    JFrame window 		    = new JFrame("Kelola User");
    Koneksi koneksi         = new Koneksi();

    // Tabel
    String[][] datas        = new String[500][7];
    String[] kolom          = {"ID","Nama User", "Email", "Sandi", "Role","Dibuat","Diubah"};
    JTable tTable           = new JTable(datas, kolom);
    JScrollPane scrollPane  = new JScrollPane(tTable);
    // Label
    JLabel lNama            = new JLabel("Nama");
    JLabel lEmail           = new JLabel("Email");
    JLabel lSandi           = new JLabel("Sandi");
    JLabel lRole            = new JLabel("Role");
    JCheckBox cLihat        = new JCheckBox("Lihat");
    // Text Field
    JTextField fId          = new JTextField();
    JTextField fNama        = new JTextField();
    JTextField fEmail       = new JTextField();
    JPasswordField fSandi   = new JPasswordField();
    JComboBox<String> cRole = new JComboBox<>();
    // Button
    JButton bTambah     = new JButton("Insert");
    JButton bUpdate 	= new JButton("Update");
    JButton bHapus   	= new JButton("Delete");
    JButton bKembali 	= new JButton("Back");

    User role;
    List<User> roles=new ArrayList<>();
    ResultSet resultSet;
    Statement statement;


    public EditUser(){
        if(UserSession.getId_user() == null){
            JOptionPane.showMessageDialog(null, "Silahkan login terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            window.setVisible(false);
            new Login();
        }else if(UserSession.getRole() != 1){
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
        window.add(lNama);
        lNama.setBounds(20, 20, 150, 20);
        lNama.setForeground(new Color(0, 0, 0));
            window.add(fNama);
            fNama.setBounds(130, 20, 170, 25);

        window.add(lEmail);
        lEmail.setBounds(20, 63, 150, 20);
        lEmail.setForeground(new Color(0, 0, 0));
            window.add(fEmail);
            fEmail.setBounds(130, 60, 170, 25);

        window.add(lSandi);
        lSandi.setBounds(340, 23, 150, 20);
        lSandi.setForeground(new Color(0, 0, 0));
            window.add(fSandi);
            fSandi.setBounds(450, 20, 140, 25);
                window.add(cLihat);
                cLihat.setBounds(595,20,55,25);
                cLihat.setBackground(new Color(88,140,126));
                cLihat.setForeground(new Color(255,255,255));

        window.add(lRole);
        lRole.setBounds(340, 63, 150, 20);
        lRole.setForeground(new Color(0, 0, 0));
            window.add(cRole);
            cRole.setBounds(450, 60, 200, 25);

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
        columnModel.getColumn(4).setPreferredWidth(70);
        columnModel.getColumn(5).setPreferredWidth(70);
        columnModel.getColumn(6).setPreferredWidth(70);
        window.add(scrollPane);
        scrollPane.setBounds(20, 120, 630, 340);
        scrollPane.setBackground(new Color(247, 252, 255));

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
                    String email = tTable.getValueAt(baris, 2).toString();
                    fEmail.setText(email);
                    String sandi = tTable.getValueAt(baris, 3).toString();
                    role.setSandi(sandi);
                    String role = tTable.getValueAt(baris, 4).toString();
                    cRole.setSelectedItem(role);

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
            public void mouseExited(MouseEvent e) {}
        });

        bTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String sandi = fSandi.getText();
                    statement = koneksi.getConnection().createStatement();
                    String sql = "INSERT INTO user VALUES(default,'" + fNama.getText() + "','" + fEmail.getText() + "','" + sandi + "','" + roles.get(cRole.getSelectedIndex()).getIdRole() + "','" + time.format(timestamp) + "','" + time.format(timestamp) + "' )";
                    int disimpan = statement.executeUpdate(sql);
                    if (disimpan == 1) {
                        JOptionPane.showMessageDialog(null, "Selamat anda berhasil mendaftar!", "Informasi", JOptionPane.WARNING_MESSAGE);
                        statement.close();
                        window.setVisible(false);
                        new EditUser();
                    }
                } catch (SQLException sqlError) {
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
                    String sandi = fSandi.getText();
                    statement = koneksi.getConnection().createStatement();
                    if (fSandi.getText().isEmpty()) {
                        String sql = "UPDATE user set name='" + fNama.getText() + "',email='" + fEmail.getText() + "',role='" + roles.get(cRole.getSelectedIndex()).getIdRole() + "',edited='" + time.format(timestamp) + "' WHERE id='" + fId.getText() + "'";
                        int disimpan = statement.executeUpdate(sql);
                        if (disimpan == 1) {
                            JOptionPane.showMessageDialog(null, "Berhasil Diubah!", "Informasi", JOptionPane.WARNING_MESSAGE);
                            statement.close();
                            window.setVisible(false);
                            new EditUser();
                        }
                    } else {
                        String sql = "UPDATE user set name='" + fNama.getText() + "',email='" + fEmail.getText() + "',password='" + sandi + "',role='" + roles.get(cRole.getSelectedIndex()).getIdRole() + "',edited='" + time.format(timestamp) + "' WHERE id='" + fId.getText() + "'";
                        int disimpan = statement.executeUpdate(sql);
                        if (disimpan == 1) {
                            JOptionPane.showMessageDialog(null, "Berhasil Diubah!", "Informasi", JOptionPane.WARNING_MESSAGE);
                            statement.close();
                            window.setVisible(false);
                            new EditUser();
                        }
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
                    String sql = "DELETE FROM user WHERE id='" + fId.getText() + "'";
                    statement.execute(sql);
                    JOptionPane.showMessageDialog(null, "Berhasil Hapus Data!", "Informasi", JOptionPane.WARNING_MESSAGE);
                    statement.close();
                    window.setVisible(false);
                    new EditUser();
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

    private void loadData(){
        cRole.removeAllItems();
        List<User> roles = getAllRole();
        for (User user : roles) {
            cRole.addItem(user.getNamaRole().toString());
        }

        try{
            statement = koneksi.getConnection().createStatement();
            String sql = "SELECT * FROM user a INNER JOIN role b ON a.role=b.role_id";
            resultSet = statement.executeQuery(sql);

            int row = 0;
            while (resultSet.next()){
                datas[row][0] = resultSet.getString("id");
                datas[row][1] = resultSet.getString("name");
                datas[row][2] = resultSet.getString("email");
                datas[row][3] = resultSet.getString("password");
                datas[row][4] = resultSet.getString("role");
                datas[row][5] = resultSet.getString("created on");
                datas[row][6] = resultSet.getString("edited");
                row++;
            }
            statement.close();

        } catch (SQLException sqlError) {
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan" + sqlError);
        } catch (ClassNotFoundException classError) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan !!");
        }
    }

    private List<User> getAllRole() {
        try {
            statement = koneksi.getConnection().createStatement();
            String sql = "SELECT * FROM role";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                role = new User();
                role.setIdRole(resultSet.getInt("role_id"));
                role.setNamaRole(resultSet.getString("role_name"));
                roles.add(role);
            }
        } catch (SQLException sqlError) {
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan" + sqlError);
        } catch (ClassNotFoundException classError) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan !!");
        }
        return roles;
    }


}
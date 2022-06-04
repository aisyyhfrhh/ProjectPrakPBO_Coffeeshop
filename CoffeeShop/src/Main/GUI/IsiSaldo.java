package Main.GUI;

import Main.Controller.Dompet;
import Main.Controller.Koneksi;
import Main.Controller.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class IsiSaldo {
    final String v25k = "DUALIMA";
    final String v50k = "LIMAPULUH";
    final String v100k= "SERATUS";

    private static final SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Timestamp timestamp     = new Timestamp(System.currentTimeMillis());

    Koneksi koneksi = new Koneksi();
    Statement statement;

    JFrame window 	= new JFrame("ISI SALDO");
    JLabel saldo	= new JLabel();
    JTextField fVoucher = new JTextField();
    JButton bIsiSaldo 	= new JButton("Masukkan Voucher");
    JButton bKembali 	= new JButton("Batal");

    NumberFormat nf = NumberFormat.getInstance(new Locale("da", "DK"));

    public IsiSaldo(){
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

        window.add(saldo);
            saldo.setText("Saldo anda Rp."+ nf.format(Dompet.getSaldo()));
            saldo.setFont(new Font("Courier",Font.PLAIN,14));
            saldo.setForeground(new Color(0, 0, 0));
            saldo.setBounds(30,5,220,30);

        window.add(fVoucher);
            fVoucher.setBounds(30,50,270,30);

        window.add(bIsiSaldo);
            bIsiSaldo.setBounds(30, 95, 150, 30);
            bIsiSaldo.setForeground(new Color(255, 255, 255));
            bIsiSaldo.setBackground(new Color(88,140,126));

        window.add(bKembali);
            bKembali.setBounds(200, 95, 100, 30);
            bKembali.setForeground(new Color(255,255,255));
            bKembali.setBackground(new Color(88,140,126));

        window.setLayout(null);
        window.setSize(350, 200);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
    }

    private void initListeners(){
        bIsiSaldo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fVoucher.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Anda tidak memasukkan voucher!", "Informasi", JOptionPane.WARNING_MESSAGE);
                }else if(fVoucher.getText().equals(v25k) || fVoucher.getText().equals(v50k) || fVoucher.getText().equals(v100k)){
                    if(fVoucher.getText().equals(v25k)){
                        Dompet.setVoucher(25000);
                    }else if(fVoucher.getText().equals(v50k)){
                        Dompet.setVoucher(50000);
                    }else if(fVoucher.getText().equals(v100k)){
                        Dompet.setVoucher(100000);
                    }
                    try {
                        statement = koneksi.getConnection().createStatement();
                        String sql      = "UPDATE wallet set jumlah='" + Dompet.getVocher2() + "' WHERE user_id='" + UserSession.getId_user() + "'";
                        String ISaldo   = "INSERT INTO riwayat_saldo VALUES(default,'"+ Dompet.getIdDompet() +"','+ "+ Dompet.getVoucher() +"','"+ time.format(timestamp) +"','PENGISIAN SALDO') ";
                        int isiSaldo     = statement.executeUpdate(sql);
                        int riwayatSaldo = statement.executeUpdate(ISaldo);
                        if (isiSaldo == 1 && riwayatSaldo == 1) {
                            JOptionPane.showMessageDialog(null, "SELAMAT! PENGISIAN SALDO Rp."+nf.format(Dompet.getVoucher()) +" BERHASIL\n saldo anda sekarang sebesar Rp."+nf.format(Dompet.getVocher2()), "Informasi", JOptionPane.WARNING_MESSAGE);
                            statement.close();
                            window.setVisible(false);
                            new DompetDigital();
                        }

                    } catch (SQLException sqlError) {
                        JOptionPane.showMessageDialog(null, "Gagal! error : " + sqlError);
                    } catch (ClassNotFoundException classError) {
                        JOptionPane.showMessageDialog(null, "Driver tidak ditemukan !!");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Maaf vocher anda tidak VALID!", "Informasi", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        bKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                new MainMenu();
            }
        });
    }
}
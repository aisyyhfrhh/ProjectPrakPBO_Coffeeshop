package Main.GUI;

import Main.Controller.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin {
    JFrame window       = new JFrame("Menu Admin");
    JButton bEuser 	= new JButton("Edit User");
    JButton bEproduk 	= new JButton("Edit Produk");
    JButton bEembali 	= new JButton("Back");

    public Admin(){
        if(UserSession.getId_user() == null){
            JOptionPane.showMessageDialog(null, "Harap login terlebih dahulu!", "Warning!!", JOptionPane.WARNING_MESSAGE);
            window.setVisible(false);
            new Login();
        }else if(UserSession.getRole() != 1) {
            JOptionPane.showMessageDialog(null, "Akses tidak diberikan!", "Warning!!", JOptionPane.WARNING_MESSAGE);
            window.setVisible(false);
            new Login();
        }else{
            initComponents();
            initListeners();
        }
    }

    private void initComponents(){
        window.getContentPane().setBackground(new Color(234,236,229));

        window.add(bEuser);
            bEuser.setBounds(47, 80, 200, 30);
            bEuser.setForeground(new Color(255,255,255));
            bEuser.setBackground(new Color(88,140,126));

        window.add(bEproduk);
            bEproduk.setBounds(47, 120, 200, 30);
            bEproduk.setForeground(new Color(255,255,255));
            bEproduk.setBackground(new Color(88,140,126));

        window.add(bEembali);
            bEembali.setBounds(47, 160, 200, 30);
            bEembali.setForeground(new Color(255,255,255));
            bEembali.setBackground(new Color(88,140,126));

        window.setLayout(null);
        window.setSize(300, 300);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
    }

    private void initListeners(){
        bEuser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                new EditUser();
            }
        });

        bEproduk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                new EditProduk();
            }
        });

        bEembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                new MainMenu();
            }
        });
    }
}
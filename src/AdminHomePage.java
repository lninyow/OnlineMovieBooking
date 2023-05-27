/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * author SALAD
 */
public class AdminHomePage extends JFrame {

    private javax.swing.JPanel AdminPanel;
    private javax.swing.JButton Cinema;
    private javax.swing.JButton EditMovies1;
    private javax.swing.JButton EditMall;
    private javax.swing.JLabel Logout;
    private javax.swing.JButton editDateTime;
    private javax.swing.JButton Seats;
    private javax.swing.JButton Snacks;
    private javax.swing.JButton EditTheater;


    public AdminHomePage() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {

        AdminPanel = new javax.swing.JPanel();
        Cinema = new javax.swing.JButton();
        EditMovies1 = new javax.swing.JButton();
        EditMall = new javax.swing.JButton();
        Seats = new javax.swing.JButton();
        Snacks = new javax.swing.JButton();
        editDateTime = new javax.swing.JButton();
        Logout = new javax.swing.JLabel();
        EditTheater = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        AdminPanel.setBackground(new java.awt.Color(25, 25, 25));
        AdminPanel.setLayout(null);

        EditMovies1.setBackground(new java.awt.Color(204, 0, 0));
        EditMovies1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
        EditMovies1.setForeground(new java.awt.Color(255, 255, 255));
        EditMovies1.setText("Add / Edit Movies");
        EditMovies1.setBorder(null);
        AdminPanel.add(EditMovies1);
        EditMovies1.setBounds(210, 200, 130, 40);

        EditMovies1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminAddEditMovies newAdminEdit = new AdminAddEditMovies();
                newAdminEdit.setVisible(true);
            }
        });

        EditMall.setBackground(new java.awt.Color(204, 0, 0));
        EditMall.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
        EditMall.setForeground(new java.awt.Color(255, 255, 255));
        EditMall.setText("Add / Edit Mall");
        EditMall.setBorder(null);
        AdminPanel.add(EditMall);
        EditMall.setBounds(350, 200, 130, 40);

        EditMall.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                AdminAddEditMall newAdminAddEdit = new AdminAddEditMall();
                newAdminAddEdit.setVisible(true);
            }
        });

        EditTheater.setBackground(new java.awt.Color(204, 0, 0));
        EditTheater.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
        EditTheater.setForeground(new java.awt.Color(255, 255, 255));
        EditTheater.setText("Add / Edit Theater");
        EditTheater.setBorder(null);
        AdminPanel.add(EditTheater);
        EditTheater.setBounds(210, 260, 130, 40);

        EditTheater.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminAddEditTheater newAdminAddEdit = new AdminAddEditTheater();
                newAdminAddEdit.setVisible(true);
            }
        });

        Seats.setBackground(new java.awt.Color(204, 0, 0));
        Seats.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
        Seats.setForeground(new java.awt.Color(255, 255, 255));
        Seats.setText("Update Seats");
        Seats.setBorder(null);
        AdminPanel.add(Seats);
        Seats.setBounds(490, 200, 130, 40);
        Seats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminAddEditSeats newAddSeats = new AdminAddEditSeats();
                newAddSeats.setVisible(true);
                newAddSeats.setSize(300, 300);
            }
        });

        editDateTime.setBackground(new java.awt.Color(204, 0, 0));
        editDateTime.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
        editDateTime.setForeground(new java.awt.Color(255, 255, 255));
        editDateTime.setText("Add/Edit Movie Showtime");
        editDateTime.setBorder(null);
        AdminPanel.add(editDateTime);
        editDateTime.setBounds(350, 260, 200, 40);

        editDateTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminAddEditShowtime newShowtimePanel = new AdminAddEditShowtime();
                newShowtimePanel.setSize(300, 300);
                newShowtimePanel.setVisible(true);
            }
        });

        Snacks.setBackground(new java.awt.Color(204, 0, 0));
        Snacks.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14));
        Snacks.setForeground(new java.awt.Color(255, 255, 255));
        Snacks.setText("Add Snacks");
        Snacks.setBorder(null);
        AdminPanel.add(Snacks);
        Snacks.setBounds(560, 260, 130, 40);
        Snacks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminAddEditFoodDrinks newSnacks = new AdminAddEditFoodDrinks();
                newSnacks.setSize(500, 300);
                newSnacks.setVisible(true);
            }
        });

        javax.swing.JButton Logout = new javax.swing.JButton(new javax.swing.ImageIcon("C:/Users/Liden/Desktop/logouticon.png"));

        // Set properties for the Logout button
        Logout.setBorder(null);
        Logout.setContentAreaFilled(false);
        Logout.setFocusPainted(false);
        Logout.setBounds(800, 20, 50, 50);

        Logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                WelcomePage loginFrame = new WelcomePage();
                loginFrame.setVisible(true);
            }
        });

        AdminPanel.add(Logout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 890, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(AdminPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 522, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(AdminPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))
        );

        pack();
    }
}

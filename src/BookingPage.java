// BookingPage.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookingPage extends JFrame {
    private JPanel cards;
    private CardLayout cardLayout;
    Connection connection;
    JComboBox<String> mallSelector;
    JComboBox<String> theaterSelector;
    JComboBox<String> movieSelector;
    JTextField dateField;
    JTextField startTimeField;
    JTextField endTimeField;
    JButton addButton;

    private User loggedInUser;
    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");

    public BookingPage(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        // Initialize your MovieDatabaseManager with actual parameters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 705);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards, BorderLayout.CENTER);

        JPanel panel4 = createPanel();
        JPanel panel5 = createPanel();
        JPanel panel6 = createPanel();
        // add more panels as needed...


        cards.add(panel4, "Select Date and Time");
        cards.add(panel5, "Select a Seat");
        cards.add(panel6, "Select Food or Drinks");
        // add more cards as needed...
        cardLayout.show(cards, "");

        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Select Date and Time");
        addButton(buttonPanel, "Select a Seat");
        addButton(buttonPanel, "Select Food or Drinks");
        // add more buttons as needed...

        add(buttonPanel, BorderLayout.NORTH);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        MovieDetailsPanel movieDetailsPanel = new MovieDetailsPanel(dbMovieManager, loggedInUser);
        panel.add(movieDetailsPanel, BorderLayout.CENTER);

        return panel;
    }



    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> cardLayout.show(cards, text));
        panel.add(button);
    }




}

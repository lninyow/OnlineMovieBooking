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

        // add more panels as needed...


        cards.add(panel4, "Select Movie Details");
        cardLayout.show(cards, "");

    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel labelPanel = new JPanel(); // New panel to hold the label
        JLabel newLabel = new JLabel("Select Movie Details");
        newLabel.setFont(new Font("",Font.PLAIN,20));
        labelPanel.add(newLabel); //
        MovieDetailsPanel movieDetailsPanel = new MovieDetailsPanel(dbMovieManager, loggedInUser);
        panel.add(movieDetailsPanel, BorderLayout.CENTER);
        panel.add(labelPanel,BorderLayout.NORTH);
        return panel;
    }








}

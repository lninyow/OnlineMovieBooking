// BookingPage.java

import javax.swing.*;
import java.awt.*;

public class BookingPage extends JFrame {
    private JPanel cards;
    private CardLayout cardLayout;
    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");

    public BookingPage() {
        // Initialize your MovieDatabaseManager with actual parameters
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        add(cards, BorderLayout.CENTER);

        SelectMoviePage panel1 = new SelectMoviePage();
        SelectMallPage selectMallPage = new SelectMallPage(dbMovieManager);
        JPanel panel3 = createPanel("Select a Cinema");
        JPanel panel4 = createPanel("Select a Date and Time");
        JPanel panel5 = createPanel("Select a Seat");
        JPanel panel6 = createPanel("Select Food or Drinks");
        // add more panels as needed...

        cards.add(panel1, "Select Movie");
        cards.add(selectMallPage, "Select Mall");
        cards.add(panel3, "Select a Cinema");
        cards.add(panel4, "Select Date and Time");
        cards.add(panel5, "Select a Seat");
        cards.add(panel6, "Select Food or Drinks");
        // add more cards as needed...
        cardLayout.show(cards, "SelectMallPage");

        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Select Movie");
        addButton(buttonPanel, "Select Mall");
        addButton(buttonPanel, "Select a Cinema");
        addButton(buttonPanel, "Select Date and Time");
        addButton(buttonPanel, "Select a Seat");
        addButton(buttonPanel, "Select Food or Drinks");
        // add more buttons as needed...

        this.add(cards, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.NORTH);
    }

    private JPanel createPanel(String text) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(text);
        panel.add(label);
        return panel;
    }

    private void addButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> cardLayout.show(cards, text));
        panel.add(button);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookingPage().setVisible(true);
        });
    }
}

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectMoviePage extends JFrame {

    private JButton nextButton;

    public SelectMoviePage() {
        setTitle("Cinema Go");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1920, 1080));
        setResizable(false);

        // Navigation Panel
        JPanel navPanel = new JPanel();
        //navPanel.setBackground(Color.RED);
        navPanel.setLayout(new GridBagLayout());
        GridBagConstraints navGBC = new GridBagConstraints();
        navGBC.gridx = 0;
        navGBC.gridy = 0;
        navGBC.weightx = 1;
        navGBC.anchor = GridBagConstraints.WEST;
        navGBC.insets.set(10, 10, 10, 10);
        JLabel cinemaGoLabel = new JLabel("CinemaGo");
        cinemaGoLabel.setFont(cinemaGoLabel.getFont().deriveFont(24f));
        navPanel.add(cinemaGoLabel, navGBC);
        navGBC.gridx = 1;
        navGBC.gridy = 0;
        navGBC.anchor = GridBagConstraints.EAST;
        JButton premiersButton = new JButton("Premiers");
        navPanel.add(premiersButton, navGBC);
        navGBC.gridx = 2;
        JButton comingSoonButton = new JButton("Coming Soon");
        navPanel.add(comingSoonButton, navGBC);
        navGBC.gridx = 3;
        JButton bookTicketsButton = new JButton("Book Tickets");
        navPanel.add(bookTicketsButton, navGBC);
        navGBC.gridx = 4;
        JButton aboutUsButton = new JButton("About Us");
        navPanel.add(aboutUsButton, navGBC);
        navGBC.gridx = 5;
        JButton contactUsButton = new JButton("Contact Us");
        navPanel.add(contactUsButton, navGBC);
        add(navPanel, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();

        // New Panel with Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 6));
        String[] buttonTexts = {"Book Tickets", "Select a Location", "Select a Cinema", "Select a Date and Time", "Select a Seat", "Select Food or Drinks"};
        for (String text : buttonTexts) {
            JButton button = new JButton(text);
            button.addActionListener(e -> System.out.println(text + " button clicked")); // add functionality here
            buttonPanel.add(button);
        }

        JLabel bookingSuccessfulLabel = new JLabel("Booking Successful");
        buttonPanel.add(bookingSuccessfulLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(buttonPanel, gbc);


        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.insets.set(0, 0, 10, 10);
        JLabel titleLabel = new JLabel("Select Movie");
        titleLabel.setFont(titleLabel.getFont().deriveFont(32f));
        contentPanel.add(titleLabel, gbc);
        gbc.gridy = 2;
        JComboBox<String> movieComboBox = new JComboBox<String>(new String[] { "Movie 1", "Movie 2", "Movie 3" });
        contentPanel.add(movieComboBox, gbc.gridx = 1);
        gbc.insets.set(0, 10, 10, 0);
        JButton nextButton = new JButton("Next");
        contentPanel.add(nextButton, gbc);
        add(contentPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SelectMoviePage();
    }
}

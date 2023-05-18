import java.awt.*;
import javax.swing.*;

public class SelectMoviePage extends JFrame {

    private JButton nextButton;

    public SelectMoviePage() {
        /** try {
            // Set system look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        } **/

        // Set up the JFrame
        setTitle("Cinema Go");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1920, 1080));
        setResizable(false);

        // Create the main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;

        // First row - CinemaGo, Search and Navigation
        JLabel cinemaGoLabel = new JLabel("CinemaGo");
        cinemaGoLabel.setForeground(Color.WHITE);
        cinemaGoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(cinemaGoLabel, c);

        JLabel searchLabel = new JLabel("Search for a movie: ");
        searchLabel.setForeground(Color.WHITE);
        c.gridx = 1;
        c.gridy = 0;
        mainPanel.add(searchLabel, c);

        JTextField searchField = new JTextField(20);
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        mainPanel.add(searchField, c);

        String[] navButtonNames = {"Premiers", "Coming Soon", "Book Tickets", "About Us", "Contact Us"};
        for (int i = 0; i < navButtonNames.length; i++) {
            JButton button = new JButton(navButtonNames[i]);
            button.setBackground(Color.LIGHT_GRAY);
            c.gridx = i + 3;
            c.gridy = 0;
            c.gridwidth = 1;
            mainPanel.add(button, c);
        }

        // Second row - Navigation for booking details
        String[] bookingButtonNames = {"Book Tickets", "Select a Location", "Select a Cinema", "Select a Date and Time", "Select a Seat", "Select Food or Drinks"};
        JPanel bookingPanel = new JPanel(new GridLayout(1, bookingButtonNames.length));
        bookingPanel.setBackground(Color.DARK_GRAY);
        for (String name : bookingButtonNames) {
            JButton button = new JButton(name);
            button.setBackground(Color.LIGHT_GRAY);
            bookingPanel.add(button);
        }
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 8;
        mainPanel.add(bookingPanel, c);

        // Content Panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Title
        JLabel titleLabel = new JLabel("Select Movie");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // More appealing font
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add some space
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        contentPanel.add(titleLabel, c);

        // Movie pictures
        CardLayout cardLayout = new CardLayout();
        JPanel moviePictures = new JPanel(cardLayout);
        moviePictures.add(new JLabel(new ImageIcon("path_to_movie1_picture.jpg")), "Movie 1");
        moviePictures.add(new JLabel(new ImageIcon("path_to_movie2_picture.jpg")), "Movie 2");
        moviePictures.add(new JLabel(new ImageIcon("path_to_movie3_picture.jpg")), "Movie 3");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        contentPanel.add(moviePictures, c);

        // Movie selection combo box
        JComboBox<String> movieComboBox = new JComboBox<>(new String[]{"Movie 1", "Movie 2", "Movie 3"});
        movieComboBox.setRenderer(new DefaultListCellRenderer() { // Custom renderer
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setFont(new Font("Serif", Font.PLAIN, 18)); // More appealing font
                return this;
            }
        });
        movieComboBox.addActionListener(e -> cardLayout.show(moviePictures, (String) movieComboBox.getSelectedItem())); // Switch movie picture
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER; // Center alignment
        contentPanel.add(movieComboBox, c);

        // Next button
        nextButton = new JButton(new ImageIcon("C:\\Users\\User\\Downloads\\right-arrow.png")); // Add icon
        nextButton.setFont(new Font("Serif", Font.BOLD, 18)); // More appealing font
        nextButton.setMargin(new Insets(10, 20, 10, 20)); // Add padding
        c.gridx = 2;
        c.gridy = 3;
        c.anchor = GridBagConstraints.EAST; // Align to the right
        contentPanel.add(nextButton, c);

        // Add content panel to main panel
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 8;
        mainPanel.add(contentPanel, c);

        // Add main panel to frame
        add(mainPanel, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SelectMoviePage();
    }
}

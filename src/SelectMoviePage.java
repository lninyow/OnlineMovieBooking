import java.awt.*;
import javax.swing.*;
import java.util.Vector;

public class SelectMoviePage extends JFrame {
    private static final String SERIF_FONT = "Serif";
    private static final String ARIAL_FONT = "Arial";
    private static final String CINEMA_GO = "CinemaGo";
    private static final String DATABASE_URL = "jdbc:sqlite:C:/sqlite3/OOP2/onlineMovieBooking.db";

    private MovieDatabaseManager movieDbManager;
    private MovieManager movieManager;

    public SelectMoviePage() {
        movieDbManager = new MovieDatabaseManager(DATABASE_URL, "", "");
        movieManager = new MovieManager(movieDbManager);

        setTitle(CINEMA_GO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1920, 1080));
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);

        addComponentsToMainPanel(mainPanel);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addComponentsToMainPanel(JPanel mainPanel) {
        GridBagConstraints c = setupGridBagLayout();

        JPanel northPanel = new JPanel(new GridBagLayout());
        northPanel.setBackground(Color.DARK_GRAY);
        addHeaderComponents(northPanel, c);
        addBookingComponents(northPanel, c);

        mainPanel.add(northPanel, BorderLayout.NORTH);

        addContentComponents(mainPanel, c);
    }

    private GridBagConstraints setupGridBagLayout() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;
        return c;
    }

    private void addHeaderComponents(JPanel mainPanel, GridBagConstraints c) {
        JLabel cinemaGoLabel = new JLabel(CINEMA_GO);
        setupLabelProperties(cinemaGoLabel, ARIAL_FONT, 24, Color.WHITE);
        mainPanel.add(cinemaGoLabel, c);

        JLabel searchLabel = new JLabel("Search for a movie: ");
        setupLabelProperties(searchLabel, ARIAL_FONT, 24, Color.WHITE);
        mainPanel.add(searchLabel, c);

        JTextField searchField = new JTextField(20);
        mainPanel.add(searchField, c);

        addButtonGroup(mainPanel, c, "Premiers", "Coming Soon", "Book Tickets", "About Us", "Contact Us");
    }

    private void addBookingComponents(JPanel mainPanel, GridBagConstraints c) {
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
    }

    private void addContentComponents(JPanel mainPanel, GridBagConstraints c) {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titleLabel = new JLabel("Select Movie");
        titleLabel.setFont(new Font(SERIF_FONT, Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(titleLabel, c);

        // Fetch available movies
        Vector<String> movieList = movieManager.getMovieTitles();

        // Create a JComboBox for movie selection and add it to the contentPanel
        JComboBox<String> movieComboBox = createMovieComboBox(contentPanel, c, movieList);

        // Create a next button and add it to the contentPanel
        JButton nextButton = createNextButton(contentPanel, c);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JComboBox<String> createMovieComboBox(JPanel contentPanel, GridBagConstraints c, Vector<String> movieList) {
        JComboBox<String> movieComboBox = new JComboBox<>(movieList);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.CENTER;
        contentPanel.add(movieComboBox, c);
        return movieComboBox;
    }

    private JButton createNextButton(JPanel contentPanel, GridBagConstraints c) {
        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font(SERIF_FONT, Font.BOLD, 18));
        nextButton.setMargin(new Insets(10, 20, 10, 20));
        c.gridx = 2;
        c.gridy = 3;
        c.anchor = GridBagConstraints.EAST;
        contentPanel.add(nextButton, c);
        return nextButton;
    }

    private void addButtonGroup(JPanel panel, GridBagConstraints c, String... buttonNames) {
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setBackground(Color.LIGHT_GRAY);
            c.gridx = i + 3;
            c.gridy = 0;
            c.gridwidth = 1;
            panel.add(button, c);
        }
    }

    private void setupLabelProperties(JLabel label, String fontName, int fontSize, Color color) {
        label.setForeground(color);
        label.setFont(new Font(fontName, Font.BOLD, fontSize));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public static void main(String[] args) {
        new SelectMoviePage();
    }
}

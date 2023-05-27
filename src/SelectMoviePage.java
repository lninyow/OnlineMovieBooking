import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;
import java.util.List;

public class SelectMoviePage extends JFrame {
    private JLabel moviePictureLabel;

    public SelectMoviePage() {
        setTitle("CinemaGo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1920, 1080));
        setResizable(false);
        initializeUI();
    }

    private void initializeUI() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTHWEST;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);

        mainPanel.add(createHeaderComponents(c), BorderLayout.NORTH);
        mainPanel.add(createContentComponents(c), BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createHeaderComponents(GridBagConstraints c) {
        JPanel northPanel = new JPanel(new GridBagLayout());
        northPanel.setBackground(Color.DARK_GRAY);

        c.gridx = 0;
        c.gridy = 0;
        northPanel.add(Box.createHorizontalGlue()); // Add horizontal glue to center the components

        addButtonGroup(northPanel, c, "Book Tickets", "Select a Location", "Select a Cinema", "Select a Date and Time", "Select a Seat", "Select Food or Drinks");

        return northPanel;
    }

    private JPanel createContentComponents(GridBagConstraints c) {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        c.gridx = 0;
        c.gridy = 0;
        contentPanel.add(createLabel("Select Movie", "Serif", 24, Color.BLACK), c);

        List<Movies.MovieData> movieDataList = Movies.retrieveMovieDataFromDatabase();
        JComboBox<Movies.MovieData> movieComboBox = createMovieComboBox(contentPanel, c, movieDataList);

        c.gridx = 1;
        moviePictureLabel = new JLabel();
        contentPanel.add(moviePictureLabel, c);

        c.gridy = 2;
        c.gridx = 0; // Center the button
        c.gridwidth = 2; // Span the button across two columns
        JButton nextButton = new JButton("Next");
        nextButton.setBackground(Color.LIGHT_GRAY);
        contentPanel.add(nextButton, c);

        return contentPanel;
    }

    private JLabel createLabel(String text, String fontName, int fontSize, Color color) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font(fontName, Font.BOLD, fontSize));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }

    private JComboBox<Movies.MovieData> createMovieComboBox(JPanel contentPanel, GridBagConstraints c, List<Movies.MovieData> movieDataList) {
        JComboBox<Movies.MovieData> movieComboBox = new JComboBox<>(movieDataList.toArray(new Movies.MovieData[0]));
        movieComboBox.setRenderer(new MovieComboBoxRenderer());
        movieComboBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Movies.MovieData selectedMovieData = (Movies.MovieData) movieComboBox.getSelectedItem();
                Movies selectedMovie = new Movies().retrieveMovieDetailsFromDatabase(selectedMovieData.getUrlImage());
                if (selectedMovie != null) {
                    updateMoviePicture(selectedMovieData.getUrlImage());
                } else {
                    System.out.println("Movie details not found for: " + selectedMovieData.getUrlImage());
                    moviePictureLabel.setIcon(null);
                }
            }
        });
        c.gridy = 1;
        contentPanel.add(movieComboBox, c);
        return movieComboBox;
    }

    private void updateMoviePicture(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(300, 300, Image.SCALE_DEFAULT));
            moviePictureLabel.setIcon(imageIcon);
        } catch (IOException e) {
            System.out.println("There was an error reading the image from: " + imageUrl);
            e.printStackTrace();
        }
    }

    private void addButtonGroup(JPanel panel, GridBagConstraints c, String... buttonNames) {
        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            button.setBackground(Color.LIGHT_GRAY);
            c.gridx = i + 1; // Adjust the grid position
            c.gridy = 0;
            panel.add(button, c);
        }
    }

    private static class MovieComboBoxRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Movies.MovieData) {
                value = ((Movies.MovieData) value).getTitle();
            }
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }
    }

    public static void main(String[] args) {
        new SelectMoviePage();
    }
}

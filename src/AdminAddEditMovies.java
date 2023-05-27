import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AdminAddEditMovies extends JFrame {
    private JTextField titleField;
    private JTextField releaseYearField;
    private JTextField directorField;
    private JTextField genreField;
    private JButton updateButton;
    private JTextArea plotSummaryArea;
    private JButton addButton;
    private List<Movies> moviesList;
    private JTextField priceField;
    private Movies movie;
    private JTextField imagePathField;

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");
    // Label to display the movie image


    private JComboBox<Integer> movieSelector; // Dropdown selector for movies

    private JButton editButton; // Add an Edit button to trigger movie editing

    private int nextId; // To keep track of the next ID for auto-increment

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextField getReleaseYearField() {
        return releaseYearField;
    }

    public JTextField getDirectorField() {
        return directorField;
    }

    public JTextField getGenreField() {
        return genreField;
    }

    public JTextArea getPlotSummaryArea() {
        return plotSummaryArea;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public List<Movies> getMoviesList() {
        return moviesList;
    }

    private String getImagePath() {
        return imagePathField.getText();
    }

    public int getNextId() {
        return nextId;
    }

    public AdminAddEditMovies() {
        setTitle("Add Movie");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        moviesList = new ArrayList<>();
        nextId = 1; // Initialize the next ID as 1

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Title:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        movieSelector = new JComboBox<>();
        movieSelector.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(movieSelector, gbc);






        titleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(titleField, gbc);


        JLabel releaseYearLabel = new JLabel("Release Year:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(releaseYearLabel, gbc);

        releaseYearField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(releaseYearField, gbc);

        JLabel directorLabel = new JLabel("Director:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(directorLabel, gbc);

        directorField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(directorField, gbc);

        JLabel genreLabel = new JLabel("Genre:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(genreLabel, gbc);

        genreField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(genreField, gbc);

        JLabel plotSummaryLabel = new JLabel("Plot Summary:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(plotSummaryLabel, gbc);

        plotSummaryArea = new JTextArea(5, 20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(new JScrollPane(plotSummaryArea), gbc);

        JLabel priceLabel = new JLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(priceLabel, gbc);

        priceField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(priceField, gbc);


        JLabel imagePathLabel = new JLabel("Image Path:");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(imagePathLabel, gbc);

        imagePathField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(imagePathField, gbc);

        addButton = new JButton("Add");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);


        updateButton = new JButton("Update");
        gbc.gridx = 0;
        gbc.gridy = 8;

        panel.add(updateButton, gbc);

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Get the selected movie ID from the movieSelector dropdown
                int selectedMovieId = (int) movieSelector.getSelectedItem();

                // Retrieve the movie details based on the selected ID from the database
                movie = getMovieById(selectedMovieId);

                // Retrieve the movie details from the UI components
                String title = titleField.getText();
                int releaseYear = Integer.parseInt(releaseYearField.getText());
                String director = directorField.getText();
                String genre = genreField.getText();
                String plotSummary = plotSummaryArea.getText();
                double price = Double.parseDouble(priceField.getText());
                String url = imagePathField.getText();

                // Update the movie object with the new details
                movie.setTitle(title);
                movie.setReleaseYear(releaseYear);
                movie.setDirector(director);
                movie.setGenre(genre);
                movie.setPlotSummary(plotSummary);
                movie.setPrice(String.valueOf(price));
                movie.setUrlImage(url);

                // Update the movie in the database
                movie.updateMovie();

                // Clear the input fields after updating the movie
                clearFields();
            }
        });



        editButton = new JButton("Edit");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(editButton, gbc);
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Get the selected movie ID from the movieSelector dropdown
                int selectedMovieId = (int) movieSelector.getSelectedItem();

                // Retrieve the movie details based on the selected ID from the database
                movie = getMovieById(selectedMovieId);

                // Populate the fields with the retrieved movie details for editing
                populateFieldsForEditing(movie);
            }
        });


        movie = new Movies(0, "", 0, "", "", 0, "", "");
        // Set initial values for the movie object
        movie.setUrlImage("");

        // Add an ActionListener to the addButton
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Retrieve the movie details from the UI components
                String title = titleField.getText();
                String releaseYearString = releaseYearField.getText();
                String director = directorField.getText();
                String genre = genreField.getText();
                String plotSummary = plotSummaryArea.getText();
                String priceString = priceField.getText();
                String url = imagePathField.getText();



                if (title.isEmpty() || releaseYearString.isEmpty() || director.isEmpty() || genre.isEmpty()
                        || plotSummary.isEmpty() || priceString.isEmpty() || url.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminAddEditMovies.this,
                            "Fields cannot be empty", "Empty Fields", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method without adding the movie
                }

                int releaseYear = Integer.parseInt(releaseYearString);
                double price = Double.parseDouble(priceString);

                // Check if the selected item in the JComboBox is equal to 0
                if (movieSelector.getSelectedItem().equals(0)) {
                    // Check if the number of movies is already 5
                    if (moviesList.size() >= 5) {
                        JOptionPane.showMessageDialog(AdminAddEditMovies.this,
                                "Cannot add more than 5 movies", "Limit Exceeded", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method without adding the movie
                    }

                    // Create a new movie and add it to the database
                    movie = new Movies(0, title, releaseYear, director, genre,price, plotSummary, url);
                    // Add the movie to the database
                    movie.addMovie();

                    // Add the movie to the moviesList
                    moviesList.add(movie);

                    // Add the movie ID to the movieSelector dropdown
                    movieSelector.addItem(movie.getId());
                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(AdminAddEditMovies.this, "Selected needs to be 0 to add movie!");
                    clearFields();

                }
            }
        });




        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        populateMovieSelector();
    }

    private void clearFields() {
        titleField.setText("");
        releaseYearField.setText("");
        directorField.setText("");
        genreField.setText("");
        plotSummaryArea.setText("");
        priceField.setText("");
        imagePathField.setText("");
    }

    private void populateFieldsForEditing(Movies movie) {
        // Set the movie details in the corresponding UI components
        titleField.setText(movie.getTitle());
        releaseYearField.setText(Integer.toString(movie.getReleaseYear()));
        directorField.setText(movie.getDirector());
        genreField.setText(movie.getGenre());
        plotSummaryArea.setText(movie.getPlotSummary());
        priceField.setText(String.valueOf(movie.getPrice()));
        imagePathField.setText(movie.getUrlImage());

    }

    private Movies getMovieById(int movieId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Prepare the SQL statement to retrieve the movie by ID
            String sql = "SELECT * FROM movie WHERE movie_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, movieId);

            // Execute the query
            resultSet = statement.executeQuery();

            // Check if a movie was found
            if (resultSet.next()) {
                // Retrieve the movie details from the result set
                int id = resultSet.getInt("movie_id");
                String title = resultSet.getString("title");
                int releaseYear = resultSet.getInt("release_year");
                String director = resultSet.getString("director");
                String genre = resultSet.getString("genre");
                String plotSummary = resultSet.getString("plot_summary");
                double price = resultSet.getDouble("price");
                String urlImage = resultSet.getString("url_image");

                // Create a new Movies object with the retrieved details
                Movies movie = new Movies(id, title, releaseYear, director, genre,price, plotSummary, urlImage);

                this.movie = movie;
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database resources
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null; // Movie not found or an error occurred
    }

    private void populateMovieSelector() {
        movieSelector.removeAllItems(); // Clear existing items

        // Retrieve movie IDs from the database
        try {
            // Establish a database connection
            Connection connection = dbMovieManager.getDatabaseConnection();

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to retrieve movie IDs
            String query = "SELECT movie_id FROM movie";
            ResultSet resultSet = statement.executeQuery(query);

            // Add retrieved movie IDs to the movieSelector
            movieSelector.addItem(0);
            while (resultSet.next()) {
                int movieId = resultSet.getInt("movie_id");
                movieSelector.addItem(movieId);
            }

            // Close the result set, statement, and connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
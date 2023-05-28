// MovieDetailsPanel.java

import org.sqlite.SQLiteException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

public class MovieDetailsPanel extends JPanel {
    private JComboBox<String> mallSelector;
    private JComboBox<String> theaterSelector;
    private JComboBox<String> movieSelector;

    private JComboBox<String> dateSelector;

    private JComboBox<String> timeSelector;

    private JButton selectTimeButton;

    private JButton nextButton;

    private JLabel moviePosterLabel;

    private MovieDatabaseManager dbMovieManager;

    private User loggedInUser;

    public MovieDetailsPanel(MovieDatabaseManager movieDatabaseManager, User loggedInUser) {
        this.loggedInUser = loggedInUser;
        dbMovieManager = movieDatabaseManager;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel mallLabel = new JLabel("Select Mall:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mallLabel, gbc);

        mallSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(mallSelector, gbc);

        mallSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedMall = (String) mallSelector.getSelectedItem();
                populateTheaterSelector(selectedMall);
            }
        });

        JLabel theaterLabel = new JLabel("Select Theater:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(theaterLabel, gbc);

        theaterSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(theaterSelector, gbc);

        theaterSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTheater = (String) theaterSelector.getSelectedItem();
                String selectedMovie = (String) movieSelector.getSelectedItem();
                populateDateSelector(selectedTheater,selectedMovie);
            }
        });


        JLabel movieLabel = new JLabel("Select Movie:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(movieLabel, gbc);


        movieSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(movieSelector, gbc);
        populateMovieSelector();

        movieSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedMovie = (String) movieSelector.getSelectedItem();
                displayMoviePoster(selectedMovie);
                repaint();
                revalidate();
            }
        });





        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(dateLabel, gbc);

        dateSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(dateSelector, gbc);

        JLabel startTimeLabel = new JLabel("Start Time (HH:MM):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(startTimeLabel, gbc);

        timeSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(timeSelector, gbc);



        selectTimeButton = new JButton("Select Time");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        add(selectTimeButton, gbc);

        selectTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTheater = (String) theaterSelector.getSelectedItem();
                String selectedMovie = (String) movieSelector.getSelectedItem();
                String selectedDate = (String) dateSelector.getSelectedItem();
                populateTimeSelector(selectedMovie,selectedTheater,selectedDate);
            }
        });


        moviePosterLabel = new JLabel();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 7;
        gbc.gridwidth = 4;
        add(moviePosterLabel, gbc);





        nextButton = new JButton("Next");
        gbc.gridx = 0; // Set the grid x-position to 0
        gbc.gridy = 8; // Set the grid y-position to 6
        gbc.gridwidth = 2; // Span 2 columns
        gbc.anchor = GridBagConstraints.CENTER; // Center align the button
        add(nextButton, gbc);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedTheater = (String) theaterSelector.getSelectedItem();
                String selectedMovie = (String) movieSelector.getSelectedItem();
                String selectedDate = (String) dateSelector.getSelectedItem();
                String selectedTime = (String) timeSelector.getSelectedItem();

                    insertBooking(selectedTheater,selectedMovie,selectedDate,selectedTime);

            }
        });


        populateMallSelector();
        movieSelector.setSelectedIndex(0);
    }

    public void populateMovieSelector() {
        Connection connection = null;
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Execute a query to fetch movies from the database
            String query = "SELECT title FROM movie";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Clear existing options in the movie selector
            movieSelector.removeAllItems();

            // Add movie options to the movie selector
            while (resultSet.next()) {
                String movieName = resultSet.getString("title");
                movieSelector.addItem(movieName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateTheaterSelector(String selectedMall) {
        Connection connection = null;
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve theater names from the database based on the selected mall
            String sql = "SELECT name FROM theater WHERE mall_id IN (SELECT mall_id FROM mall WHERE mall_name = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedMall);
            ResultSet resultSet = statement.executeQuery();

            // Clear the existing items in the theaterSelector
            theaterSelector.removeAllItems();

            // Add theater names to the theaterSelector
            while (resultSet.next()) {
                String theaterName = resultSet.getString("name");
                theaterSelector.addItem(theaterName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void populateMallSelector() {
        Connection connection = null;
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve mall names from the database
            String sql = "SELECT mall_name FROM mall";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Clear the existing items in the mallSelector
            mallSelector.removeAllItems();

            // Add mall names to the mallSelector
            while (resultSet.next()) {
                String mallName = resultSet.getString("mall_name");
                mallSelector.addItem(mallName);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void populateDateSelector(String selectedTheater, String selectedMovie) {
        Connection connection = null;
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve the movie ID based on the selected movie
            String movieIdQuery = "SELECT movie_id FROM movie WHERE title = ?";
            PreparedStatement movieIdStatement = connection.prepareStatement(movieIdQuery);
            movieIdStatement.setString(1, selectedMovie);
            ResultSet movieIdResult = movieIdStatement.executeQuery();
            int movieId = -1;
            if (movieIdResult.next()) {
                movieId = movieIdResult.getInt("movie_id");
            }

            // Retrieve the theater ID based on the selected theater
            String theaterIdQuery = "SELECT theater_id FROM theater WHERE name = ?";
            PreparedStatement theaterIdStatement = connection.prepareStatement(theaterIdQuery);
            theaterIdStatement.setString(1, selectedTheater);
            ResultSet theaterIdResult = theaterIdStatement.executeQuery();
            int theaterId = -1;
            if (theaterIdResult.next()) {
                theaterId = theaterIdResult.getInt("theater_id");
            }

            // Retrieve available dates from the database based on the movie ID and theater ID
            String sql = "SELECT DISTINCT date FROM showtime WHERE theater_id = ? AND movie_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, theaterId);
            statement.setInt(2, movieId);
            ResultSet resultSet = statement.executeQuery();

            // Clear the existing items in the dateSelector
            dateSelector.removeAllItems();

            // Add dates to the dateSelector
            if (resultSet.next()) {
                do {
                    String date = resultSet.getString("date");
                    dateSelector.addItem(date);
                } while (resultSet.next());
            } else {
                // If no dates are available, display a default message or perform any desired action
                dateSelector.addItem("No available dates");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private void displayMoviePoster(String selectedMovie) {
        // Retrieve the movie poster URL and price from the database based on the selected movie
        Connection connection = null;
        try {
            connection = dbMovieManager.getDatabaseConnection();
            String query = "SELECT url_image, price FROM movie WHERE title = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, selectedMovie);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String posterUrl = resultSet.getString("url_image");
                double price = resultSet.getDouble("price");

                // Load the image from the URL
                ImageIcon posterIcon = new ImageIcon(new URL(posterUrl));
                // Scale the image to fit within the JLabel
                Image scaledImage = posterIcon.getImage().getScaledInstance(400, 550, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                // Set the scaled image as the icon for the JLabel
                moviePosterLabel.setIcon(scaledIcon);

                // Set the price label text
                String priceText = "Price: " + price;
                moviePosterLabel.setText(priceText);

                revalidate();
                repaint();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }





    private void populateTimeSelector(String selectedMovie, String selectedTheater, String selectedDate) {
        Connection connection = null;
        timeSelector.removeAllItems();
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve the movie ID based on the selected movie
            String movieIdQuery = "SELECT movie_id FROM movie WHERE title = ?";
            PreparedStatement movieIdStatement = connection.prepareStatement(movieIdQuery);
            movieIdStatement.setString(1, selectedMovie);
            ResultSet movieIdResult = movieIdStatement.executeQuery();
            int movieId = -1;
            if (movieIdResult.next()) {
                movieId = movieIdResult.getInt("movie_id");
            }

            // Retrieve the theater ID based on the selected theater
            String theaterIdQuery = "SELECT theater_id FROM theater WHERE name = ?";
            PreparedStatement theaterIdStatement = connection.prepareStatement(theaterIdQuery);
            theaterIdStatement.setString(1, selectedTheater);
            ResultSet theaterIdResult = theaterIdStatement.executeQuery();
            int theaterId = -1;
            if (theaterIdResult.next()) {
                theaterId = theaterIdResult.getInt("theater_id");
            }

            // Retrieve available start times from the database based on the movie ID, theater ID, and date
            String sql = "SELECT start_time FROM showtime WHERE theater_id = ? AND movie_id = ? AND date = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, theaterId);
            statement.setInt(2, movieId);
            statement.setString(3, selectedDate);
            ResultSet resultSet = statement.executeQuery();

            // Clear the existing items in the timeSelector

            // Add start times to the timeSelector
            while (resultSet.next()) {
                String startTime = resultSet.getString("start_time");
                timeSelector.addItem(startTime);
            }

            // Set the first item as the selected item in the timeSelector
            if (timeSelector.getItemCount() > 0) {
                timeSelector.setSelectedIndex(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void insertBooking(String selectedTheater, String selectedMovie, String selectedDate, String selectedTime) {
        Connection connection = null;
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve the showtime_id based on the selected values
            int showtimeId = getShowtimeId(connection, selectedTheater, selectedMovie, selectedDate, selectedTime);
            int price = getPriceFromMovie(connection, selectedMovie);
            // Calculate the total price by multiplying the price with the number of tickets
            int totalTicketPrice = price;

            // Insert the booking details into the "booking" table
            String insertQuery = "INSERT INTO booking (user_id, showtime_id, seat_id, showtime_date, showtime_start_time, total_price, payment_status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, loggedInUser.getUserId());
            insertStatement.setInt(2, showtimeId);
            insertStatement.setInt(3, 0); // Placeholder value for seat_id
            insertStatement.setString(4, selectedDate);
            insertStatement.setString(5, selectedTime);
            insertStatement.setInt(6, totalTicketPrice);
            insertStatement.setInt(7, 0); // Placeholder value for payment_status

            // Execute the insert statement
            insertStatement.executeUpdate();

            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int bookingId = generatedKeys.getInt(1);


                int theaterId = getTheaterId(connection,selectedTheater);
                SeatDetailsPanel seatDetailsPanel = new SeatDetailsPanel(dbMovieManager, loggedInUser, bookingId,theaterId);

                // Remove the current MovieDetailsPanel from the parent container
                Container parent = getParent();
                parent.remove(MovieDetailsPanel.this);

                // Add the SeatDetailsPanel to the parent container
                parent.add(seatDetailsPanel);
                parent.revalidate();
                parent.repaint();
            }

            // Perform any additional actions or proceed to the next step
        } catch (SQLiteException e) {
            // Handle the exception
            System.err.println("Error: Failed to insert booking. " + e.getMessage());
        }catch (SQLException e) {
            e.printStackTrace();
            // Handle any errors that occur during the database operation
        } finally {
            // Close the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private int getShowtimeId(Connection connection, String selectedTheater, String selectedMovie, String selectedDate, String selectedTime) throws SQLException {
        int showtimeId = -1;

        int movieId = getMovieId(connection, selectedMovie);
        int theaterId = getTheaterId(connection, selectedTheater);

        String query = "SELECT showtime_id FROM showtime WHERE theater_id = ? AND movie_id = ? AND date = ? AND start_time = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, theaterId);
        statement.setInt(2, movieId);
        statement.setString(3, selectedDate);
        statement.setString(4, selectedTime);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            showtimeId = resultSet.getInt("showtime_id");
        } else {
            // No showtime available, display a JOptionPane message dialog
            JOptionPane.showMessageDialog(null, "No showtime available for the selected fields.", "No Showtime", JOptionPane.WARNING_MESSAGE);
        }

        return showtimeId;
    }


    private int getTheaterId(Connection connection, String theaterName) throws SQLException {
        int theaterId = -1;

        String query = "SELECT theater_id FROM theater WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, theaterName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            theaterId = resultSet.getInt("theater_id");
        }

        return theaterId;
    }


    private int getMovieId(Connection connection, String movieTitle) throws SQLException {
        int movieId = -1;

        String query = "SELECT movie_id FROM movie WHERE title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, movieTitle);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            movieId = resultSet.getInt("movie_id");
        }

        return movieId;
    }


    private int getPriceFromMovie(Connection connection, String selectedMovie) throws SQLException {
        int price = 0;

        String query = "SELECT price FROM movie WHERE title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, selectedMovie);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            price = resultSet.getInt("price");
        }

        return price;
    }






}

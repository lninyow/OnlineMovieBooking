import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminAddEditShowtime extends JFrame {
    private JComboBox<String> mallSelector;
    private JComboBox<String> theaterSelector;
    private JComboBox<String> movieSelector;
    private JTextField dateField;
    private JTextField startTimeField;
    private JTextField endTimeField;
    private JButton addButton;

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db", "username", "password");

    private Connection connection;

    public AdminAddEditShowtime() {
        setTitle("Add Showtime");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel mallLabel = new JLabel("Select Mall:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(mallLabel, gbc);

        mallSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(mallSelector, gbc);

        mallSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedMall = (String) mallSelector.getSelectedItem();
                populateTheaterSelector(selectedMall);
            }
        });

        JLabel theaterLabel = new JLabel("Select Theater:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(theaterLabel, gbc);

        theaterSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(theaterSelector, gbc);

        JLabel movieLabel = new JLabel("Select Movie:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(movieLabel, gbc);

        movieSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(movieSelector, gbc);
        populateMovieSelector();


        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(dateLabel, gbc);

        dateField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(dateField, gbc);

        JLabel startTimeLabel = new JLabel("Start Time (HH:MM):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(startTimeLabel, gbc);

        startTimeField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(startTimeField, gbc);

        JLabel endTimeLabel = new JLabel("End Time (HH:MM):");
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(endTimeLabel, gbc);

        endTimeField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(endTimeField, gbc);

        addButton = new JButton("Add Showtime");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedMall = (String) mallSelector.getSelectedItem();
                String selectedTheater = (String) theaterSelector.getSelectedItem();
                String selectedMovie = (String) movieSelector.getSelectedItem();
                String date = dateField.getText();
                String startTime = startTimeField.getText();
                String endTime = endTimeField.getText();

                if (selectedMall == null || selectedTheater == null || selectedMovie == null || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminAddEditShowtime.this,
                            "Please fill in all fields", "Missing Fields", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Establish a database connection
                    connection = dbMovieManager.getDatabaseConnection();

                    int mallId = getMallId(selectedMall);
                    int theaterId = getTheaterId(selectedTheater, mallId);
                    int movieId = getMovieId(selectedMovie);

                    if (mallId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditShowtime.this,
                                "Selected mall does not exist", "Invalid Mall", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (theaterId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditShowtime.this,
                                "Selected theater does not exist in the selected mall", "Invalid Theater", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (movieId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditShowtime.this,
                                "Selected movie does not exist", "Invalid Movie", JOptionPane.ERROR_MESSAGE);
                        return;
                    }



                    // Insert the showtime into the database
                    try {
                        insertShowtime(theaterId, movieId, date, startTime, endTime);
                    } catch (SQLException ex) {
                        // Handle SQL exception
                        ex.printStackTrace();
                        return; // or show an error message and return
                    }

                    JOptionPane.showMessageDialog(AdminAddEditShowtime.this,
                            "Showtime added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                    clearFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Close the database connection
                    closeConnection();
                }
            }
        });

        add(panel);

        pack();
        setLocationRelativeTo(null);

        populateMallSelector();
    }


    //gets the Mallid of the Selected mallName from the JComboBox of MallSelector
    private int getMallId(String mallName) throws SQLException {
        String sql = "SELECT mall_id FROM mall WHERE mall_name = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a new database connection
            Connection connection = dbMovieManager.getDatabaseConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, mallName);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("mall_id");
            }

            return -1;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    //gets the TheaterId which admin needs to pass the mallId(from getMallid) and theaterName from TheaterSelector of ComboBox
    private int getTheaterId(String theaterName, int mallId) throws SQLException {
        String sql = "SELECT theater_id FROM theater WHERE name = ? AND mall_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, theaterName);
            statement.setInt(2, mallId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("theater_id");
            }

            return -1;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    //just gets the MovieId by the selected Selector from the JComboBox
    private int getMovieId(String movieTitle) throws SQLException {
        String sql = "SELECT movie_id FROM movie WHERE title = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, movieTitle);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("movie_id");
            }

            return -1;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }


    //makes a new Showtime for a movie in the selected theater, all theater will have the same movies
    private void insertShowtime(int theaterId, int movieId, String date, String startTime, String endTime) throws SQLException {
        String sql = "INSERT INTO showtime (theater_id, movie_id, date, start_time, end_time) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = null;
        try {
            connection = dbMovieManager.getDatabaseConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, theaterId);
            statement.setInt(2, movieId);
            statement.setString(3, date);
            statement.setString(4, startTime);
            statement.setString(5, endTime);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    //just clear the fields after clicking the Add Button
    private void clearFields() {
        if (mallSelector.getItemCount() > 0) {
            mallSelector.setSelectedIndex(0);
        }

        if (theaterSelector.getItemCount() > 0) {
            theaterSelector.setSelectedIndex(0);
        }

        if (movieSelector.getItemCount() > 0) {
            movieSelector.setSelectedIndex(0);
        }

        dateField.setText("");
        startTimeField.setText("");
        endTimeField.setText("");
    }


    //populates the JComboBox selector, by using a Select mall_name statement and adding it to the list
    private void populateMallSelector() {
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


    //this will populate the TheaterSelector but you need to select a Mall first because this gets populated, because each Mall has different theaters
    private void populateTheaterSelector(String selectedMall) {
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve theater names from the database based on the selected mall
            String sql = "SELECT name FROM theater WHERE mall_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            int mallId = getMallId(selectedMall);
            statement.setInt(1, mallId);
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


    //this just populate the MovieSelector
    private void populateMovieSelector() {
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve movie titles from the database
            String sql = "SELECT title FROM movie";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Clear the existing items in the movieSelector
            movieSelector.removeAllItems();

            // Add movie titles to the movieSelector
            while (resultSet.next()) {
                String movieTitle = resultSet.getString("title");
                movieSelector.addItem(movieTitle);
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


    //this  just close the connection
    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}

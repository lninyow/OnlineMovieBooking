import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminAddEditSeats extends JFrame {
    private JComboBox<String> mallSelector;
    private JComboBox<String> theaterSelector;
    private JTextField totalSeatsField;
    private JTextField numRowsField;
    private JButton assignButton;

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db", "username", "password");

    private Connection connection;

    public AdminAddEditSeats() {
        setTitle("Assign Seats");
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

        JLabel totalSeatsLabel = new JLabel("Total Seats:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(totalSeatsLabel, gbc);

        totalSeatsField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(totalSeatsField, gbc);

        JLabel numRowsLabel = new JLabel("Number of Rows:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(numRowsLabel, gbc);

        numRowsField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(numRowsField, gbc);

        assignButton = new JButton("Assign Seats");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(assignButton, gbc);

        assignButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedMall = (String) mallSelector.getSelectedItem();
                String selectedTheater = (String) theaterSelector.getSelectedItem();
                String totalSeatsText = totalSeatsField.getText();
                String numRowsText = numRowsField.getText();

                if (selectedMall == null || selectedTheater == null || totalSeatsText.isEmpty() || numRowsText.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminAddEditSeats.this,
                            "Please fill in all fields", "Missing Fields", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int totalSeats = Integer.parseInt(totalSeatsText);
                int numRows = Integer.parseInt(numRowsText);
                int seatsPerRow = totalSeats / numRows;

                try {
                    // Establish a database connection
                    connection = dbMovieManager.getDatabaseConnection();

                    int mallId = getMallId(selectedMall);
                    int theaterId = getTheaterId(selectedTheater, mallId);

                    if (mallId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditSeats.this,
                                "Selected mall does not exist", "Invalid Mall", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (theaterId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditSeats.this,
                                "Selected theater does not exist", "Invalid Theater", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    assignSeats(theaterId, totalSeats, numRows, seatsPerRow);

                    JOptionPane.showMessageDialog(AdminAddEditSeats.this,
                            "Seats assigned successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                    totalSeatsField.setText("");
                    numRowsField.setText("");
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(AdminAddEditSeats.this,
                            "An error occurred while assigning seats", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        populateMallSelector();
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


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

    private void assignSeats(int theaterId, int totalSeats, int numRows, int seatsPerRow) throws SQLException {
        // Loop through each seat and insert it into the database
        int currentRow = 1;
        int currentSeat = 1;

        for (int i = 1; i <= totalSeats; i++) {
            // Insert the seat into the database with the appropriate theater_id, row, and seat_number
            String insertQuery = "INSERT INTO seat (theater_id, row, seat_number) VALUES (?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setInt(1, theaterId);
            insertStatement.setInt(2, currentRow);
            insertStatement.setInt(3, currentSeat);
            insertStatement.executeUpdate();

            // Increment the seat and row numbers
            currentSeat++;
            if (currentSeat > seatsPerRow) {
                currentRow++;
                currentSeat = 1;
            }
        }
    }

}


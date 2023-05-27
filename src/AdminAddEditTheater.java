import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminAddEditTheater extends JFrame {
    private JTextField theaterNameField;
    private JButton addButton;
    private JButton deleteButton;
    private JComboBox<String> mallSelector;
    private JComboBox<String> theaterSelector;  // New theater selector

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");

    private Connection connection;

    public AdminAddEditTheater() {
        setTitle("Add Theater");
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


        JLabel theaterLabel = new JLabel("Select Theater:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(theaterLabel, gbc);

        theaterSelector = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(theaterSelector, gbc);


        mallSelector.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedMall = (String) mallSelector.getSelectedItem();
                if (selectedMall != null) {
                    int mallId = 0;
                    try {
                        mallId = getMallId(selectedMall);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (mallId != -1) {
                        populateTheaterSelector(mallId);  // Populate the theater selector based on the selected mall
                    }
                }
            }
        });

        JLabel theaterNameLabel = new JLabel("Theater Name:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(theaterNameLabel, gbc);

        theaterNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(theaterNameField, gbc);

        addButton = new JButton("Add Theater");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String theaterName = theaterNameField.getText();
                String selectedMall = (String) mallSelector.getSelectedItem();

                if (theaterName.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                            "Theater name cannot be empty", "Empty Field", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selectedMall == null) {
                    JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                            "Please select a mall", "No Mall Selected", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Establish a database connection
                    connection = dbMovieManager.getDatabaseConnection();



                    // Retrieve the mall ID based on the selected mall name
                    int mallId = getMallId(selectedMall);

                    if (mallId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                                "Selected mall does not exist", "Invalid Mall", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Insert the theater into the database
                    insertTheater(theaterName, mallId);

                    JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                            "Theater added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                    clearFields();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Close the database connection
                    closeConnection();
                }
            }
        });

        deleteButton = new JButton("Delete Theater");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(deleteButton, gbc);

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String selectedTheater = (String) theaterSelector.getSelectedItem();
                String selectedMall = (String) mallSelector.getSelectedItem();

                if (selectedTheater == null || selectedMall == null) {
                    JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                            "Please select a theater and a mall", "Missing Fields", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Establish a database connection
                    connection = dbMovieManager.getDatabaseConnection();

                    int mallId = getMallId(selectedMall);
                    if (mallId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                                "Selected mall does not exist", "Invalid Mall", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int theaterId = getTheaterId(selectedTheater, mallId);
                    if (theaterId == -1) {
                        JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                                "Selected theater does not exist", "Invalid Theater", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Delete the theater from the database
                    deleteTheater(theaterId);

                    JOptionPane.showMessageDialog(AdminAddEditTheater.this,
                            "Theater deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                    clearFields();
                    populateMallSelector();
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




    private void insertTheater(String theaterName, int mallId) throws SQLException {
        String sql = "INSERT INTO theater (name, mall_id) VALUES (?, ?)";
        PreparedStatement statement = null;
        try {
            connection = dbMovieManager.getDatabaseConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, theaterName);
            statement.setInt(2, mallId);
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



    private void clearFields() {
        theaterNameField.setText("");
        mallSelector.setSelectedIndex(0);
        populateMallSelector(); // Repopulate the mall selector
        closeConnection(); // Close the database connection after populating the mall selector
    }


    private void populateMallSelector() {
        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Retrieve unique mall names from the database
            String sql = "SELECT DISTINCT mall_name FROM mall";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Clear the existing items in the mallSelector
            mallSelector.removeAllItems();

            // Add unique mall names to the mallSelector
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


    private void populateTheaterSelector(int mallId) {
        theaterSelector.removeAllItems(); // Clear the existing items in the theater selector

        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            theaterSelector.addItem("None");

            // Retrieve theater names for the selected mall from the database
            String sql = "SELECT name FROM theater WHERE mall_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, mallId);
            ResultSet resultSet = statement.executeQuery();

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

    private void deleteTheater(int theaterId) throws SQLException {
        String sql = "DELETE FROM theater WHERE theater_id = ?";
        PreparedStatement statement = null;
        try {
            connection = dbMovieManager.getDatabaseConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, theaterId);
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

    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null; // Reset the connection reference
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}

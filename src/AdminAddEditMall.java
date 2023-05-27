import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminAddEditMall extends JFrame {
    private JTextField mallNameField;
    private JTextField locationField;
    private JButton updateButton;
    private JButton addButton;
    private List<Mall> mallList;
    private Mall mall;

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");


    private JComboBox<Integer> mallSelector; // Dropdown selector for malls

    private JButton editButton; // Add an Edit button to trigger mall editing

    private int nextId; // To keep track of the next ID for auto-increment

    public JTextField getMallNameField() {
        return mallNameField;
    }

    public JTextField getLocationField() {
        return locationField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public List<Mall> getMallList() {
        return mallList;
    }

    public int getNextId() {
        return nextId;
    }

    public AdminAddEditMall() {
        setTitle("Add Mall");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mallList = new ArrayList<>();
        nextId = 1; // Initialize the next ID as 1

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel mallNameLabel = new JLabel("Mall Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(mallNameLabel, gbc);

        mallSelector = new JComboBox<>();
        mallSelector.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(mallSelector, gbc);

        mallNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(mallNameField, gbc);

        JLabel locationLabel = new JLabel("Location:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(locationLabel, gbc);

        locationField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(locationField, gbc);

        addButton = new JButton("Add");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Retrieve the movie details from the UI components
                String mallName = mallNameField.getText();
                String location = locationField.getText();




                if (mallName.isEmpty() || location.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminAddEditMall.this,
                            "Fields cannot be empty", "Empty Fields", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method without adding the movie
                }



                // Check if the selected item in the JComboBox is equal to 0
                if (mallSelector.getSelectedItem().equals(0)) {
                    // Check if the number of movies is already 5
                    if (mallList.size() >= 5) {
                        JOptionPane.showMessageDialog(AdminAddEditMall.this,
                                "Cannot add more than 5 movies", "Limit Exceeded", JOptionPane.ERROR_MESSAGE);
                        return; // Exit the method without adding the movie
                    }

                    // Create a new movie and add it to the database
                    mall = new Mall(0, mallName,location);
                    // Add the movie to the database
                    mall.addMall();

                    // Add the movie to the moviesList
                    mallList.add(mall);

                    // Add the movie ID to the movieSelector dropdown
                    mallSelector.addItem(mall.getId());
                    clearFields();

                } else {

                    JOptionPane.showMessageDialog(AdminAddEditMall.this, "Selected needs to be 0 to add mall!");
                    clearFields();

                }
            }
        });

        updateButton = new JButton("Update");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(updateButton, gbc);

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Get the selected mall ID from the mallSelector dropdown
                int selectedMallId = (int) mallSelector.getSelectedItem();

                // Retrieve the mall details based on the selected ID from the database
                mall = getMallById(selectedMallId);

                // Retrieve the mall details from the UI components
                String mallName = mallNameField.getText();
                String location = locationField.getText();

                // Update the mall object with the new details
                mall.setMallName(mallName);
                mall.setLocation(location);

                // Update the mall in the database
                mall.updateMall();

                // Clear the input fields after updating the mall
                clearFields();
            }
        });

        editButton = new JButton("Edit");
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(editButton, gbc);



        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                // Get the selected mall ID from the mallSelector dropdown
                int selectedMallId = (int) mallSelector.getSelectedItem();

                // Retrieve the mall details based on the selected ID from the database
                mall = getMallById(selectedMallId);

                // Set the mall details in the input fields
                mallNameField.setText(mall.getMallName());
                locationField.setText(mall.getLocation());
            }
        });

        add(panel);

        pack();
        setLocationRelativeTo(null);
        populateMallSelector();
     }

    private Mall getMallById(int mallId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a database connection
            connection = dbMovieManager.getDatabaseConnection();

            // Prepare the SQL statement to retrieve the movie by ID
            String sql = "SELECT * FROM mall WHERE mall_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, mallId);

            // Execute the query
            resultSet = statement.executeQuery();

            // Check if a movie was found
            if (resultSet.next()) {
                // Retrieve the movie details from the result set
                int id = resultSet.getInt("movie_id");
                String mallName = resultSet.getString("mall_name");
                String location = resultSet.getString("location");


                // Create a new Movies object with the retrieved details
                Mall mall = new Mall(id,mallName,location);

                this.mall = mall;
                return mall;
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


    private void clearFields() {
        mallNameField.setText("");
        locationField.setText("");
    }

    private void populateMallSelector() {
        mallSelector.removeAllItems(); // Clear existing items

        // Retrieve movie IDs from the database
        try {
            // Establish a database connection
            Connection connection = dbMovieManager.getDatabaseConnection();

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to retrieve movie IDs
            String query = "SELECT mall_id FROM mall";
            ResultSet resultSet = statement.executeQuery(query);

            // Add retrieved movie IDs to the movieSelector
            mallSelector.addItem(0);
            while (resultSet.next()) {
                int mallId = resultSet.getInt("mall_id");
                mallSelector.addItem(mallId);
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
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdminAddEditFoodDrinks extends JFrame {
    private JTextField foodNameField;
    private JTextField foodPriceField;
    private JButton addButton;

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db", "username", "password");

    private Connection connection;

    public AdminAddEditFoodDrinks() {
        setTitle("Add/Edit Food and Drinks");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel foodNameLabel = new JLabel("Food/Drink Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(foodNameLabel, gbc);

        foodNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(foodNameField, gbc);

        JLabel foodPriceLabel = new JLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(foodPriceLabel, gbc);

        foodPriceField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(foodPriceField, gbc);

        addButton = new JButton("Add Food/Drink");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String foodName = foodNameField.getText();
                String foodPriceText = foodPriceField.getText();

                if (foodName.isEmpty() || foodPriceText.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminAddEditFoodDrinks.this,
                            "Please fill in all fields", "Missing Fields", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // Establish a database connection
                    connection = dbMovieManager.getDatabaseConnection();

                    int foodPrice = Integer.parseInt(foodPriceText);

                    addFoodDrink(foodName, foodPrice);

                    JOptionPane.showMessageDialog(AdminAddEditFoodDrinks.this,
                            "Food/Drink added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

                    foodNameField.setText("");
                    foodPriceField.setText("");
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(AdminAddEditFoodDrinks.this,
                            "An error occurred while adding food/drink", "Error", JOptionPane.ERROR_MESSAGE);
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

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addFoodDrink(String foodName, int foodPrice) throws SQLException {
        // Insert the food/drink into the database
        String insertQuery = "INSERT INTO food_drinks (name, price) VALUES (?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
        insertStatement.setString(1, foodName);
        insertStatement.setInt(2, foodPrice);
        insertStatement.executeUpdate();
    }
}

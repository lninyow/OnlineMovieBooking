import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class FoodDetailsPanel extends JPanel {

    private JPanel foodPanel;
    private JLabel titleLabel;
    private JLabel totalPriceLabel;
    private JButton orderButton;

    private double total = 0.0;
    private MovieDatabaseManager dbMovieManager;

    private int bookingId;



    public FoodDetailsPanel(MovieDatabaseManager dbMovieManager, int bookingId) {
        this.dbMovieManager = dbMovieManager;
        initComponents();
        loadFoodItems();
        this.bookingId = bookingId;
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setText("Food Options");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        totalPriceLabel = new JLabel("Total: ₱0.00");
        totalPriceLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        orderButton = new JButton("Proceed to Checkout");
        orderButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        orderButton.addActionListener((ActionEvent evt) -> {
            try {
                Checkout(bookingId);

                CheckOutFrame checkOutFrame = new CheckOutFrame(dbMovieManager, bookingId);
                checkOutFrame.setVisible(true);

                Window parentWindow = SwingUtilities.windowForComponent(FoodDetailsPanel.this);
                if (parentWindow != null) {
                    parentWindow.dispose();
                }
                // Close the current frame (assuming FoodAndDrinks is a JFrame)
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(totalPriceLabel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
        add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        add(orderButton, BorderLayout.SOUTH);

        foodPanel = new JPanel();
        foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
        foodPanel.setBackground(Color.WHITE);
        mainPanel.add(foodPanel);
    }


    private void loadFoodItems() {
        try (Connection connection = dbMovieManager.getDatabaseConnection()) {
            String query = "SELECT id, name, price FROM food_drinks";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int foodId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    addFoodItem(name, price);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void addFoodItem(String name, double price) {
        JLabel nameLabel = new JLabel(name);
        JLabel priceLabel = new JLabel("₱" + String.format("%.2f", price));
        JLabel quantityLabel = new JLabel("Quantity:");

        JButton minusButton = new JButton("-");
        JButton plusButton = new JButton("+");

        JLabel quantityValueLabel = new JLabel("0");
        quantityValueLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

        JPanel foodItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        foodItemPanel.setBackground(Color.WHITE);

        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        minusButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        plusButton.setFont(new Font("Tahoma", Font.BOLD, 14));

        minusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityValueLabel.getText());
            if (quantity > 0) {
                quantity--;
                quantityValueLabel.setText(Integer.toString(quantity));
                double itemTotal = price * -1;
                updateTotal(itemTotal);
            }
        });

        plusButton.addActionListener(e -> {
            int quantity = Integer.parseInt(quantityValueLabel.getText());
            quantity++;
            quantityValueLabel.setText(Integer.toString(quantity));
            double itemTotal = price;
            updateTotal(itemTotal);
        });

        foodItemPanel.add(nameLabel);
        foodItemPanel.add(priceLabel);
        foodItemPanel.add(quantityLabel);
        foodItemPanel.add(minusButton);
        foodItemPanel.add(quantityValueLabel);
        foodItemPanel.add(plusButton);

        foodPanel.add(foodItemPanel);
    }

    private void updateTotal(double itemTotal) {
        total += itemTotal;
        totalPriceLabel.setText("Total: ₱" + String.format("%.2f", total));
    }

    private void Checkout(int bookingId) throws SQLException {
        double bookingTotal = 0.0;

        // Fetch booking details from the database using bookingId
        try (Connection connection = dbMovieManager.getDatabaseConnection()) {
            String query = "SELECT total_price FROM booking WHERE booking_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, bookingId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    bookingTotal = resultSet.getDouble("total_price");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Calculate the final total by adding the food items' total and booking total
        double finalTotal = total + bookingTotal;

        // Update the total_price in the booking table
        try (Connection connection = dbMovieManager.getDatabaseConnection()) {
            String updateQuery = "UPDATE booking SET total_price = ? WHERE booking_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                statement.setDouble(1, finalTotal);
                statement.setInt(2, bookingId);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Print or use the finalTotal as needed
        System.out.println("Final Total: " + finalTotal);
        // ...
    }
}

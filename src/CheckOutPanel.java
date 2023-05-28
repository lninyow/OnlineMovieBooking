import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CheckOutPanel extends JPanel {
    private JLabel theaterLabel;
    private JLabel movieLabel;
    private JLabel dateLabel;
    private JLabel startTimeLabel;
    private JLabel seatLabel;
    private JLabel foodLabel;
    private JLabel totalPriceLabel;
    private JTextField paymentField;
    private JButton payButton;

    private MovieDatabaseManager dbMovieManager;
    private int bookingId;

    public CheckOutPanel(MovieDatabaseManager dbMovieManager, int bookingId) {
        this.dbMovieManager = dbMovieManager;
        this.bookingId = bookingId;
        initComponents();
        loadBookingDetails(bookingId);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245)); // Set a light gray background color

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE); // Set a white background color
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add a gray border

        JPanel labelsPanel = new JPanel(new GridBagLayout());
        labelsPanel.setBackground(Color.WHITE); // Set a white background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 10);

        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);

        theaterLabel = new JLabel();
        theaterLabel.setFont(labelFont);

        movieLabel = new JLabel();
        movieLabel.setFont(labelFont);

        dateLabel = new JLabel();
        dateLabel.setFont(labelFont);

        startTimeLabel = new JLabel();
        startTimeLabel.setFont(labelFont);

        seatLabel = new JLabel();
        seatLabel.setFont(labelFont);

        foodLabel = new JLabel();
        foodLabel.setFont(labelFont);

        totalPriceLabel = new JLabel();
        totalPriceLabel.setFont(labelFont);

        labelsPanel.add(new JLabel("Theater Name:"), gbc);
        labelsPanel.add(theaterLabel, gbc);

        labelsPanel.add(new JLabel("Movie Name:"), gbc);
        labelsPanel.add(movieLabel, gbc);

        labelsPanel.add(new JLabel("Date:"), gbc);
        labelsPanel.add(dateLabel, gbc);

        labelsPanel.add(new JLabel("Start Time:"), gbc);
        labelsPanel.add(startTimeLabel, gbc);

        labelsPanel.add(new JLabel("Seat:"), gbc);
        labelsPanel.add(seatLabel, gbc);

        labelsPanel.add(new JLabel("Total Price:"), gbc);
        labelsPanel.add(totalPriceLabel, gbc);

        mainPanel.add(labelsPanel, BorderLayout.CENTER);

        JPanel paymentPanel = new JPanel(new BorderLayout());
        paymentPanel.setBackground(Color.WHITE); // Set a white background color

        JPanel paymentAmountPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        paymentAmountPanel.setBackground(Color.WHITE); // Set a white background color
        paymentAmountPanel.add(new JLabel("Payment Amount:"));
        paymentField = new JTextField(6);
        paymentAmountPanel.add(paymentField);
        paymentAmountPanel.add(new JLabel("  "));
        payButton = new JButton("Pay");
        paymentAmountPanel.add(payButton);

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int paymentAmount = Integer.parseInt(paymentField.getText());
                int totalPrice = Integer.parseInt(totalPriceLabel.getText());
                if (paymentAmount >= totalPrice) {
                    try (Connection connection = dbMovieManager.getDatabaseConnection()) {
                        String updateQuery = "UPDATE booking SET payment_status = ? WHERE booking_id = ?";
                        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                            statement.setBoolean(1, true);
                            statement.setInt(2, bookingId);
                            statement.executeUpdate();
                            JOptionPane.showMessageDialog(CheckOutPanel.this, "Payment Successful");
                            showReceiptDialog();

                            Window parentWindow = SwingUtilities.windowForComponent(CheckOutPanel.this);
                            if (parentWindow != null) {
                                parentWindow.dispose();
                            }
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(CheckOutPanel.this, "Insufficient Payment Amount");
                }
            }
        });

        paymentPanel.add(paymentAmountPanel, BorderLayout.NORTH);

        mainPanel.add(paymentPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadBookingDetails(int bookingId) {
            try (Connection connection = dbMovieManager.getDatabaseConnection()) {
                String query = "SELECT booking.showtime_date, booking.showtime_start_time, booking.total_price, " +
                        "theater.name, movie.title, seat.row, seat.seat_number " +
                        "FROM booking, showtime, theater, movie, seat " +
                        "WHERE booking.booking_id = ? " +
                        "AND booking.showtime_id = showtime.showtime_id " +
                        "AND showtime.theater_id = theater.theater_id " +
                        "AND showtime.movie_id = movie.movie_id " +
                        "AND booking.seat_id = seat.seat_id";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, bookingId);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String showtimeDate = resultSet.getString("showtime_date");
                        String showtimeStartTime = resultSet.getString("showtime_start_time");
                        int totalPrice = resultSet.getInt("total_price");
                        String theaterName = resultSet.getString("name");
                        String movieName = resultSet.getString("title");
                        String seatRow = resultSet.getString("row");
                        String seatNumber = resultSet.getString("seat_number");

                        // Update the necessary labels or fields with the retrieved information
                        dateLabel.setText(showtimeDate);
                        startTimeLabel.setText(showtimeStartTime);
                        totalPriceLabel.setText(Integer.toString(totalPrice));
                        seatLabel.setText("Row: "+seatRow+" - " + "Seat Number: "+seatNumber);
                        theaterLabel.setText(theaterName);
                        movieLabel.setText(movieName);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    private void showReceiptDialog() {
        JDialog receiptDialog = new JDialog();

        receiptDialog.setTitle("Payment Receipt");
        receiptDialog.setSize(400, 300);
        receiptDialog.setLocationRelativeTo(this);
        receiptDialog.setLayout(new BorderLayout());

        JTextArea receiptTextArea = new JTextArea();
        receiptTextArea.setEditable(false);
        receiptTextArea.append("Payment Receipt:\n");
        receiptTextArea.append("Theater Name: " + theaterLabel.getText() + "\n");
        receiptTextArea.append("Movie Name: " + movieLabel.getText() + "\n");
        receiptTextArea.append("Date: " + dateLabel.getText() + "\n");
        receiptTextArea.append("Start Time: " + startTimeLabel.getText() + "\n");
        receiptTextArea.append("Seat: " + seatLabel.getText() + "\n");
        receiptTextArea.append("Total Price: " + totalPriceLabel.getText() + "\n");

        JButton printButton = new JButton("Print Receipt");
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform printing logic here
                JOptionPane.showMessageDialog(receiptDialog, "Printing Receipt...");

                receiptDialog.dispose();

            }
        });

        receiptDialog.add(new JScrollPane(receiptTextArea), BorderLayout.CENTER);
        receiptDialog.add(printButton, BorderLayout.SOUTH);

        receiptDialog.setVisible(true);
    }

    private void closePanel() {
        Container parentContainer = getParent();
        if (parentContainer != null) {
            parentContainer.remove(this);
            parentContainer.revalidate();
            parentContainer.repaint();
        }
    }


}
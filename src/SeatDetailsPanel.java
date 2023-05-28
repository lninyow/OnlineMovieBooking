import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDetailsPanel extends JPanel {

    private MovieDatabaseManager dbMovieManager;
    private User loggedInUser;
    private int bookingId;
    private int theaterId;
    private List<JButton> seatButtons;

    public SeatDetailsPanel(MovieDatabaseManager dbMovieManager, User loggedInUser, int bookingId, int theaterId) {
        this.dbMovieManager = dbMovieManager;
        this.loggedInUser = loggedInUser;
        this.bookingId = bookingId;
        this.theaterId = theaterId;
        this.seatButtons = new ArrayList<>();
        List<Seat> seats;

        setLayout(new BorderLayout());
        setSize(300, 300);

        JPanel seatButtonsPanel = new JPanel();
        seatButtonsPanel.setSize(300, 300);
        seatButtonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;

        try {
            Seat seat = new Seat();
            seats = seat.getSeatsForTheater(theaterId);
            int numRows = getMaxRowNumber(seats);
            int numCols = getMaxSeatNumber(seats);

            seatButtonsPanel.setLayout(new GridLayout(numRows, numCols));
            seatButtonsPanel.setPreferredSize(new Dimension(300, 300));
            // Create and add seat buttons dynamically
            for (Seat seatObj : seats) {
                JButton seatButton = new JButton("Seat " + seatObj.getSeatNumber() + " (Row " + seatObj.getRow() + ")");
                seatButton.setSize(50, 50);
                seatButton.addActionListener(e -> {
                    int selectedSeatId = seatObj.getSeatId();

                    try {
                        // Establish a database connection
                        Connection connection = dbMovieManager.getDatabaseConnection();

                        // Update the booking table with the selected seat ID
                        String updateQuery = "UPDATE booking SET seat_id = ? WHERE booking_id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, selectedSeatId);
                            updateStatement.setInt(2, bookingId);
                            updateStatement.executeUpdate();

                            // Perform any additional steps or actions after assigning the seat to the booking
                            // For example, update the UI or proceed to the next step
                        }

                        // Close the database connection

                        connection.close();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Handle any errors that occur during the database operation
                    }

                });


                if (isSeatPopulated(seatObj)) {
                    seatButton.setEnabled(false);
                    seatButton.setBackground(Color.RED);
                }

                seatButtonsPanel.add(seatButton, constraints);
                seatButtons.add(seatButton);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add the seat buttons panel to the center of the main panel with center alignment
        add(seatButtonsPanel, BorderLayout.CENTER);

        // Add empty panels to the north, south, east, and west to center the buttons panel


        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel newLabel = new JLabel("(If seat is red it is taken)");
        northPanel.add(newLabel);
        add(northPanel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Perform action when the Next button is clicked
                // Example: Navigate to the next step or screen
            }
        });
        southPanel.add(nextButton);
        add(southPanel, BorderLayout.SOUTH);


    }


    private int getMaxRowNumber(List<Seat> seats) {
        int maxRowNumber = 0;
        for (Seat seat : seats) {
            if (seat.getRow() > maxRowNumber) {
                maxRowNumber = seat.getRow();
            }
        }
        return maxRowNumber;
    }

    private int getMaxSeatNumber(List<Seat> seats) throws SQLException {
        int maxSeatNumber = 0;

        // Establish a database connection
        Connection connection = dbMovieManager.getDatabaseConnection();

        // Execute a query to retrieve the maximum seat number for the given theater
        String query = "SELECT MAX(seat_number) FROM seat WHERE theater_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, theaterId);
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the maximum seat number from the result set
            if (resultSet.next()) {
                maxSeatNumber = resultSet.getInt(1);
            }
        }

        // Close the database connection
        connection.close();

        return maxSeatNumber;
    }


    private boolean isSeatPopulated(Seat seat) throws SQLException {
        boolean isPopulated = false;

        // Establish a database connection
        Connection connection = dbMovieManager.getDatabaseConnection();

        // Check if the seat is already populated in the booking table for the given theater
        String query = "SELECT b.seat_id FROM booking b INNER JOIN seat s ON b.seat_id = s.seat_id WHERE s.theater_id = ? AND b.seat_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, seat.getTheaterId());
            statement.setInt(2, seat.getSeatId());
            ResultSet resultSet = statement.executeQuery();

            // Check if the seat ID exists in the result set
            if (resultSet.next()) {
                isPopulated = true;
            }
        }

        // Close the database connection
        connection.close();

        return isPopulated;
    }



}






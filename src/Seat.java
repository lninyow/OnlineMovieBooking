import java.util.List;
import java.sql.*;
import java.util.*;

public class Seat {
    private int seatId;
    private int theaterId;
    private int row;

    private int seatNumber; // Add the seatNumber field



    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db", "username", "password");

    private Connection connection;

    public Seat() {

    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public Seat(int seatId, int theaterId, int row, int seatNumber) {
        this.seatId = seatId;
        this.theaterId = theaterId;
        this.row = row;
        this.seatNumber = seatNumber; // Set the seatNumber field

    }

    // getters and setters
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }



    public void assignSeats(int theaterId, int totalSeats, int seatsPerRow) throws SQLException {
        // Delete existing seats for the theater
        deleteSeatsForTheater(theaterId);


        updateTotalSeats(theaterId, totalSeats);



        // Loop through each seat and insert it into the database
        int currentRow = 1;
        int currentSeat = 1;

        for (int i = 1; i <= totalSeats; i++) {// Insert the seat into the database with the appropriate theater_id, row, and seat_number
            String insertQuery = "INSERT INTO seat (theater_id, row, seat_number) VALUES (?, ?, ?)";
            try (Connection connection = dbMovieManager.getDatabaseConnection();
                 PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setInt(1, theaterId);
                insertStatement.setInt(2, currentRow);
                insertStatement.setInt(3, currentSeat);
                insertStatement.executeUpdate();
            }

            // Increment the seat and row numbers
            currentSeat++;

            if (currentSeat > seatsPerRow) {
                currentRow++;
                currentSeat = 1;
            }
        }
    }

    private void deleteSeatsForTheater(int theaterId) throws SQLException {
        String deleteQuery = "DELETE FROM seat WHERE theater_id = ?";
        try (Connection connection = dbMovieManager.getDatabaseConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, theaterId);
            deleteStatement.executeUpdate();
        }
    }

    private void updateTotalSeats(int theaterId, int totalSeats) throws SQLException {
        String updateQuery = "UPDATE theater SET total_seats = ? WHERE theater_id = ?";
        try (Connection connection = dbMovieManager.getDatabaseConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, totalSeats);
            updateStatement.setInt(2, theaterId);
            updateStatement.executeUpdate();
        }
    }


    public List<Seat> getSeatsForTheater(int theaterId) throws SQLException {
        List<Seat> seats = new ArrayList<>();

        String query = "SELECT * FROM seat WHERE theater_id = ?";
        try (Connection connection = dbMovieManager.getDatabaseConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, theaterId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int seatId = resultSet.getInt("seat_id");
                    int row = resultSet.getInt("row");
                    int seatNumber = resultSet.getInt("seat_number");

                    Seat seat = new Seat(seatId, theaterId, row, seatNumber);
                    seats.add(seat);
                }
            }
        }
        return seats;
    }



}

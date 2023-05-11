import java.util.List;
import java.sql.*;
import java.util.*;

public class Seat {
    private int seatId;
    private int theaterId;
    private int row;
    private int seatNumber;

    private MovieDatabaseManager movieDbManager;

    public Seat(int seatId, int theaterId, int row, int seatNumber) {
        this.seatId = seatId;
        this.theaterId = theaterId;
        this.row = row;
        this.seatNumber = seatNumber;
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

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }




    public void addSeat(int theaterId, String row, int seatNumber) throws SQLException {
        String sql = "INSERT INTO seat(theater_id, row, seat_number) VALUES (?, ?, ?)";

        try (Connection conn = movieDbManager.getDatabaseConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, theaterId);
            pstmt.setString(2, row);
            pstmt.setInt(3, seatNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Integer> getAvailableSeats(int showtimeId, int theaterId, int rowNumber) throws SQLException {
        String query = "SELECT seat.seat_id FROM seat " +
                "LEFT JOIN booking ON booking.showtime_id = ? AND seat.seat_id = booking.seat_id " +
                "WHERE booking.booking_id IS NULL AND seat.theater_id = ? AND seat.row = ?";
        List<Integer> availableSeats = new ArrayList<>();

        try (Connection connection = movieDbManager.getDatabaseConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, showtimeId);
            stmt.setInt(2, theaterId);
            stmt.setInt(3, rowNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                availableSeats.add(rs.getInt("seat_id"));
            }
        }

        return availableSeats;
    }


}

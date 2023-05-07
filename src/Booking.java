import java.sql.*;

public class Booking {
    private int bookingId;
    private int userId;
    private int showtimeId;
    private int num_tickets;
    private Timestamp booking_time;
    private int totalPrice;
    private boolean paymentStatus;
    private MovieDatabaseManager movieDbManager;


    public Booking(MovieDatabaseManager movieDbManager) {
        this.movieDbManager = movieDbManager;
    }


    public Booking(int bookingId, int userId, int showtimeId, int num_tickets, Timestamp booking_time, int totalPrice, boolean paymentStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.num_tickets = num_tickets;
        this.booking_time = booking_time;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }

    public Booking(int bookingId, int userId, int showtimeId, int numTickets, Date bookingTime, int totalPrice, boolean paymentStatus) {
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getNum_tickets() {
        return num_tickets;
    }

    public void setNum_tickets(int num_tickets) {
        this.num_tickets = num_tickets;
    }

    public Timestamp getBooking_time() {
        return booking_time;
    }

    public void setBooking_time(Timestamp booking_time) {
        this.booking_time = booking_time;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Booking createBooking(int user_id, int showtime_id, int num_tickets, int total_price) {
        // check if required fields are not null or empty
        if (user_id == 0 || showtime_id == 0 || num_tickets == 0 || total_price == 0) {
            throw new IllegalArgumentException("Please provide all the required fields.");
        }
        // check if enough seats are available
        int availableSeats = getAvailableSeats(showtime_id);
        if (num_tickets > availableSeats) {
            throw new IllegalArgumentException("Sorry, there are not enough available seats for your booking.");
        }

        // insert a new row into the booking table
        try {
            String sql = "INSERT INTO booking (user_id, showtime_id, num_tickets, total_price, payment_status) VALUES (?, ?, ?, ?, 0)";
            Connection conn = movieDbManager.getDatabaseConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, user_id);
            pstmt.setInt(2, showtime_id);
            pstmt.setInt(3, num_tickets);
            pstmt.setInt(4, total_price);
            pstmt.executeUpdate();

            // get the generated booking_id and booking_time
            ResultSet rs = pstmt.getGeneratedKeys();
            int booking_id = 0;
            Date booking_time = null;
            if (rs.next()) {
                booking_id = rs.getInt(1);
                booking_time = new Date(rs.getTimestamp(2).getTime());
            }

            // create a new Booking object with the generated values
            Booking booking = new Booking(booking_id, user_id, showtime_id, num_tickets, booking_time, total_price, false);

            return booking;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




}

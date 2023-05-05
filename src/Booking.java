import java.sql.*;

public class Booking {
    private int bookingId;
    private int userId;
    private int showtimeId;
    private int num_tickets;
    private Timestamp booking_time;
    private int totalPrice;
    private boolean paymentStatus;


    public Booking(int bookingId, int userId, int showtimeId, int num_tickets, Timestamp booking_time, int totalPrice, boolean paymentStatus) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.num_tickets = num_tickets;
        this.booking_time = booking_time;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
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


}

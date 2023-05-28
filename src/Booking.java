import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Booking {
    private int bookingId;
    private int userId;
    private int showtimeId;
    private int seatId;
    private int numTickets;
    private String showtimeDate;
    private String showtimeStartTime;
    private int totalPrice;
    private int paymentStatus;
    private int foodDrinksId;

    public Booking(int bookingId, int userId, int showtimeId, int seatId, int numTickets, String showtimeDate,
                   String showtimeStartTime, int totalPrice, int paymentStatus, int foodDrinksId) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.numTickets = numTickets;
        this.showtimeDate = showtimeDate;
        this.showtimeStartTime = showtimeStartTime;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
        this.foodDrinksId = foodDrinksId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    public void setShowtimeDate(String showtimeDate) {
        this.showtimeDate = showtimeDate;
    }

    public void setShowtimeStartTime(String showtimeStartTime) {
        this.showtimeStartTime = showtimeStartTime;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaymentStatus(int paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setFoodDrinksId(int foodDrinksId) {
        this.foodDrinksId = foodDrinksId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getUserId() {
        return userId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public String getShowtimeDate() {
        return showtimeDate;
    }

    public String getShowtimeStartTime() {
        return showtimeStartTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }

    public int getFoodDrinksId() {
        return foodDrinksId;
    }

    public Booking() {
    }






}
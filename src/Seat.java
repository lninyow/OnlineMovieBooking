public class Seat {
    private int seatId;
    private int theaterId;
    private int row;
    private int seatNumber;

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
}

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Theater {
    private int theaterId;
    private int mallId;

    public Theater() {
    }

    private String name;
    private int totalSeats;

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");

    private Connection connection;

    public Theater(int theaterId, int mallId, String name, int totalSeats) {
        this.theaterId = theaterId;
        this.mallId = mallId;
        this.name = name;
        this.totalSeats = totalSeats;
    }



    public int getMallId(String mallName) throws SQLException {
        String sql = "SELECT mall_id FROM mall WHERE mall_name = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a new database connection
            Connection connection = dbMovieManager.getDatabaseConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, mallName);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("mall_id");
            }

            return -1;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void insertTheater(String theaterName, int mallId) throws SQLException {
        String sql = "INSERT INTO theater (name, mall_id) VALUES (?, ?)";
        PreparedStatement statement = null;
        try {
            connection = dbMovieManager.getDatabaseConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, theaterName);
            statement.setInt(2, mallId);
            statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteTheater(int theaterId) throws SQLException {
        String deleteTheaterSql = "DELETE FROM theater WHERE theater_id = ?";
        String deleteShowtimeSql = "DELETE FROM showtime WHERE theater_id = ?";
        String deleteSeatsSql = "DELETE FROM seat WHERE theater_id = ?";
        PreparedStatement theaterStatement = null;
        PreparedStatement showtimeStatement = null;
        PreparedStatement seatsStatement = null;


        try {
            connection = dbMovieManager.getDatabaseConnection();
            connection.setAutoCommit(false);

            // Delete showtimes associated with the theater
            showtimeStatement = connection.prepareStatement(deleteShowtimeSql);
            showtimeStatement.setInt(1, theaterId);
            showtimeStatement.executeUpdate();

            // Delete the theater
            theaterStatement = connection.prepareStatement(deleteTheaterSql);
            theaterStatement.setInt(1, theaterId);
            theaterStatement.executeUpdate();

            seatsStatement = connection.prepareStatement(deleteSeatsSql);
            seatsStatement.setInt(1, theaterId);
            seatsStatement.executeUpdate();

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback the transaction in case of an exception
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            // Close the statements and connection
            if (showtimeStatement != null) {
                showtimeStatement.close();
            }
            if (theaterStatement != null) {
                theaterStatement.close();
            }
            if(seatsStatement != null){
                seatsStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public int getTheaterId(String theaterName, int mallId) throws SQLException {
        String sql = "SELECT theater_id FROM theater WHERE name = ? AND mall_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection == null) {
            // Initialize the connection here
            connection = dbMovieManager.getDatabaseConnection();
        }
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, theaterName);
            statement.setInt(2, mallId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("theater_id");
            }

            return -1;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }


}


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class Mall {
    private int mallId;

    private String mallName;
    private String location;
    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");


    // Constructors
    public Mall(int mallId, String mallName, String location) {
        this.mallId = mallId;
        this.mallName = mallName;
        this.location = location;
    }

    // Getters and setters
    public int getMallId() {
        return mallId;
    }

    public void setMallId(int mallId) {
        this.mallId = mallId;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void addMall() {
        try {
            Connection connection = dbMovieManager.getDatabaseConnection();
            String query = "INSERT INTO mall (mall_name, location) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mallName);
            statement.setString(2, location);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Mall added to the database.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMall() {
        try {
            Connection connection = dbMovieManager.getDatabaseConnection();
            String query = "UPDATE mall SET mall_name = ?, location = ? WHERE mall_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mallName);
            statement.setString(2, location);
            statement.setInt(3, mallId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Mall updated in the database.");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return mallId;
    }


}

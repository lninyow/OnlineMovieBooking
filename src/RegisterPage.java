import java.sql.SQLException;
import java.sql.*;
public class RegisterPage {
    private int userId;
    private String firstName,lastName,userName,password;
    private int contact_no;
    private String emailAddress;

    private MovieDatabaseManager dbMovieManager;

    public MovieDatabaseManager getDbMovieManager() {
        return dbMovieManager;
    }

    public void setDbMovieManager(MovieDatabaseManager dbMovieManager) {
        this.dbMovieManager = dbMovieManager;
    }

    public RegisterPage(int userId, String firstName, String lastName, String userName, String password, int contact_no, String emailAddress) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.contact_no = contact_no;
        this.emailAddress = emailAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getContact_no() {
        return contact_no;
    }

    public void setContact_no(int contact_no) {
        this.contact_no = contact_no;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean registerUser(String username, String password) throws SQLException {
        // Check if the username already exists
        Connection conn = dbMovieManager.getDatabaseConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT username FROM user WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            // The username already exists, so return false
            return false; // if statement in Web page, where it pops up " Username already taken" if it returns false
        }

        // Insert the new user's information into the user table
        stmt = conn.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.executeUpdate();
        return true;
    }



}

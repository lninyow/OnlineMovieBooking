import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MovieDatabaseManager {
        private String databaseUrl;
        private String username;
        private String password;

        public MovieDatabaseManager(String databaseUrl, String username, String password) {
            this.databaseUrl = databaseUrl;
            this.username = username;
            this.password = password;
        }

        public Connection getDatabaseConnection() throws SQLException {
            return DriverManager.getConnection(databaseUrl, username, password);
        }
}


    import javax.swing.*;
    import java.awt.event.*;
    import java.sql.*;
    public class Main {
    public static void main(String[] args) throws SQLException {

        MovieDatabaseManager onlineMovieBooking = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db", "username", "password");
       //new WelcomePage();
       // HomePageTest newHomepage = new HomePageTest();
        //newHomepage.setVisible(true);
        RegisterPage registrationPage = new RegisterPage();
        registrationPage.setVisible(true);




    }
}
    import javax.swing.*;
    import java.awt.event.*;
    import java.sql.*;
    public class Main {
    public static void main(String[] args) {

        MovieDatabaseManager onlineMovieBooking = new MovieDatabaseManager("jdbc:mysql:D://oop2 for finals database//onlineMovieBooking.db", "username", "password");
       //  new WelcomePage();
        RegisterPage registrationPage = new RegisterPage();
        registrationPage.setVisible(true);

    }
}
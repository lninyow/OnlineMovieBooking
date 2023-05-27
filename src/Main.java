    import javax.swing.*;
    import java.awt.event.*;
    import java.sql.*;
    public class Main {
    public static void main(String[] args)  {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomePage();
            }
        });
    }
}
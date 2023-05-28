import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CheckOutFrame extends JFrame {
    private CheckOutPanel checkOutPanel;

    public CheckOutFrame(MovieDatabaseManager dbMovieManager, int bookingId) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Checkout");
        setResizable(false);

        checkOutPanel = new CheckOutPanel(dbMovieManager, bookingId);
        add(checkOutPanel);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }
}
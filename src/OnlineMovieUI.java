import javax.swing.*;
import java.awt.*;
public class OnlineMovieUI extends JFrame{


    private JButton homeButton, bookingButton, aboutUsButton;

    public OnlineMovieUI() {
        setTitle("Navigation Bar");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the home button
        homeButton = new JButton("Home");
        homeButton.setPreferredSize(new Dimension(150, 50));

        // Create the booking button
        bookingButton = new JButton("Booking");
        bookingButton.setPreferredSize(new Dimension(150, 50));

        // Create the about us button
        aboutUsButton = new JButton("About Us");
        aboutUsButton.setPreferredSize(new Dimension(150, 50));

        // Create the navigation bar panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        navBarPanel.setPreferredSize(new Dimension(1920, 60));
        navBarPanel.setBackground(Color.GRAY);

        // Add the buttons to the navigation bar panel
        navBarPanel.add(homeButton);
        navBarPanel.add(bookingButton);
        navBarPanel.add(aboutUsButton);

        // Add the navigation bar panel to the JFrame
        getContentPane().add(navBarPanel, BorderLayout.NORTH);

        setVisible(true);
    }


}


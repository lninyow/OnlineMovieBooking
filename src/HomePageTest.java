import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;


public class HomePageTest extends JFrame {
    private JLabel lbBookYourTicket;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField tfSearch;
    private JButton btnBookTicket;
    private JButton btnPremiers;
    private JButton btnComingSoon1;
    private JButton btnBookTickets;
    private JButton btnAboutUs1;
    private JButton btnContactUs;
    private JLabel image;
    private JLabel imageMovie1;
    private JLabel imageMovie2;
    private JLabel imageMovie3;
    private JLabel imageMovie4;
    private JLabel imageMovie5;
    private JLabel imageComingSoon1;
    private JLabel imageComingSoon2;
    private JLabel imageComingSoon3;
    private JLabel imageComingSoon4;
    private JLabel imageComingSoon5;

    public HomePageTest() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 705);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;

        // First row
        jLabel1 = new JLabel("Search for a movie: ");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(jLabel1, c);

        tfSearch = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        panel.add(tfSearch, c);

        btnPremiers = new JButton("Premiers");
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        panel.add(btnPremiers, c);

        btnComingSoon1 = new JButton("Coming Soon");
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        panel.add(btnComingSoon1, c);

        btnBookTickets = new JButton("Book Tickets");
        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        panel.add(btnBookTickets, c);

        btnAboutUs1 = new JButton("About Us");
        c.gridx = 5;
        c.gridy = 0;
        c.gridwidth = 1;
        panel.add(btnAboutUs1, c);

        btnContactUs = new JButton("Contact Us");
        c.gridx = 7;
        c.gridy = 0;
        panel.add(btnContactUs, c);


  // Second row
        try {
            BufferedImage image = ImageIO.read(new File("C://Users//Liden//Downloads//longpic.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 10;
            panel.add(label, c);
        } catch (IOException e) {
            // handle the exception
        }



        // Third row
        lbBookYourTicket = new JLabel("Book Your Ticket");
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        panel.add(lbBookYourTicket, c);

        jLabel4 = new JLabel("In Theatres");
        c.gridx = 2;
        c.gridy = 3;
        panel.add(jLabel4, c);


        //Fourth Row
        try {
            BufferedImage image = ImageIO.read(new File("C://Users//Liden//Downloads//bookticket.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            c.gridx = 0;
            c.gridy = 4;
            panel.add(label, c);
        } catch (IOException e) {
            // handle the exception
        }
        //insert pictures



        //Fifth Row


        btnBookTicket = new JButton("Book");
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        panel.add(btnBookTicket, c);

        jLabel2 = new JLabel("Coming Soon");
        c.gridx = 2;
        c.gridy = 5;

        panel.add(jLabel2, c);



        getContentPane().add(panel, BorderLayout.NORTH);

    }
}

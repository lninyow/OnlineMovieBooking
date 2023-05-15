import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;


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
        c.insets = new Insets(10, 5, 10, 5);
        c.anchor = GridBagConstraints.NORTH;

        // First row
        jLabel1 = new JLabel("Search for a movie: ");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(jLabel1, c);

        tfSearch = new JTextField(20);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(tfSearch, c);

        btnPremiers = new JButton("Premiers");
        c.gridx = 2;
        c.gridy = 0;
        panel.add(btnPremiers, c);

        btnComingSoon1 = new JButton("Coming Soon");
        c.gridx = 3;
        c.gridy = 0;

        panel.add(btnComingSoon1, c);

        btnBookTickets = new JButton("Book Tickets");
        c.gridx = 4;
        c.gridy = 0;
        panel.add(btnBookTickets, c);

        btnAboutUs1 = new JButton("About Us");
        c.gridx = 5;
        c.gridy = 0;
        panel.add(btnAboutUs1, c);

        btnContactUs = new JButton("Contact Us");
        c.gridx = 6;
        c.gridy = 0;
        panel.add(btnContactUs, c);


  // Second row
        try {
            BufferedImage image = ImageIO.read(new File("C://Users//Liden//Downloads//longpic.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 9;
            panel.add(label, c);
        } catch (IOException e) {
            // handle the exception
        }




        // Third row
        lbBookYourTicket = new JLabel("Book Your Ticket");
        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.WEST;
        panel.add(lbBookYourTicket, c);


        jLabel4 = new JLabel("In Theatres");
        c.gridx = 2;
        c.gridy = 3;
        panel.add(jLabel4, c);


        //Fourth Row
        JPanel newPane = new JPanel();
        newPane.setLayout(new BorderLayout());
        btnBookTicket = new JButton("Book");
        ImageIcon icon = new ImageIcon("C://Users//Liden//Downloads//bookticket.png");
        image = new JLabel(icon);
        c.gridx = 0;
        c.gridy = 4;
        newPane.add(image,BorderLayout.NORTH);
        newPane.add(btnBookTicket,BorderLayout.SOUTH);
        panel.add(newPane, c);





            ImageIcon icon1 = new ImageIcon("C://Users//Liden//Desktop//movie1.png");
            imageMovie1 = new JLabel(icon1);




        ImageIcon icon2 = new ImageIcon("C://Users//Liden//Desktop//movie2.png");
        imageMovie2 = new JLabel(icon2);



        ImageIcon icon3 = new ImageIcon("C://Users//Liden//Desktop//movie3.png");
        imageMovie3 = new JLabel(icon3);



        ImageIcon icon4 = new ImageIcon("C://Users//Liden//Desktop//movie4.png");
        imageMovie4 = new JLabel(icon4);


        ImageIcon icon5 = new ImageIcon("C://Users//Liden//Desktop//movie5.png");
        imageMovie5 = new JLabel(icon5);


        JPanel moviePanel = new JPanel(new FlowLayout());

        moviePanel.add(imageMovie1);
        moviePanel.add(imageMovie2);
        moviePanel.add(imageMovie3);
        moviePanel.add(imageMovie4);
        moviePanel.add(imageMovie5);
        c.gridx = 2;
        c.gridy = 4;

        panel.add(moviePanel,c);





        //insert pictures


        //Fifth Row




        jLabel2 = new JLabel("Coming Soon");
        c.gridx = 2;
        c.gridy = 5;
        panel.add(jLabel2, c);



        getContentPane().add(panel, BorderLayout.NORTH);

    }
}

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        setTitle("HomePage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 715);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());



        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(1206,715));
        panel.setBackground(new Color(43,43,43));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;

        //First row of homepage
        // First row
        jLabel1 = new JLabel("CinemaGo");
        jLabel1.setFont(new Font("SansSerif", Font.BOLD, 24)); // Set font size to 24
        jLabel1.setForeground(Color.WHITE);
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

    //Second row of homepage
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



        //Third row of homepage
        // Third row
        lbBookYourTicket = new JLabel("Book Your Ticket");
        lbBookYourTicket.setFont(new Font("SansSerif", Font.BOLD, 16)); // Set font size to 24
        lbBookYourTicket.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(lbBookYourTicket, c);


        jLabel4 = new JLabel("In Theatres");
        jLabel4.setFont(new Font("SansSerif", Font.BOLD, 18)); // Set font size to 24
        jLabel4.setForeground(Color.WHITE);
        c.gridx = 2;
        c.gridy = 3;
        panel.add(jLabel4, c);

        //Fourth Row of homepage
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
        //movie3 details below
        //movie 3 details
        JPanel blackWidowPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bw = new GridBagConstraints();
        blackWidowPanel.setPreferredSize(new Dimension(1201,706));
        blackWidowPanel.setBackground(new Color(43,43,43));
        bw.anchor = GridBagConstraints.NORTHWEST;
        bw.insets = new Insets(10, 5, 5, 5);

        JButton backButton1 = new JButton("Back");
        backButton1.setForeground(Color.WHITE);
        backButton1.setBackground(new Color(255,30,20));
        bw.gridx=0;
        bw.gridy=0;
        blackWidowPanel.add(backButton1,bw);

        ImageIcon blackWidowIcon = new ImageIcon("C://Users//Liden//Desktop//blck.jpg");
        Image resizedImage = blackWidowIcon.getImage().getScaledInstance(400,550,Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel blackWidowPoster = new JLabel(resizedIcon);
        bw.gridx=0;
        bw.gridy=1;
        bw.gridheight=5;
        bw.gridwidth=3;
        blackWidowPanel.add(blackWidowPoster,bw);


        JPanel movieDesc = new JPanel(new GridLayout(6,0));
        movieDesc.setBackground(new Color(43,43,43));
        JLabel title1 = new JLabel("Black Widow");
        title1.setFont(new Font("SansSerif", Font.BOLD, 32));
        title1.setForeground(Color.WHITE);

        movieDesc.add(title1);

        JLabel bwdesc = new JLabel("2021 | 2h 13m | 16");
        bwdesc.setForeground(Color.WHITE);
        bwdesc.setFont(new Font("SansSerif",Font.BOLD,12));
        movieDesc.add(bwdesc);

        JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        flowPanel.setBackground(new Color(43,43,43));
        flowPanel.setPreferredSize(new Dimension(300,200));
        JLabel plotsum1 = new JLabel();
        plotsum1.setText("<html>Natasha Romanoff, aka Black Widow, confronts the darker parts of her ledger when a dangerous conspiracy with<br>" +
                "ties to her past arises. Pursued by a force that will stop at nothing to bring her down, Natasha must deal with<br>" +
                "her history as a spy, and the broken relationships left in her wake long before she became an Avenger.</html>");
        plotsum1.setForeground(Color.WHITE);
        plotsum1.setFont(new Font("SansSerif",Font.BOLD,13));
        flowPanel.add(plotsum1);

        movieDesc.add(flowPanel);



        JLabel starring1 = new JLabel("Starring Scarlett Johansson, Florence Pugh, David Harbour, Olga Kurylenko, Robert Downey Jr.");
        starring1.setFont(new Font("SansSerif",Font.BOLD, 14));
        starring1.setForeground(Color.WHITE);
        movieDesc.add(starring1);



        JLabel directed1 = new JLabel("Directed by Cate ShortLand");
        directed1.setForeground(Color.WHITE);
        directed1.setFont(new Font("SansSerif",Font.BOLD, 14));
        movieDesc.add(directed1);


        JLabel genre1 = new JLabel("Genre Action, Superhero, Science fiction, Spy, Thriller");
        genre1.setForeground(Color.WHITE);
        genre1.setFont(new Font("SansSerif",Font.BOLD, 14));
        movieDesc.add(genre1);

        bw.gridx = 4;
        bw.gridy = 1;
        bw.gridheight = 5;
        blackWidowPanel.add(movieDesc,bw);




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


        //Fifth Row of homepage
        //Fifth Row
        jLabel2 = new JLabel("Coming Soon");
        jLabel2.setFont(new Font("SansSerif", Font.BOLD, 18)); // Set font size to 24
        jLabel2.setForeground(Color.WHITE);
        c.gridx = 2;
        c.gridy = 5;
        panel.add(jLabel2, c);
        //Sixth Row of homepage
        //Sixth Row

        ImageIcon iconn1 = new ImageIcon("C://Users//Liden//Desktop//mugentrain.png");
        imageComingSoon1 = new JLabel(iconn1);




        ImageIcon iconn2 = new ImageIcon("C://Users//Liden//Desktop//expendables.png");
        imageComingSoon2 = new JLabel(iconn2);



        ImageIcon iconn3 = new ImageIcon("C://Users//Liden//Desktop//gotg.png");
        imageComingSoon3 = new JLabel(iconn3);



        ImageIcon iconn4 = new ImageIcon("C://Users//Liden//Desktop//jaws.png");
        imageComingSoon4 = new JLabel(iconn4);


        ImageIcon iconn5 = new ImageIcon("C://Users//Liden//Desktop//meg2.png");
        imageComingSoon5 = new JLabel(iconn5);




        JPanel moviePanel1 = new JPanel(new FlowLayout());

        moviePanel1.add(imageComingSoon1);
        moviePanel1.add(imageComingSoon2);
        moviePanel1.add(imageComingSoon3);
        moviePanel1.add(imageComingSoon4);
        moviePanel1.add(imageComingSoon5);
        c.gridx = 2;
        c.gridy = 6;

        panel.add(moviePanel1,c);


       getContentPane().add(panel,BorderLayout.CENTER);

    }
}

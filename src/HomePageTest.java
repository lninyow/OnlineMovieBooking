import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JFrame;
import java.util.*;
import java.util.List;
import javax.swing.border.EmptyBorder;


public class HomePageTest extends JFrame {
    private JLabel lbBookYourTicket;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JButton btnBookTicket;
    private JButton btnPremiers;
    private JButton btnComingSoon1;
    private JButton btnAboutUs1;
    private JButton btnContactUs;
    private JButton btnLogOut; // Declare the Logout button
    private JLabel image;

    private JPanel cardPanel; // Declare the cardPanel variable at the class level

    private JLabel imageComingSoon1;
    private JLabel imageComingSoon2;
    private JLabel imageComingSoon3;
    private JLabel imageComingSoon4;
    private JLabel imageComingSoon5;
    private JPanel mainPanel;
    private User loggedInUser;



    public HomePageTest(User loggedInUser) {

        this.loggedInUser = loggedInUser;

        setTitle("HomePage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 715);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mainPanel = createMainPanel();

        CardLayout cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout); // Assign the created JPanel to the class-level cardPanel variable
        cardPanel.add(mainPanel, "mainPanel");


        getContentPane().add(cardPanel,BorderLayout.CENTER);
    }



    private JPanel createMainPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(1206, 715));
        panel.setBackground(new Color(43, 43, 43));
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


        btnPremiers = new JButton("Premiers");
        c.gridx = 2;
        c.gridy = 0;
        panel.add(btnPremiers, c);

        btnComingSoon1 = new JButton("Coming Soon");
        c.gridx = 3;
        c.gridy = 0;
        panel.add(btnComingSoon1, c);

        btnAboutUs1 = new JButton("About Us");
        c.gridx = 4;
        c.gridy = 0;
        panel.add(btnAboutUs1, c);

        btnContactUs = new JButton("Contact Us");
        c.gridx = 5;
        c.gridy = 0;
        panel.add(btnContactUs, c);

        btnLogOut = new JButton("Log Out");
        c.gridx = 6;
        c.gridy = 0;
        panel.add(btnLogOut, c);

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show confirmation dialog
                int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    // Log out and show WelcomePage
                    loggedInUser = null; // Or whatever you need to do to log out
                    WelcomePage welcomePage = new WelcomePage();
                    welcomePage.setVisible(true);
                    HomePageTest.this.dispose(); // Close HomePageTest
                }
            }
        });


        //Second row of homepage
        // Second row
        try {
            BufferedImage image = ImageIO.read(new File("src/Images/longpic.png"));
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


        jLabel4 = new JLabel("In Theaters");
        jLabel4.setFont(new Font("SansSerif", Font.BOLD, 18)); // Set font size to 24
        jLabel4.setForeground(Color.WHITE);
        c.gridx = 4;
        c.gridy = 3;
        panel.add(jLabel4, c);

        //Fourth Row of homepage
        //Fourth Row
        JPanel newPane = new JPanel();
        newPane.setLayout(new BorderLayout());
        btnBookTicket = new JButton("Book");
        ImageIcon icon = new ImageIcon("src/Images/bookticket.png");
        image = new JLabel(icon);
        c.gridx = 0;
        c.gridy = 4;
        newPane.add(image, BorderLayout.NORTH);
        newPane.add(btnBookTicket, BorderLayout.SOUTH);
        panel.add(newPane, c);

        btnBookTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookingPage bookingPage = new BookingPage(loggedInUser);
                bookingPage.setVisible(true);
            }
        });




//        ImageIcon icon1 = new ImageIcon("C://Users//Liden//Desktop//movie1.png");
//        imageMovie1 = new JLabel(icon1);
//
//
//        ImageIcon icon2 = new ImageIcon("C://Users//Liden//Desktop//movie2.png");
//        imageMovie2 = new JLabel(icon2);
//
//
//        ImageIcon icon3 = new ImageIcon("C://Users//Liden//Desktop//movie3.png");
//        imageMovie3 = new JLabel(icon3);
//        //movie3 details below
//        //movie 3 details
////        imageMovie3.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseClicked(MouseEvent e) {
////                super.mouseClicked(e);
////                mainPanel.setVisible(false); // Hide the main panel
////                blackWidowPanel.setVisible(true); // Show the blackWidowPanel
////            }
////        });
//
//
//        ImageIcon icon4 = new ImageIcon("C://Users//Liden//Desktop//movie4.png");
//        imageMovie4 = new JLabel(icon4);
//
//        ImageIcon icon5 = new ImageIcon("C://Users//Liden//Desktop//movie5.png");
//        imageMovie5 = new JLabel(icon5);

        List<String> moviePosters = Movies.retrieveMoviePostersFromDatabase();

        JPanel moviePanel = new JPanel(new FlowLayout());
        moviePanel.setBackground(new Color(43,43,43));

        for (String imageUrl : moviePosters) {

            if(imageUrl == null){
                continue;
            }
            try {
                URL url = new URL(imageUrl);
                Image image = ImageIO.read(url);

                // Resize the image to 200x200 pixels
                Image scaledImage = image.getScaledInstance(125, 180, Image.SCALE_SMOOTH);

                // Create an ImageIcon from the scaled image
                ImageIcon loopicon = new ImageIcon(scaledImage);

                // Create a JLabel with the movie image
                JLabel imageLabel = new JLabel(loopicon);
                imageLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleMovieImageClick(imageUrl);
                    }
                });

                // Add the imageLabel to the moviePanel
                moviePanel.add(imageLabel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        moviePanel.add(imageMovie1);
//        moviePanel.add(imageMovie2);
//        moviePanel.add(imageMovie3);
//        moviePanel.add(imageMovie4);
//        moviePanel.add(imageMovie5);
        c.gridx = 4;
        c.gridy = 4;

        panel.add(moviePanel, c);


        //Fifth Row of homepage
        //Fifth Row
        jLabel2 = new JLabel("Coming Soon");
        jLabel2.setFont(new Font("SansSerif", Font.BOLD, 18)); // Set font size to 24
        jLabel2.setForeground(Color.WHITE);
        c.gridx = 4;
        c.gridy = 5;

        panel.add(jLabel2, c);
        //Sixth Row of homepage
        //Sixth Row

        ImageIcon iconn1 = new ImageIcon("src/Images/jaws.png");
        imageComingSoon1 = new JLabel(iconn1);


        ImageIcon iconn2 = new ImageIcon("src/Images/gotg.png");
        imageComingSoon2 = new JLabel(iconn2);


        ImageIcon iconn3 = new ImageIcon("src/Images/meg2.png");
        imageComingSoon3 = new JLabel(iconn3);


        ImageIcon iconn4 = new ImageIcon("src/Images/mugentrain.png");
        imageComingSoon4 = new JLabel(iconn4);


        ImageIcon iconn5 = new ImageIcon("src/Images/expendables.png");
        imageComingSoon5 = new JLabel(iconn5);


        JPanel moviePanel1 = new JPanel(new FlowLayout());
        moviePanel1.setBackground(new Color(43,43,43));

        moviePanel1.add(imageComingSoon1);
        moviePanel1.add(imageComingSoon2);
        moviePanel1.add(imageComingSoon3);
        moviePanel1.add(imageComingSoon4);
        moviePanel1.add(imageComingSoon5);
        c.gridx = 4;
        c.gridy = 6;

        panel.add(moviePanel1, c);

        return panel;
    }




    //createMovieDetails when you create a new poster, it creates a panel
    private JPanel createMovieDetails(String imageUrl) {
        Movies movies = new Movies();
        Movies movie = movies.retrieveMovieDetailsFromDatabase(imageUrl);

        JPanel moviePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        moviePanel.setPreferredSize(new Dimension(1201, 706));
        moviePanel.setBackground(new Color(43, 43, 43));
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 5, 5, 5);

        JButton backButton = new JButton("Back");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(new Color(255, 30, 20));
        backButton.addActionListener(e -> {
            moviePanel.setVisible(false); // Hide the moviePanel
            mainPanel.setVisible(true); // Show the main panel
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        moviePanel.add(backButton, gbc);

        try {
            // Create and set the movie poster
            URL url = new URL(imageUrl);
            Image image = ImageIO.read(url);
            Image resizedImage = image.getScaledInstance(400, 550, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            JLabel posterLabel = new JLabel(resizedIcon);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridheight = 5;
            gbc.gridwidth = 3;
            moviePanel.add(posterLabel, gbc);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel movieDescPanel = new JPanel(new GridBagLayout());
        movieDescPanel.setBackground(new Color(43, 43, 43));

        GridBagConstraints descLabelGbc = new GridBagConstraints();
        descLabelGbc.anchor = GridBagConstraints.WEST;
        descLabelGbc.insets = new Insets(5, 5, 5, 5);
        descLabelGbc.gridx = 0;
        descLabelGbc.gridy = 0;

        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        movieDescPanel.add(titleLabel, descLabelGbc);

        descLabelGbc.gridy++;
        JLabel yearLabel = new JLabel("Release Year: " + movie.getReleaseYear());
        yearLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        yearLabel.setForeground(Color.WHITE);
        movieDescPanel.add(yearLabel, descLabelGbc);

        descLabelGbc.gridy++;
        descLabelGbc.fill = GridBagConstraints.HORIZONTAL;
        descLabelGbc.weightx = 1.0;

        JTextArea descTextArea = new JTextArea(movie.getPlotSummary());
        descTextArea.setForeground(Color.WHITE);
        descTextArea.setFont(new Font("SansSerif", Font.BOLD, 12));
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setBackground(new Color(43, 43, 43));
        descTextArea.setEditable(false);

            JScrollPane descScrollPane = new JScrollPane(descTextArea);
            descScrollPane.setPreferredSize(new Dimension(400, 80));
            descScrollPane.setBorder(BorderFactory.createEmptyBorder());
            movieDescPanel.add(descScrollPane, descLabelGbc);


        descLabelGbc.weightx = 0.0;
        descLabelGbc.gridy++;
        JLabel directedLabel = new JLabel("Directed by " + movie.getDirector());
        directedLabel.setForeground(Color.WHITE);
        directedLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        movieDescPanel.add(directedLabel, descLabelGbc);

        descLabelGbc.gridy++;
        JLabel genreLabel = new JLabel("Genre: " + movie.getGenre());
        genreLabel.setForeground(Color.WHITE);
        genreLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        movieDescPanel.add(genreLabel, descLabelGbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridheight = 5;
        gbc.gridwidth = 4;
        moviePanel.add(movieDescPanel, gbc);

        return moviePanel;
    }


    //poster click goes to movie details
    private void handleMovieImageClick(String imageUrl) {
        // Create a new movie details panel for the clicked movie
        JPanel movieDetailsPanel = createMovieDetails(imageUrl);

        // Get the CardLayout from the cardPanel
        CardLayout cardLayout = (CardLayout) cardPanel.getLayout();

        // Add the movie details panel to the cardPanel
        cardPanel.add(movieDetailsPanel, imageUrl);

        // Show the movie details panel
        cardLayout.show(cardPanel, imageUrl);
    }


}


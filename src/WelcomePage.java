import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;

public class WelcomePage extends JFrame {
    private JPanel loginPanel;
    private JLabel welcomeLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel imageLabel;
    private JPanel loginLabel;
    private JLabel loginLabelText;

    private JPanel noAccountPanel;
    private JLabel noAccountLabel;
    private JLabel RegisterHereLabel;

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");



    public WelcomePage() {
        super("Login Page");
            // Create the login panel
        // Create the login panel
        loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(800, 900));

        loginPanel.setLayout(new GridBagLayout());

        // Create the welcome label
        welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        GridBagConstraints welcomeConstraints = new GridBagConstraints();
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 0;
        welcomeConstraints.insets = new Insets(0, 100, 150, 0);
        loginPanel.add(welcomeLabel, welcomeConstraints);

        loginLabel = new JPanel();
        loginLabel.setLayout(new FlowLayout());
        loginLabelText = new JLabel("Log in");
        loginLabelText.setFont(new Font("SansSerif", Font.BOLD, 40));
        GridBagConstraints loginConstraints = new GridBagConstraints();
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 0;
        loginConstraints.insets = new Insets(100, 0, 50, 0);
        loginLabel.add(loginLabelText);
        loginPanel.add(loginLabel, loginConstraints);


//        noAccountPanel = new JPanel();
//        noAccountPanel.setLayout(new FlowLayout());
//        noAccountLabel = new JLabel("Dont have an account?");
//        noAccountLabel.setFont(new Font("SansSerif",Font.PLAIN,25));
//        GridBagConstraints c = new GridBagConstraints();
//        c.gridx = 0;
//        c.gridy = 0;
//        noAccountPanel.add(noAccountLabel);
//        loginPanel.add(noAccountPanel,c);


        // Create the username label and field
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("",Font.PLAIN,15));
        GridBagConstraints usernameLabelConstraints = new GridBagConstraints();
        usernameLabelConstraints.gridx = 0;
        usernameLabelConstraints.gridy = 1;
        usernameLabelConstraints.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(usernameLabel, usernameLabelConstraints);

        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints usernameFieldConstraints = new GridBagConstraints();
        usernameFieldConstraints.gridx = 1;
        usernameFieldConstraints.gridy = 1;
        usernameFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        usernameFieldConstraints.insets = new Insets(10, 0, 10, 0);
        loginPanel.add(usernameField, usernameFieldConstraints);

        // Create the password label and field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("",Font.PLAIN,15));
        GridBagConstraints passwordLabelConstraints = new GridBagConstraints();
        passwordLabelConstraints.gridx = 0;
        passwordLabelConstraints.gridy = 2;
        passwordLabelConstraints.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(passwordLabel, passwordLabelConstraints);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        GridBagConstraints passwordFieldConstraints = new GridBagConstraints();
        passwordFieldConstraints.gridx = 1;
        passwordFieldConstraints.gridy = 2;
        passwordFieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        passwordFieldConstraints.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(passwordField, passwordFieldConstraints);

        // Create the login button
        loginButton = new JButton("Log in");
        Color buttonColor = new Color(65, 75, 178);
        loginButton.setPreferredSize(new Dimension(215,35));
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        GridBagConstraints loginButtonConstraints = new GridBagConstraints();
        loginButtonConstraints.gridx = 1;
        loginButtonConstraints.gridy = 3;
        loginButtonConstraints.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(loginButton, loginButtonConstraints);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText(); // Replace tfUsername with your actual JTextField for username input
                char[] passwordchar = passwordField.getPassword();
                String password = new String(passwordchar); // Replace pfPassword with your actual JPasswordField for password input
                System.out.println(password);

                boolean isLoggedIn = false;
                try {
                    isLoggedIn = login(username, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (isLoggedIn) {
                    // Perform actions for a successful login, such as navigating to the next page or showing a success message
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    HomePageTest newHomepage = new HomePageTest();
                    newHomepage.setVisible(true);
                } else {
                    // Handle incorrect login credentials, such as displaying an error message
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

            // Create the image label
            ImageIcon imageIcon = new ImageIcon("image.jpg");
            Image image = imageIcon.getImage().getScaledInstance(800, 900, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);

            imageLabel = new JLabel(imageIcon);
            imageLabel.setBounds(0, 0, 800, 900);

            // Add the components to the frame
            add(loginPanel, BorderLayout.WEST);
            add(imageLabel, BorderLayout.EAST);


            // Set frame properties
            setSize(1200, 705);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }


    public boolean login(String username, String password) throws SQLException {
        Connection conn = dbMovieManager.getDatabaseConnection();
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If the query returns any rows, the login is successful
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    }





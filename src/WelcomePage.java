import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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

    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:C:/sqlite3/OOP2/onlineMovieBooking.db", "username", "password");

    public WelcomePage() {
        super("Login Page");
        // Create the login panel
        loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(600, 900));
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints welcomeConstraints = new GridBagConstraints();

        // Create the welcome label
        welcomeLabel = new JLabel("WELCOME");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 0;
        welcomeConstraints.gridwidth = 2;
        welcomeConstraints.insets = new Insets(0, 0, 50, 0);
        loginPanel.add(welcomeLabel, welcomeConstraints);

        loginLabel = new JPanel();
        loginLabel.setLayout(new FlowLayout());
        loginLabelText = new JLabel("Log in");
        loginLabelText.setFont(new Font("SansSerif", Font.BOLD, 40));
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 1;
        welcomeConstraints.gridwidth = 1;
        welcomeConstraints.insets = new Insets(0, 0, 0, 0);
        loginLabel.add(loginLabelText);
        loginPanel.add(loginLabel, welcomeConstraints);

        noAccountPanel = new JPanel();
        noAccountPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        noAccountLabel = new JLabel("Don't have an account?");
        noAccountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JButton registerButton = new JButton("Register Here!");
        Border emptyBorder = BorderFactory.createEmptyBorder();
        registerButton.setBorder(emptyBorder);
        registerButton.setContentAreaFilled(false);
        registerButton.setForeground(Color.BLUE);
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 2;
        welcomeConstraints.gridwidth = 2;
        welcomeConstraints.anchor = GridBagConstraints.LINE_END;

        noAccountPanel.add(noAccountLabel);
        noAccountPanel.add(registerButton);
        loginPanel.add(noAccountPanel, welcomeConstraints);

        welcomeConstraints.anchor = GridBagConstraints.CENTER;

        // Create the username label and field
        usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("", Font.PLAIN, 15));
        welcomeConstraints.gridwidth = 1;
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 3;
        welcomeConstraints.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(usernameLabel, welcomeConstraints);

        usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(200, 30));
        welcomeConstraints.gridx = 1;
        welcomeConstraints.gridy = 3;
        welcomeConstraints.insets = new Insets(10, 0, 10, 0);
        loginPanel.add(usernameField, welcomeConstraints);

        // Create the password label and field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("", Font.PLAIN, 15));
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 4;
        welcomeConstraints.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(passwordLabel, welcomeConstraints);

        passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(200, 30));
        welcomeConstraints.gridx = 1;
        welcomeConstraints.gridy = 4;
        welcomeConstraints.insets = new Insets(10, 0, 10, 0);
        loginPanel.add(passwordField, welcomeConstraints);

        // Create the login button
        loginButton = new JButton("Log in");
        Color buttonColor = new Color(255, 3, 20);
        loginButton.setPreferredSize(new Dimension(215, 35));
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        welcomeConstraints.gridx = 1;
        welcomeConstraints.gridy = 5;
        welcomeConstraints.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(loginButton, welcomeConstraints);

        // RegisterButton mouseListener
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                registerButton.setContentAreaFilled(true);
                registerButton.setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                registerButton.setContentAreaFilled(true);
                registerButton.setBackground(Color.WHITE);
            }
        });

        // Login enter
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String username = usernameField.getText();
                    char[] passwordchar = passwordField.getPassword();
                    String password = new String(passwordchar);

                    boolean isLoggedIn = false;
                    try {
                        isLoggedIn = login(username, password);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    if (isLoggedIn) {
                        User loggedInUser = fetchUserDetails(username);
                        if (loggedInUser != null) {
                            if (username.equals("admin") && password.equals("1234")) {
                                JOptionPane.showMessageDialog(null, "Admin login successful!");
                                // Perform actions for a successful admin login, such as navigating to the admin homepage
                                // Replace the code below with the appropriate actions for your admin page
                                AdminHomePage adminHomePage = new AdminHomePage(loggedInUser);
                                adminHomePage.setVisible(true);
                                setVisible(false);
                            } else {
                                JOptionPane.showMessageDialog(null, "Login successful!");
                                // Perform actions for a successful user login, such as navigating to the user homepage
                                // Replace the code below with the appropriate actions for your user page
                                HomePageTest newHomepage = new HomePageTest(loggedInUser);
                                newHomepage.setVisible(true);
                                setVisible(false);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to fetch user details. Please try again.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                    }
                }
            }
        });

        // Login button click
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChar = passwordField.getPassword();
                String password = new String(passwordChar);

                boolean isLoggedIn = false;

                try {
                    isLoggedIn = login(username, password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (isLoggedIn) {
                    User loggedInUser = fetchUserDetails(username);
                    if (loggedInUser != null) {
                        if (username.equals("admin") && password.equals("1234")) {
                            JOptionPane.showMessageDialog(null, "Admin login successful!");
                            // Perform actions for a successful admin login, such as navigating to the admin homepage
                            // Replace the code below with the appropriate actions for your admin page
                            AdminHomePage adminHomePage = new AdminHomePage(loggedInUser);
                            adminHomePage.setVisible(true);
                            setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Login successful!");
                            // Perform actions for a successful user login, such as navigating to the user homepage
                            // Replace the code below with the appropriate actions for your user page
                            HomePageTest newHomepage = new HomePageTest(loggedInUser);
                            newHomepage.setVisible(true);
                            setVisible(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to fetch user details. Please try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
                }
            }
        });

        // RegisterButton action listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterPage newRegister = new RegisterPage();
                newRegister.setVisible(true);
                setVisible(false);
            }
        });

        // Create the image label
        ImageIcon imageIcon = new ImageIcon("C://Users//Liden//Desktop//blckwidowposter.jpg");
        Image image = imageIcon.getImage().getScaledInstance(400, 500, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);

        imageLabel = new JLabel(imageIcon);
        // Create an empty border with padding
        Border emptyBorder1 = BorderFactory.createEmptyBorder(0, 0, 0, 50);
        imageLabel.setBorder(emptyBorder1);

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

    private User fetchUserDetails(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = dbMovieManager.getDatabaseConnection();
            String query = "SELECT * FROM user WHERE username = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String password = rs.getString("password");
                String emailAddress = rs.getString("email_address");

                User user = new User(userId, firstName, lastName, username, password, emailAddress);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database resources in the reverse order of their creation
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}

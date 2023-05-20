import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class RegisterPage extends JFrame {
    boolean valid = true;
    MovieDatabaseManager dbMovieManager = new MovieDatabaseManager("jdbc:sqlite:D:/oop2final/onlineMovieBooking.db","username", "password");

    public RegisterPage() {
        // Set up the registration page

        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 705);
        setLocationRelativeTo(null);

        // Create a JPanel to hold the components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.WEST;

        JPanel newUserPanel  = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel newuser = new JLabel("New User Registration");
        newuser.setFont(new Font("SansSerif",Font.PLAIN,30));
        newUserPanel.add(newuser);
        panel.add(newUserPanel);

        // Add the components to the panel
        JPanel firstLastNamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel firstNameLabel = new JLabel("First Name");
        JTextField firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(200,30));

        JLabel lastNameLabel = new JLabel("Last Name");
        JTextField lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(200,30));
        firstLastNamePanel.add(firstNameLabel);
        firstLastNamePanel.add(firstNameField,c);
        firstLastNamePanel.add(lastNameLabel);
        firstLastNamePanel.add(lastNameField);
        panel.add(firstLastNamePanel,c);


        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = new JLabel("Username");
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(473,30));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        panel.add(usernamePanel,c);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel emailLabel = new JLabel("Email address");
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(450,30));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        panel.add(emailPanel,c);


        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(473,30));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        panel.add(passwordPanel,c);


        JButton registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(550,40));
        Color buttonColor = new Color(65, 75, 178);
        registerButton.setBackground(buttonColor);
        registerButton.setForeground(Color.WHITE);


        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String emailAddress = emailField.getText();
                boolean valid = true;
                if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty()) {
                    valid = false;
                }

                if (valid) {
                    try {
                        boolean success = registerUser(username, password, firstName, lastName, emailAddress);
                        if (success) {
                            usernameField.setText("");
                            passwordField.setText("");
                            firstNameField.setText("");
                            lastNameField.setText("");
                            emailField.setText("");
                            JOptionPane.showMessageDialog(null, "Registration successful!");
                            // User registration successful
                            // Show a success message or navigate to the next page
                        } else {
                            JOptionPane.showMessageDialog(null, "Username is taken. Registration failed. Please try again.");

                            // User registration failed because the username already exists
                            // Show an error message
                        }
                    } catch (SQLException ex) {
                        // Handle the SQLException appropriately
                        ex.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Please fill out all fields.");
                }
            }
        });


        JLabel loginLabel = new JLabel("Already have an account? ");
        JButton loginButton = new JButton("Log in now");
        Border emptyBorder = BorderFactory.createEmptyBorder();
        loginButton.setBorder(emptyBorder);
        loginButton.setForeground(Color.BLUE);
        loginButton.setContentAreaFilled(false);

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                loginButton.setContentAreaFilled(true);
                loginButton.setBackground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                loginButton.setContentAreaFilled(true);
                loginButton.setBackground(Color.WHITE);

            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomePage newWelcomePage = new WelcomePage();
                newWelcomePage.setVisible(true);
                setVisible(false);
            }
        });


        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.add(registerButton);
        panel.add(registerPanel,c);

        // Create a JPanel to hold the login link
        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.add(loginLabel);
        loginPanel.add(loginButton);

        // Add the panels to the frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(loginPanel, BorderLayout.SOUTH);
    }



    public boolean registerUser(String username, String password, String firstName,String  lastName, String email) throws SQLException {
        // Check if the username already exists
        Connection conn = dbMovieManager.getDatabaseConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT username FROM user WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            // The username already exists, so return false
            return false; // if statement in Web page, where it pops up " Username already taken" if it returns false
        }

        // Insert the new user's information into the user table
        stmt = conn.prepareStatement("INSERT INTO user (first_name, last_name, username, password,email_address) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        stmt.setString(3, username);
        stmt.setString(4, password);
        stmt.setString(5, email);
        stmt.executeUpdate();
        return true;
    }

}

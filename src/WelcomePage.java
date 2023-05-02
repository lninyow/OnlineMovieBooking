import java.awt.*;
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

    public WelcomePage() {
        super("Login Page");
            // Create the login panel
            loginPanel = new JPanel();
            loginPanel.setPreferredSize(new Dimension(800, 900));
            loginPanel.setLayout(null);

            // Create the welcome label
            welcomeLabel = new JLabel("WELCOME");
            welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
            welcomeLabel.setBounds(300, 50, 200, 50);
            loginPanel.add(welcomeLabel);

            // Create the username label and field
            usernameLabel = new JLabel("Username:");
            usernameLabel.setBounds(150, 150, 100, 25);
            loginPanel.add(usernameLabel);

            usernameField = new JTextField();
            usernameField.setBounds(250, 150, 300, 25);
            loginPanel.add(usernameField);

            // Create the password label and field
            passwordLabel = new JLabel("Password:");
            passwordLabel.setBounds(150, 200, 100, 25);
            loginPanel.add(passwordLabel);

            passwordField = new JPasswordField();
            passwordField.setBounds(250, 200, 300, 25);
            loginPanel.add(passwordField);

            // Create the login button
            loginButton = new JButton("Log in");
            loginButton.setBounds(250, 250, 100, 25);
            loginPanel.add(loginButton);

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
            setSize(1920, 1080);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }
    }




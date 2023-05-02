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
        welcomeConstraints.insets = new Insets(0, 100, 50, 0);
        loginPanel.add(welcomeLabel, welcomeConstraints);

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




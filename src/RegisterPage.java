import javax.swing.*;
import java.awt.*;

public class RegisterPage extends JFrame {
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
        JLabel loginLabel = new JLabel("Already have an account? ");
        JButton loginButton = new JButton("Log in now");
        loginButton.setForeground(buttonColor);
        loginButton.setBorder(null);


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

}

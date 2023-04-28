    import javax.swing.*;
    import java.awt.event.*;
    
    public class Main {
    public static void main(String[] args) {
        JFrame newFrame = new JFrame("Test");
        newFrame.setSize(1920,1080);

        JLabel label = new JLabel("Hello World!");
        label.setBounds(50, 50, 100, 30);
        newFrame.add(label);

        JButton button = new JButton("Click Me!");
        button.setBounds(50, 100, 100, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("Button Clicked!");
            }
        });
        newFrame.add(button);

        newFrame.setLayout(null);
        newFrame.setVisible(true);
    }
}
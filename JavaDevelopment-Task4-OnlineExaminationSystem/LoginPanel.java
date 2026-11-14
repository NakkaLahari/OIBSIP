import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    JTextField userField;
    JPasswordField passField;

    Color blue = new Color(35, 105, 190);

    public LoginPanel(Main main) {

        setLayout(new GridBagLayout());
        setBackground(new Color(235, 243, 255));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 225, 245)),
            BorderFactory.createEmptyBorder(45, 50, 45, 50)
        ));

        JLabel title = new JLabel("ONLINE EXAMINATION");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(new Color(25, 85, 160));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("STUDENT LOGIN PORTAL");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel userLabel = new JLabel("Username");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        userField = new JTextField();
        userField.setMaximumSize(new Dimension(280, 40));
        userField.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel passLabel = new JLabel("Password");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));

        passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(280, 40));
        passField.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton loginButton = new JButton("LOGIN");
        loginButton.setMaximumSize(new Dimension(280, 42));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(blue);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel footer = new JLabel("Welcome! Best of luck for your exam.");
        footer.setForeground(Color.GRAY);
        footer.setFont(new Font("Arial", Font.ITALIC, 12));
        footer.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(35));

        card.add(userLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(userField);
        card.add(Box.createVerticalStrut(20));

        card.add(passLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(passField);
        card.add(Box.createVerticalStrut(30));

        card.add(loginButton);
        card.add(Box.createVerticalStrut(20));
        card.add(footer);

        add(card);

        loginButton.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            main.login(u, p);
        });
    }

    public void clearFields() {
        userField.setText("");
        passField.setText("");
    }
}
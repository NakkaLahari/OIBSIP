import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {

    JTextField nameField;
    JPasswordField passwordField;

    Color blue = new Color(35, 105, 190);

    public ProfilePanel(Main main) {

        setLayout(new GridBagLayout());
        setBackground(new Color(235, 243, 255));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 225, 245)),
            BorderFactory.createEmptyBorder(40, 50, 40, 50)
        ));

        JLabel title = new JLabel("STUDENT PROFILE");
        title.setFont(new Font("Arial", Font.BOLD, 25));
        title.setForeground(new Color(25, 85, 160));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Update your details before the exam");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("Display Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(280, 40));

        JLabel passLabel = new JLabel("New Password");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(280, 40));

        JButton startButton = new JButton("SAVE & START EXAM");
        startButton.setMaximumSize(new Dimension(280, 42));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBackground(blue);
        startButton.setForeground(Color.WHITE);
        startButton.setFont(new Font("Arial", Font.BOLD, 14));
        startButton.setFocusPainted(false);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.add(title);
        card.add(Box.createVerticalStrut(10));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        card.add(nameLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(nameField);
        card.add(Box.createVerticalStrut(20));

        card.add(passLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(passwordField);
        card.add(Box.createVerticalStrut(30));

        card.add(startButton);

        add(card);

        startButton.addActionListener(e -> {

            String name = nameField.getText();
            String pass = new String(passwordField.getPassword());

            main.updateProfile(name, pass);
        });
    }

    public void setProfile(String name) {
        nameField.setText(name);
        passwordField.setText("");
    }
}
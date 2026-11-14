import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {

    Main main;

    JLabel titleLabel;
    JLabel scoreLabel;
    JLabel timeLabel;
    JTextArea resultArea;

    public ResultPanel(Main main) {

        this.main = main;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(235, 243, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        titleLabel = new JLabel("EXAMINATION RESULT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setForeground(new Color(25, 85, 160));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel summary = new JPanel();
        summary.setLayout(new BoxLayout(summary, BoxLayout.Y_AXIS));
        summary.setBackground(Color.WHITE);
        summary.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 23));
        scoreLabel.setForeground(new Color(20, 145, 90));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setForeground(Color.DARK_GRAY);
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        summary.add(scoreLabel);
        summary.add(Box.createVerticalStrut(12));
        summary.add(timeLabel);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setBackground(Color.WHITE);
        resultArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(resultArea);

        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setBackground(new Color(35, 105, 190));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setFocusPainted(false);
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(150, 42));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(235, 243, 255));
        bottom.add(logoutButton);

        logoutButton.addActionListener(e -> main.logout());

        JPanel center = new JPanel(new BorderLayout(10, 10));
        center.setBackground(new Color(235, 243, 255));
        center.add(summary, BorderLayout.NORTH);
        center.add(scroll, BorderLayout.CENTER);

        add(titleLabel, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    public void displayResult(int timeTaken) {

        int score = 0;
        int correct = 0;
        int incorrect = 0;
        int unanswered = 0;

        StringBuilder details = new StringBuilder();

        for (int i = 0; i < main.questions.length; i++) {

            Question q = main.questions[i];

            details.append("Question ").append(i + 1).append(": ")
                   .append(q.question).append("\n");

            if (main.answers[i] == q.correctAnswer) {

                score++;
                correct++;

                details.append("Status: CORRECT\n");

            } else if (main.answers[i] == -1) {

                unanswered++;

                details.append("Status: NOT ANSWERED\n");

            } else {

                incorrect++;

                details.append("Status: INCORRECT\n");
            }

            details.append("Correct Answer: ")
                   .append(q.options[q.correctAnswer])
                   .append("\n");

            details.append("----------------------------------------\n");
        }

        scoreLabel.setText(
            "YOUR SCORE: " + score + " / " + main.questions.length
        );

        int minutes = timeTaken / 60;
        int seconds = timeTaken % 60;

        timeLabel.setText(String.format(
            "Time Taken: %02d minutes %02d seconds", minutes, seconds
        ));

        details.insert(0,
            "CORRECT ANSWERS: " + correct +
            "\nINCORRECT ANSWERS: " + incorrect +
            "\nUNANSWERED: " + unanswered +
            "\n\n========================================\n\n"
        );

        resultArea.setText(details.toString());
        resultArea.setCaretPosition(0);
    }
}
import javax.swing.*;
import java.awt.*;

public class ExamPanel extends JPanel {

    Main main;

    JLabel titleLabel;
    JLabel timerLabel;
    JLabel questionLabel;
    JLabel numberLabel;

    JRadioButton[] options = new JRadioButton[4];
    ButtonGroup group = new ButtonGroup();

    JButton previousButton;
    JButton nextButton;
    JButton submitButton;

    boolean loading = false;

    Color blue = new Color(35, 105, 190);

    public ExamPanel(Main main) {

        this.main = main;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(235, 243, 255));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(blue);
        header.setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));

        titleLabel = new JLabel("ONLINE EXAMINATION");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        timerLabel = new JLabel("Time Left: 01:00");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        header.add(titleLabel, BorderLayout.WEST);
        header.add(timerLabel, BorderLayout.EAST);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(35, 40, 35, 40));

        numberLabel = new JLabel();
        numberLabel.setForeground(blue);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 17));
        numberLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.BOLD, 19));
        questionLabel.setForeground(new Color(40, 50, 65));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(numberLabel);
        content.add(Box.createVerticalStrut(25));
        content.add(questionLabel);
        content.add(Box.createVerticalStrut(30));

        for (int i = 0; i < 4; i++) {

            options[i] = new JRadioButton();
            options[i].setFont(new Font("Arial", Font.PLAIN, 16));
            options[i].setBackground(Color.WHITE);
            options[i].setFocusPainted(false);
            options[i].setAlignmentX(Component.LEFT_ALIGNMENT);

            group.add(options[i]);
            content.add(options[i]);
            content.add(Box.createVerticalStrut(18));

            final int index = i;

            options[i].addActionListener(e -> {
                if (!loading) {
                    main.setAnswer(main.currentQuestion, index);
                }
            });
        }

        JPanel navigation = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        navigation.setBackground(new Color(235, 243, 255));

        previousButton = new JButton("PREVIOUS");
        nextButton = new JButton("NEXT");
        submitButton = new JButton("SUBMIT EXAM");

        styleButton(previousButton, new Color(100, 120, 145));
        styleButton(nextButton, blue);
        styleButton(submitButton, new Color(20, 145, 90));

        navigation.add(previousButton);
        navigation.add(nextButton);
        navigation.add(submitButton);

        previousButton.addActionListener(e -> {
            if (main.currentQuestion > 0) {
                main.currentQuestion--;
                loadQuestion();
            }
        });

        nextButton.addActionListener(e -> {
            if (main.currentQuestion < main.questions.length - 1) {
                main.currentQuestion++;
                loadQuestion();
            }
        });

        submitButton.addActionListener(e -> main.submitExam(true));

        add(header, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(navigation, BorderLayout.SOUTH);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(145, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void loadQuestion() {

        loading = true;

        int index = main.currentQuestion;
        Question q = main.questions[index];

        numberLabel.setText(
            "QUESTION " + (index + 1) + " OF " + main.questions.length
        );

        questionLabel.setText(
            "<html><body style='width:650px'>" + q.question + "</body></html>"
        );

        group.clearSelection();

        for (int i = 0; i < 4; i++) {
            options[i].setText(q.options[i]);
            options[i].setSelected(main.answers[index] == i);
        }

        previousButton.setEnabled(index > 0);
        nextButton.setEnabled(index < main.questions.length - 1);

        updateTimer();

        loading = false;
    }

    public void updateTimer() {

        int minutes = main.remainingSeconds / 60;
        int seconds = main.remainingSeconds % 60;

        timerLabel.setText(String.format(
            "Time Left: %02d:%02d", minutes, seconds
        ));

        if (main.remainingSeconds <= 10) {
            timerLabel.setForeground(Color.YELLOW);
        } else {
            timerLabel.setForeground(Color.WHITE);
        }
    }
}
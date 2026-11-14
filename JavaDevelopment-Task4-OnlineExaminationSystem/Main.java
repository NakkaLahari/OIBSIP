import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    CardLayout cl;
    JPanel mainPanel;

    LoginPanel loginPanel;
    ProfilePanel profilePanel;
    ExamPanel examPanel;
    ResultPanel resultPanel;

    String username = "student";
    String password = "1234";
    String displayName = "Student";

    Question[] questions;
    int[] answers;
    int currentQuestion = 0;

    Timer timer;
    int remainingSeconds = 60;
    int examDuration = 60;
    boolean examRunning = false;

    public Main() {

        setTitle("Online Examination System");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        questions = createQuestions();
        answers = new int[questions.length];

        cl = new CardLayout();
        mainPanel = new JPanel(cl);

        loginPanel = new LoginPanel(this);
        profilePanel = new ProfilePanel(this);
        examPanel = new ExamPanel(this);
        resultPanel = new ResultPanel(this);

        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(profilePanel, "PROFILE");
        mainPanel.add(examPanel, "EXAM");
        mainPanel.add(resultPanel, "RESULT");

        add(mainPanel);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {

                if (examRunning) {
                    int choice = JOptionPane.showConfirmDialog(
                        Main.this,
                        "Are you sure you want to quit the exam?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        timer.stop();
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        });

        showScreen("LOGIN");
    }

    public Question[] createQuestions() {

        return new Question[] {

            new Question("Which language is used for Android development?",
                new String[]{"Java", "HTML", "CSS", "SQL"}, 0),

            new Question("Which is not an OOP concept?",
                new String[]{"Compilation", "Inheritance", "Polymorphism", "Encapsulation"}, 0),

            new Question("Which keyword creates an object in Java?",
                new String[]{"new", "class", "void", "static"}, 0),

            new Question("Which method is the entry point of Java?",
                new String[]{"main()", "start()", "run()", "init()"}, 0),

            new Question("Which package contains Swing classes?",
                new String[]{"javax.swing", "java.sql", "java.net", "java.io"}, 0),

            new Question("Which component is used for a button in Swing?",
                new String[]{"JButton", "JLabel", "JFrame", "JTextField"}, 0),

            new Question("Which keyword is used for inheritance?",
                new String[]{"extends", "implements", "import", "package"}, 0),

            new Question("Which data type stores true or false?",
                new String[]{"boolean", "int", "char", "float"}, 0),

            new Question("Which loop executes at least once?",
                new String[]{"do-while", "for", "while", "nested for"}, 0),

            new Question("Which collection allows duplicate elements?",
                new String[]{"ArrayList", "Set", "HashSet", "TreeSet"}, 0)
        };
    }

    public void showScreen(String screen) {
        cl.show(mainPanel, screen);
    }

    public void login(String u, String p) {

        if (u.equals(username) && p.equals(password)) {
            profilePanel.setProfile(displayName);
            showScreen("PROFILE");
        } else {
            JOptionPane.showMessageDialog(
                this, "Invalid username or password!"
            );
        }
    }

    public void updateProfile(String name, String newPass) {

        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter your display name.");
            return;
        }

        displayName = name.trim();

        if (!newPass.trim().isEmpty()) {
            password = newPass;
        }

        startExam();
    }

    public void startExam() {

        currentQuestion = 0;

        for (int i = 0; i < answers.length; i++) {
            answers[i] = -1;
        }

        remainingSeconds = examDuration;
        examRunning = true;

        examPanel.loadQuestion();
        showScreen("EXAM");

        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(1000, e -> {

            remainingSeconds--;

            examPanel.updateTimer();

            if (remainingSeconds <= 0) {
                timer.stop();

                JOptionPane.showMessageDialog(
                    this, "Time is over! Your exam will be submitted."
                );

                submitExam(false);
            }
        });

        timer.start();
    }

    public void setAnswer(int question, int option) {
        answers[question] = option;
    }

    public void submitExam(boolean manual) {

        if (manual) {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to submit your exam?",
                "Submit Confirmation",
                JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }
        }

        if (timer != null) {
            timer.stop();
        }

        examRunning = false;

        int timeTaken = examDuration - remainingSeconds;

        resultPanel.displayResult(timeTaken);
        showScreen("RESULT");
    }

    public void logout() {

        if (timer != null) {
            timer.stop();
        }

        examRunning = false;
        loginPanel.clearFields();

        showScreen("LOGIN");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Main m = new Main();
            m.setVisible(true);
        });
    }
}
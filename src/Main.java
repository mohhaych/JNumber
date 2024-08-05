import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Main {

    private static final int MAX_ATTEMPTS = 10;
    private static String target;
    private static int attemptsLeft;

    public static void main(String[] args) {
        target = getTarget();
        attemptsLeft = MAX_ATTEMPTS;

        // Create the GUI
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    // Generates the target number and prints it to the console
    public static String getTarget() {
        Random r = new Random();
        StringBuilder target = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            target.append(r.nextInt(10));
        }
        // Print target number for marking
        System.out.println("Target Number: " + target);
        return target.toString();
    }

    // Creates and shows the GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("JNumber Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        JLabel instructionLabel = new JLabel("Enter a 5-digit number:");
        panel.add(instructionLabel);

        JTextField guessField = new JTextField();
        panel.add(guessField);

        JButton submitButton = new JButton("Submit Guess");
        panel.add(submitButton);

        JTextArea feedbackArea = new JTextArea(10, 30);
        feedbackArea.setEditable(false);
        panel.add(new JScrollPane(feedbackArea));

        JLabel attemptsLabel = new JLabel("Attempts Left: " + attemptsLeft);
        panel.add(attemptsLabel);

        JButton resetButton = new JButton("New Game");
        panel.add(resetButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guess = guessField.getText().trim();
                handleGuess(guess, feedbackArea, attemptsLabel);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                target = getTarget();
                attemptsLeft = MAX_ATTEMPTS;
                feedbackArea.setText("");
                attemptsLabel.setText("Attempts Left: " + attemptsLeft);
                guessField.setText("");
            }
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // Handles the guess logic and updates feedback
    private static void handleGuess(String guess, JTextArea feedbackArea, JLabel attemptsLabel) {
        if (guess.length() != 5 || !guess.matches("\\d+")) {
            feedbackArea.append("Invalid input. Please enter a 5-digit number.\n");
            return;
        }

        attemptsLeft--;

        if (guess.equals(target)) {
            feedbackArea.append("Congratulations! You guessed the number.\n");
            return;
        }

        StringBuilder feedback = new StringBuilder("Feedback: ");
        for (int i = 0; i < 5; i++) {
            char guessDigit = guess.charAt(i);
            char targetDigit = target.charAt(i);

            if (guessDigit == targetDigit) {
                feedback.append(guessDigit).append(" ");
            } else if (guessDigit < targetDigit) {
                feedback.append("Low ");
            } else {
                feedback.append("High ");
            }
        }
        feedback.append("\n");
        feedbackArea.append(feedback.toString());

        if (attemptsLeft <= 0) {
            feedbackArea.append("Game Over! The number was: " + target + "\n");
        } else {
            attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        }
    }
}

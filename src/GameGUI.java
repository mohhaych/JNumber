import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI {
    private final GameLogic gameLogic;
    private final JFrame frame;
    private final JTextField guessField;
    private final JTextArea feedbackArea;

    public GameGUI(GameLogic gameLogic) {
        this.gameLogic = gameLogic;

        frame = new JFrame("JNumber Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel instructionLabel = new JLabel("Enter your 5-digit guess:");
        panel.add(instructionLabel);

        guessField = new JTextField();
        panel.add(guessField);

        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(new GuessButtonListener());
        panel.add(guessButton);

        feedbackArea = new JTextArea();
        feedbackArea.setEditable(false);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(feedbackArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guess = guessField.getText().trim();
            if (guess.length() != 5 || !guess.matches("\\d{5}")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid 5-digit number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!gameLogic.isValidGuess(guess)) {
                JOptionPane.showMessageDialog(frame, "Guess is not in the valid dictionary.", "Invalid Guess", JOptionPane.ERROR_MESSAGE);
                return;
            }

            gameLogic.incrementAttempts();
            String feedback = gameLogic.checkGuess(guess);
            feedbackArea.append("Attempt " + gameLogic.getAttempts() + ": " + guess + " -> " + feedback + "\n");

            if (gameLogic.isCorrectGuess(guess)) {
                feedbackArea.append("Congratulations! You've guessed the number in " + gameLogic.getAttempts() + " attempts.\n");
                guessField.setEnabled(false);
            } else if (gameLogic.getAttempts() >= 10) {
                feedbackArea.append("Game Over! You've used all your attempts. The correct number was " + gameLogic.getTargetNumber() + ".\n");
                guessField.setEnabled(false);
            }
        }
    }
}

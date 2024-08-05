import java.util.List;

public class GameLogic {
    private final String targetNumber;
    private final List<String> gameDictionary;
    private int attempts;

    public GameLogic(String targetNumber, List<String> gameDictionary) {
        this.targetNumber = targetNumber;
        this.gameDictionary = gameDictionary;
        this.attempts = 0;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        attempts++;
    }

    public boolean isValidGuess(String guess) {
        return gameDictionary.contains(guess);
    }

    public String checkGuess(String guess) {
        StringBuilder feedback = new StringBuilder();
        for (int i = 0; i < targetNumber.length(); i++) {
            int guessDigit = Character.getNumericValue(guess.charAt(i));
            int targetDigit = Character.getNumericValue(targetNumber.charAt(i));
            if (guessDigit > targetDigit) {
                feedback.append("H"); // High
            } else if (guessDigit < targetDigit) {
                feedback.append("L"); // Low
            } else {
                feedback.append("C"); // Correct
            }
        }
        return feedback.toString();
    }

    public boolean isCorrectGuess(String guess) {
        return guess.equals(targetNumber);
    }
}

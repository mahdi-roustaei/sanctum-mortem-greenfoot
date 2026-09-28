import greenfoot.*;
import java.util.List;

public class HighscoreScreen extends World {
    public HighscoreScreen() {
        super(1200, 800, 1);
        displayHighscores();
    }

    private void displayHighscores() {
        List<String> highscores = HighscoreManager.getHighscores();
        int y = 200;
        showText("Highscores:", getWidth() / 2, y);
        y += 40;
        int rank = 1;
        for (String entry : highscores) {
            showText(rank + ". " + entry, getWidth() / 2, y);
            y += 30;
            rank++;
        }
    }
}

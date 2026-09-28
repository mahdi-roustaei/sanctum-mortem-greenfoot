import greenfoot.*;
import java.util.List;

public class GameOver extends World {
    private int timer = 300; // 5 seconds timer (60 fps)

    public GameOver(int score, String playerName) {
        super(1200, 800, 1);
        HighscoreManager.addHighscore(playerName, score); // Save the score
    }
    public void act() {
        timer--;
        if (timer == 0) {
            Greenfoot.setWorld(new HighscoreScreen()); // Show highscore table
            Greenfoot.playSound("highscore.wav");
        }
    }
}
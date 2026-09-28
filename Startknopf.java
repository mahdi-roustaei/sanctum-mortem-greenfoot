import greenfoot.*;
import javax.swing.JOptionPane;

public class Startknopf extends Actor {
    public static String playerName = "Player"; // Default player name
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            playerName = JOptionPane.showInputDialog("Enter your name:");
            if (playerName == null || playerName.trim().isEmpty()) {
                playerName = "Player"; // Set a default name if empty
            }
            Greenfoot.setWorld(new Level2(playerName)); // Start Level2 with player name
        }
    }
}



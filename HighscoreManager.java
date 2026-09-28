import java.io.*;
import java.util.*;
public class HighscoreManager {
    private static final String FILE_NAME = "highscores.txt";

    public static List<String> getHighscores() {
        List<String> highscores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highscores.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading highscores.");
        }
        return highscores;
    }

    public static void addHighscore(String playerName, int score) {
        List<String> highscores = getHighscores();
        Map<String, Integer> scoreMap = new HashMap<>();
        // Parse existing highscores into a Map (player name -> score)
        for (String entry : highscores) {
            String[] parts = entry.split(" : ");
            String name = parts[0];
            int existingScore = Integer.parseInt(parts[1]);
            scoreMap.put(name, Math.max(scoreMap.getOrDefault(name, 0), existingScore)); // Keep the highest score
        }
        // Update the player's score if the new score is higher
        if (scoreMap.containsKey(playerName)) {
            if (score > scoreMap.get(playerName)) {
                scoreMap.put(playerName, score); // Update to the higher score
            }
        } else {
            scoreMap.put(playerName, score); // Add new player score
        }
        // Convert Map to a List and Sort by Score in Descending Order
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scoreMap.entrySet());
        sortedScores.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        // Save the sorted scores back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : sortedScores) {
                writer.write(entry.getKey() + " : " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving highscores.");
        }
    }
}


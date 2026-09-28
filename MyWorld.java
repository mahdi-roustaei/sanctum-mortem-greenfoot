import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The main game world where the game takes place.
 */
public class MyWorld extends World {
    private final Player player = new Player();
    private final int enemySpawnDistanceFromPlayer = 1000;
    public static int highscore = 0;
    private String playerName;

    /**
     * Constructor for objects of class MyWorld.
     * Accepts the player name as a parameter.
     */
    public MyWorld() {
    super(1200, 800, 1);
    highscore = 0;
    addObject(player, getWidth() / 2, getHeight() / 2);
}
    public MyWorld(String playerName) {
    this(); // Call the default constructor
    System.out.println("Player name: " + playerName);
}

    /**
     * Game loop logic.
     */
    public void act() {

        showHighscore(); // Continuously show the highscore
        if (player.isDead()) { // Check if the player has died
            Greenfoot.setWorld(new GameOver(highscore, playerName)); // Transition to GameOver screen
        }
    }

    /**
     * Add points to the highscore.
     */
    public void addHighscore(int score) {
        highscore += score;
    }

    /**
     * Display the highscore on the screen.
     */
    public void showHighscore() {
        showText("Highscore: " + highscore, getWidth() - 90, 20);
    }

    /**
     * Spawn a skeleton enemy.
     */
    public void spawnSkeleton(World world) {
        addObject(new Skeleton(getPlayer()), 
            EssentialFunctions.getRandomNumberInversion() * getPlayer().getX() + enemySpawnDistanceFromPlayer, 
            EssentialFunctions.getRandomNumberInversion() * getPlayer().getY() + enemySpawnDistanceFromPlayer
        );
    }

    /**
     * Spawn an archer enemy.
     */
    public void spawnArcher(World world) {
        int x = EssentialFunctions.getRandomNumberInversion() * getPlayer().getX() + enemySpawnDistanceFromPlayer;
        int y = EssentialFunctions.getRandomNumberInversion() * getPlayer().getY() + enemySpawnDistanceFromPlayer;
        addObject(new Archer(getPlayer()), x, y);
    }

    /**
     * Spawn an orc enemy.
     */
    public void spawnOrc(World world) {
        addObject(
            new Orc(getPlayer()), 
            EssentialFunctions.getRandomNumberInversion() * getPlayer().getX() + enemySpawnDistanceFromPlayer, 
            EssentialFunctions.getRandomNumberInversion() * getPlayer().getY() + enemySpawnDistanceFromPlayer
        );
    }

    /**
     * Get the player object.
     */
    public Player getPlayer() {
        return player;
    }
}

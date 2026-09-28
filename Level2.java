import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
    public class Level2 extends MyWorld {
    private static double ENEMY_SPAWN_CHANCE = 0.5;
    private GreenfootSound bgm = new GreenfootSound("pixelBGM.mp3");
    public Level2(String playerName) {
        super(playerName); // Pass the player name to MyWorld
        addObject(getPlayer(), 250, 250);
        setBackground(new GreenfootImage("images/background/Dirt_01.png"));

        // Spawn initial objects for testing
        addObject(new Archer(getPlayer()), 250, 750);
        addObject(new Orc(getPlayer()), 500, 800);
        addObject(new SpeedBoost(), 250, 700);
        addObject(new HealingPotion(), 500, 700);

        ENEMY_SPAWN_CHANCE = 0.5;
    }

        public void act() {
        showHighscore();
        bgm.playLoop();
        bgm.setVolume(50);
        /**
         * DEBUGGING: Spawnrate direkt auf Bildschirm anzeigen, weil ich kein Bock mehr habe, zwischen Terminal und SPiel hin und her zu wechseln
         */
        //showText("Spawn Chance: " + ENEMY_SPAWN_CHANCE, 150, 70);
    
        /**
         * if-Statement zum Aufruf der spawnSkeleton() - Methode unter Berücksichtigung der derzeitigen Spawnrate
         */
        
        double TGT_RANDOM_NR = (int)EssentialFunctions.getRandomNr(100.0);
        
        if (TGT_RANDOM_NR < ENEMY_SPAWN_CHANCE) {
            spawnSkeleton(this);
        }
        // Spawn Archer, wenn Highscore >= 35
        if (MyWorld.highscore >= 20 && EssentialFunctions.getRandomNr(150.0) < 1) { // Seltener Spawn
            spawnArcher(this);
        }
        // spawn Orc, wenn Highscore >= 20
        if (MyWorld.highscore >= 30 && EssentialFunctions.getRandomNr(180.0) < 1) { // Seltener Spawn
            spawnOrc(this);
        }
    }
    /**
     * Methode zur Erhöhung der Gegner-Spawnwahrscheinlichkeit um einen angegebenen Parameter-Wert.
     * Wird von den Gegner-Klassen (bspw. Skeleton-Klasse) aus aufgerufen, sobald diese gekillt werden
     */
    
    public static double raiseSpawnRate(double valuePerKill) {
        
        ENEMY_SPAWN_CHANCE = EssentialFunctions.roundSpawnRate(ENEMY_SPAWN_CHANCE + valuePerKill);
            
        System.out.println("Spawn Rate: " + ENEMY_SPAWN_CHANCE);
        
        return ENEMY_SPAWN_CHANCE;
    }
}

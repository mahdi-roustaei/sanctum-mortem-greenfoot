import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * Player-Klasse: Enthält den Code für die Lebensleiste, Schadenserkennung durch Gegner, Steuerung des Spielers, Berechnung der Schussrate des Spielers
 * und Getter für die x- und y Positionen des Spielers
 */

import java.io.File;
import java.io.IOException;


public class Player extends Entity {
    
    MyWorld myWorld;
    
    /**
     * Konstanten zur Berechnung der Schussrate des Spielers und dessen Laufgeschwindigkeit
     */
    
    private final int INIT_FPS = 30;
    private final int SHOTS_PER_SECOND = INIT_FPS / 1;
    private int playerSpeed = 4;
    private int shotsTimer = 0;
    private final int maxHealth = 10;
    private int health = 10;
    
    /**
     * Variablen und Konstanten für die Animation des Spielers
     */
    
    private int animationClock = 0;
    private int animationIndexRunning = 0;
    private int animationClockIdle = 0;
    private int animationIndexIdle = 0;
    private int animationClockSpells = 0;
    private int animationIndexSpells = 0;
    private final int ANIMATION_SPEED_IDLE = 20;
    private final int ANIMATION_SPEED = 5;
    private final int ANIMATION_SPEED_SPELLS = 3;
    private final int PLAYER_SIZE = 3;
    private boolean isRunning = false;
    private boolean facingLeft = false;
    private boolean isCastingSpells = false;
    
    /**
     * Sounds lol
     */
    GreenfootSound step = new GreenfootSound("step.mp3");
    private int stepCounter = 0;
    /**
     * Variablen für die CheckTouchByEnemy - Methode
     */

    private final GreenfootImage[] playerRunningAnimationRight;
    private final GreenfootImage[] playerRunningAnimationLeft;
    private final GreenfootImage[] playerIdleAnimationRight;
    private final GreenfootImage[] playerIdleAnimationLeft;
    private final GreenfootImage[] playerCastingSpellRight;
    private final GreenfootImage[] playerCastingSpellLeft;
    
    private int cooldownCounter = 0;
    private final int COOLDOWN_SECONDS = 3;
    private boolean isDamageCooldownActive = false;
    private boolean gameOver = false;
    
    public Player() {
        
        playerRunningAnimationRight = prepareSprites(PLAYER_SIZE, false, "images/mage/tile", 134, 139);
        playerRunningAnimationLeft = prepareSprites(PLAYER_SIZE, true, "images/mage/tile", 134, 139);
        playerIdleAnimationRight = prepareSprites(PLAYER_SIZE, false, "images/mage/idle/tile", 001, 003);
        playerIdleAnimationLeft = prepareSprites(PLAYER_SIZE, true, "images/mage/idle/tile", 001, 003);
        playerCastingSpellRight = prepareSprites(PLAYER_SIZE, false, "images/mage/castingspells/tile", 104, 107);
        playerCastingSpellLeft = prepareSprites(PLAYER_SIZE, true, "images/mage/castingspells/tile", 104, 107);
        
        setImage(playerRunningAnimationRight[0]);
    }
    /*condition for the highscore table */
      public boolean isDead() {
        return health <= 0; // Player dies when health is 0
    }
    /**
     * displayHealth() : Wird fortlaufend 30x/Sek durch die act()-Methode ausgeführt.
     * Dient der Darstellung der aktuellen Lebensleiste und aktualisiert dessen Stand
     */
    
    public void displayHealth() {
        
        for (Heart heart : getWorld().getObjects(Heart.class)) {
            this.getWorld().removeObject(heart);
        }
        
        for (int i = 0; i < health; i++) {
            this.getWorld().addObject(new Heart(), 50 + (i * 30), 30);  
        }
    }
    
    private void checkTouchBySpeedBoost() {
            
        if (isTouching(SpeedBoost.class)) {
            Greenfoot.playSound("speedPotionDrinking.wav");    
            removeTouching(SpeedBoost.class); // Entfernt das Item
            playerSpeed += 1; // Erhöht die Geschwindigkeit dauerhaft um 1
        }
    }
    
    private void checkTouchByHealingPotion() {
        
        if (isTouching(HealingPotion.class)) {
        Greenfoot.playSound("healPotionDrinking.wav");
        removeTouching(HealingPotion.class);
        
        for (int i = 0; i < 2; i++) {
            
            if (health < maxHealth) {
                
                health++;
                
            } else {
                
                break;
            }
        }
        
    }
    }
    
    
    /**
     * checkTouchByEnemy() : Wird 30x/Sek fortlaufend durch die act()-Methode ausgeführt und registriert direkte Berührungen mit Gegnern.
     * Bei Berührung mit einem Gegner wird die Lebensleiste reduziert sowie ein Cooldown-Counter gestartet.
     * Der Cooldown verhindert, dass der Spieler bei Berührung mit jedem einzelnen Frame/Sek ein Herz aus der Lebensleiste verliert.
     */
    
    private void checkTouchByEnemy() {
        
        if (cooldownCounter <= 0) {
            isDamageCooldownActive = false;
            
        } else {
            
            cooldownCounter--;
        }
        
        if ((isTouching(Enemies.class) || isTouching(EnemyProjectiles.class)) &! isDamageCooldownActive) {
            Greenfoot.playSound("playerTakesDMG.mp3");
            cooldownCounter = COOLDOWN_SECONDS * INIT_FPS;
            isDamageCooldownActive = true;
            health--;
        } 
        
        if (health <= 0) {
            Greenfoot.playSound("playerFinallyDies.mp3");
            Greenfoot.setWorld(new GameOver(MyWorld.highscore, Startknopf.playerName));
}
           
    }

    /**
     * Methode, die mehrfach pro Sekunde durch act() aufgerufen wird und das Spieler-Bild setzt: Steuert über if - Verkettungen WELCHE Sprite-Arrays durchlaufen werden sollen und
     * das sie überhaupt von Bild zu Bild durchlaufen werden.
     */
    
    private void updateAnimation() {
        
        animationClock++;
        animationClockIdle++;
        animationClockSpells++;
        
        if (animationClockSpells > ANIMATION_SPEED_SPELLS) {
            
            animationIndexSpells++;
            animationClockSpells = 0;
        }
        
        if (animationIndexSpells > playerCastingSpellRight.length - 1) {
            
            animationIndexSpells = 0;
        }
        
        if (animationIndexRunning < playerRunningAnimationRight.length - 1) {
            
            animationIndexRunning++;
            
        } else {
            
            animationIndexRunning = 0;
        }
        
        if (animationClockIdle > ANIMATION_SPEED_IDLE) {
            
            animationIndexIdle++;
            animationClockIdle = 0;
            
        }
        
        if (animationIndexIdle > playerIdleAnimationRight.length - 1) {
            
            animationIndexIdle = 0;
        }
            
        if (Greenfoot.isKeyDown("d")) {
            
            facingLeft = false;
            isCastingSpells = false;
            System.out.println(getImage());
            this.setImage(playerRunningAnimationRight[animationIndexRunning]);
            
        } else if (Greenfoot.isKeyDown("a")) {
            
            facingLeft = true;
            isCastingSpells = false;
            this.setImage(playerRunningAnimationLeft[animationIndexRunning]);
            
        } else if (facingLeft && Greenfoot.isKeyDown("w")) {
            
            isCastingSpells = false;
            this.setImage(playerRunningAnimationLeft[animationIndexRunning]);
            
        } else if (!facingLeft && Greenfoot.isKeyDown("w")) {
            
            isCastingSpells = false;
            this.setImage(playerRunningAnimationRight[animationIndexRunning]);
            
        } else if (facingLeft && Greenfoot.isKeyDown("s")) {
            
            isCastingSpells = false;
            this.setImage(playerRunningAnimationLeft[animationIndexRunning]);
            
        } else if (!facingLeft && Greenfoot.isKeyDown("s")) {
            
            isCastingSpells = false;
            this.setImage(playerRunningAnimationRight[animationIndexRunning]);
            
        } else if (!facingLeft && isCastingSpells) {
            
            this.setImage(playerCastingSpellRight[animationIndexSpells]);
            
        } else if (facingLeft && isCastingSpells) {
            
            this.setImage(playerCastingSpellLeft[animationIndexSpells]);
        } else if (!facingLeft) {
            
            this.setImage(playerIdleAnimationRight[animationIndexIdle]);
            
        } else if (facingLeft) {
            
            this.setImage(playerIdleAnimationLeft[animationIndexIdle]);
        }
    }
    
    /**
     * act-Methode: Wird vom Programm automatisch mehrfach pro Sekunde ausgeführt.
     */
    
    public void act() {
        
        checkTouchBySpeedBoost();
        checkTouchByHealingPotion();
        animationClock++;
        
        if (animationClock >= ANIMATION_SPEED) {
            
            updateAnimation();
            animationClock = 0;
        }
        
    /** MOVEMENT, DIE DEFAULT ROTATION IST NACH RECHTS */
        
    if (!gameOver && Greenfoot.isKeyDown("w"))
    {
        setRotation(270);
        walk();
        
    } 
    if (!gameOver && Greenfoot.isKeyDown("a"))
    {
        setRotation(180);
        walk();
    } 
    if (!gameOver && Greenfoot.isKeyDown("s"))
    {
        setRotation(90);
        walk();
    } 
    if (!gameOver && Greenfoot.isKeyDown("d"))
    {
        setRotation(0);
        walk();
    }
    
        /** TURNIN 4 DA SHOOTIN, DIE DEFAULT ROTATION IST NACH RECHT */
    if (!gameOver && Greenfoot.isKeyDown("right") && Greenfoot.isKeyDown("up"))
    {
        setRotation(45);
        fireShots();
        setRotation(0);
    } else
    if (!gameOver && Greenfoot.isKeyDown("up") && Greenfoot.isKeyDown("left"))
    {
        setRotation(315);
        fireShots();
        setRotation(0);
    } else
    if (!gameOver && Greenfoot.isKeyDown("left") && Greenfoot.isKeyDown("down"))
    {
        setRotation(225);
        fireShots();
        setRotation(0);
    } else
    if (!gameOver && Greenfoot.isKeyDown("right") && Greenfoot.isKeyDown("down"))
    {
        setRotation(135);
        fireShots();
        setRotation(0);
    } else
    if (!gameOver && Greenfoot.isKeyDown("up"))
    {
        setRotation(0);
        fireShots();
        setRotation(0);
    } else
    if (!gameOver && Greenfoot.isKeyDown("left"))
    {
        setRotation(270);
        fireShots();
        setRotation(0);
    } else
    if (!gameOver && Greenfoot.isKeyDown("down"))
    {
        setRotation(180);
        fireShots();
        setRotation(0);
    } else
    if (!gameOver && Greenfoot.isKeyDown("right"))
    {
        setRotation(90);
        fireShots();
        setRotation(0);
    }
    checkTouchByEnemy();
    displayHealth();
    shotsTimer++;
    
    }
    
    private void walk() {
        move(playerSpeed);
        setRotation(0);
        stepCounter++;
        if (stepCounter == 15 || stepCounter == 30) {
            Greenfoot.playSound("step.mp3");
        } else if (stepCounter >= 31) {
            stepCounter = 0;
        }

    }
    
    private void fireShots() {
                        
            // Timer, der die Schussrate bestimmt
            
            if (shotsTimer >= SHOTS_PER_SECOND) {
                
            getWorld().addObject(new PlayerProjectiles(this), getX(), getY());
            shotsTimer = 0;
            isCastingSpells = true;
            
            }
    }
    
    /**
     * Getter - Methoden zur die Abfrage der x- und y - Koordinaten des Spielers von außerhalb der Player - Klasse
     */
    
    public int getPlayerX() {
        
        return this.getX();
    }
    
    public int getPlayerY() {
        
        return this.getY();
    }
    

}

 

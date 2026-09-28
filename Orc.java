import greenfoot.*;

/**
 * Die Orc-Klasse repräsentiert einen Gegner mit 2 Leben.
 */
public class Orc extends Enemies {
    
    private int health = 2; // Orc hat 2 Leben
    private final int ORC_SIZE = 5;
    private final int ORC_MOVEMENT_SPEED = 2;
    private final Player player;
    private final GreenfootSound slash = new GreenfootSound ("slash.wav");
    private final GreenfootImage[] idleRight;
    private final GreenfootImage[] idleLeft;
    private final GreenfootImage[] runningRight;
    private final GreenfootImage[] runningLeft;
    private final GreenfootImage[] attackRight;
    private final GreenfootImage[] attackLeft;
    private final GreenfootImage[] dyingRight;
    private final GreenfootImage[] dyingLeft;
    
    private final int ANI_UPDATES_PER_SEC = 3;
    private final double SPRITE_DURATION = 30.0 / ANI_UPDATES_PER_SEC;
    private double animationClock = 0.0;
    private int aniIndex = 0;
    private int aniIndexDeath = 0;
    
    private boolean isFacingLeft = false;
    private boolean isAttacking = false;
    private boolean isDead = false;
    
    /**
     * Mindestdistanz, ab der die Angriffsanimation abgespielt wird
     */
    private final int MINIMUM_ATTACK_DISTANCE = 70;

    public Orc(Player player) {
        
        idleRight = prepareSprites(ORC_SIZE, false, "images/orc/idle/tile",0, 5);
        idleLeft = prepareSprites(ORC_SIZE, true, "images/orc/idle/tile", 0, 5);
        runningRight = prepareSprites(ORC_SIZE, false, "images/orc/running/tile", 8, 14);
        runningLeft = prepareSprites(ORC_SIZE, true, "images/orc/running/tile", 8, 14);
        attackRight = prepareSprites(ORC_SIZE, false, "images/orc/attacking/tile", 17, 21);
        attackLeft = prepareSprites(ORC_SIZE, true, "images/orc/attacking/tile", 17, 21);
        dyingRight = prepareSprites(ORC_SIZE, false, "images/orc/death/tile", 40, 43);
        dyingLeft = prepareSprites(ORC_SIZE, true, "images/orc/death/tile", 40, 43);
        
        setImage(idleRight[0]);
        
        this.player = player;
    }
    
    /**
     * Prüft welche Animation abgespielt werden muss:
     * - Idle
     * - Laufen
     * - Angriff
     * - tot
     */
    
    private void updateAnimation() {
        
        if (isDead && isFacingLeft) {
            
            if (aniIndexDeath < dyingLeft.length) {
                
                setImage(dyingLeft[aniIndexDeath]);
                aniIndexDeath++;
                
            } 
            
        } else if (isDead && !isFacingLeft) {
            
            if (aniIndexDeath < dyingRight.length) {
                
                setImage(dyingRight[aniIndexDeath]);
                aniIndexDeath++;
            } 
            
        } else if (!isAttacking && isFacingLeft) {
            
            if (aniIndex < runningLeft.length) {
                
            setImage(runningLeft[aniIndex]);
            
            } else {
                aniIndex = 0;
            }
            
        } else if (!isAttacking && !isFacingLeft) {
            
            if (aniIndex < runningRight.length) {
                
                setImage(runningRight[aniIndex]);
                
            } else {
                aniIndex = 0;
            }
            
        } else if (isAttacking && isFacingLeft) {
            
            if (aniIndex < attackLeft.length) {
                
                setImage(attackLeft[aniIndex]);
                
            } else {
                aniIndex = 0;
                slash.play();
                slash.setVolume(75);
            }
            
        } else if (isAttacking && !isFacingLeft) {
            
            if (aniIndex < attackRight.length) {
                setImage(attackRight[aniIndex]);
            } else {
                aniIndex = 0;
                slash.play();
                slash.setVolume(75);
            } 
        }
        
        aniIndex++;
    }
    
    private void checkHitByPlayer() {
        
        // Prüfen, ob der Orc von einem Projektil getroffen wurde
        if (isHitByPlayerProjectile()) {
            health--; // Reduziert die Lebenspunkte des Orcs um 1
            removeTouching(PlayerProjectiles.class); // Entfernt das Projektil nach dem Treffer
            if (health <= 0) { // Überprüfen, ob die Lebenspunkte aufgebraucht sind
                ((MyWorld) getWorld()).addHighscore(2); // 2 Punkte für das Besiegen eines Orcs
                
                // NEU: Item mit einer Wahrscheinlichkeit von 20% fallen lassen
                if (Greenfoot.getRandomNumber(100) < 20) { 
                    getWorld().addObject(new SpeedBoost(), getX(), getY());
                }
                Greenfoot.playSound("OrcDeath.wav");
                isDead = true;
            } else { 
                Greenfoot.playSound("OrcHit.wav");}
        }
    }
    

    public void act() {
        
        /**
         * Prüfen, ob Gegner sich nach links wenden soll
         */
        
        isFacingLeft = player.getX() < this.getX() ? true : false;
        isAttacking = getDistancePlayerOrc(player, this) <= MINIMUM_ATTACK_DISTANCE ? true : false;
        
        System.out.println("Distance Player Orc: " + getDistancePlayerOrc(player, this));
        
        /**
         * Animation in bestimmter Frequenz updaten
         */
        
        animationClock++;
        
        if (animationClock >= SPRITE_DURATION) {
            
            updateAnimation();
            animationClock = 0;
        }
        
        // Bewegung in Richtung des Spielers
        turnTowards(player.getX(), player.getY());
        
        if (!isAttacking && !isDead) {
            move(ORC_MOVEMENT_SPEED); // Orc bewegt sich mit einer konstanten Geschwindigkeit
        }
        
        setRotation(0);
        checkHitByPlayer();
        
        if (isDead && aniIndexDeath >= dyingRight.length) {
            
            getWorld().removeObject(this);
        }
        
        
    }
}


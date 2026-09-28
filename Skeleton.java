import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Skeleton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Skeleton extends Enemies {
    
    private final Player player;
    /**
     * Globale Variablen und Konstanten zur Veränderung ...
     * 
     * - der Wahrschienlichkeit von Ruhezeiten von Skeletons (zufälliges Stehenbleiben) sowie deren Dauer
     * - der Laufgeschwindigkeit der Skeletons
     */
    
    private final double RESTING_CHANCE = 0.05;
    private final int RESTING_TIME = 50;
    private final int SKELETON_MOVE_SPEED = 2;
    private double random1ofHundredNr = EssentialFunctions.getRandomNr(100.0);
    
    private final int SKELETON_SIZE = 3;
    private GreenfootImage[] runningAnimationRight = null;
    private GreenfootImage[] runningAnimationLeft = null;
    private GreenfootImage[] deathAnimation = null;
    private int animationIndex = 0;
    private int animationClock = 0;
    private final int ANIMATION_SPEED = 3;
    private final int DEATH_ANI_SPEED = 10;
    private boolean isDead = false;
    private final int DESPAWN_TIME_AFTER_DEATH = 80;
    private int deathTime = 0;
    
    private final int POTION_DROP_CHANCE = 3;
    
    public Skeleton(Player player) {
        
        this.player = player;
        
        runningAnimationRight = prepareSprites(SKELETON_SIZE, false, "images/skeleton/tile", 4, 9);
        runningAnimationLeft = prepareSprites(SKELETON_SIZE, true, "images/skeleton/tile", 4, 9);
        deathAnimation = prepareSprites(SKELETON_SIZE, false, "images/skeleton/tile", 10, 13);
        
        setImage(runningAnimationRight[0]);
    }
    
    private void moveTowardsPlayer() {
        
        turnTowards(player.getX(), player.getY());
        move(SKELETON_MOVE_SPEED);
        setRotation(0);
    }
    
    private void runDeathAnimation() {
        
        animationClock++;
        deathTime++;
        if (deathTime == 1) {
            Greenfoot.playSound("boneKill.mp3");
        }
        
        
        if (!(animationIndex >= deathAnimation.length) && animationClock >= DEATH_ANI_SPEED) {
            
            setImage(deathAnimation[animationIndex]);
            animationIndex++;
            animationClock = 0;
        }
        if (deathTime >= DESPAWN_TIME_AFTER_DEATH) {
            
             getWorld().removeObject(this);
        } 
    }
    
    private void updateRunningAnimation() {
        
        if (animationIndex < runningAnimationRight.length - 1) {
            
            animationIndex++;
            
        } else {
            
            animationIndex = 0;
        }
        if (player.getX() > getX()) {
            
            setImage(runningAnimationRight[animationIndex]);
            
        } else {
            
            setImage(runningAnimationLeft[animationIndex]);
        }
    }
    
    public void act() {
        
        animationClock++;
        
        if (isDead) {
            
            runDeathAnimation();
        } else if (animationClock >= ANIMATION_SPEED) {
            
            updateRunningAnimation();
            animationClock = 0;
        }
        /**
         * if - Statements: ruft raiseSpawnRate in Level- Klasse auf, um dort die Spawnwahrscheinlichkeit der Gegner zu erhöhen
         * zweites if-Statement: Sorgt für zufallsbedingtes Stehenbleiben der Skeletons
         */
       if (!isDead &! (deathTime > 0) && isHitByPlayerProjectile()) {
           
            if (Greenfoot.getRandomNumber(100) < POTION_DROP_CHANCE) {
               getWorld().addObject(new HealingPotion(), this.getX(), this.getY());
           }
           
           
            ((MyWorld)getWorld()).addHighscore(1);
            Level2.raiseSpawnRate(0.10 / MyWorld.highscore + 0.05);
            animationIndex = 0;
            animationClock = 0;
            isDead = true;
        
        }
         if (RESTING_CHANCE > random1ofHundredNr) {
           
           sleepFor(RESTING_TIME);
           random1ofHundredNr = EssentialFunctions.getRandomNr(100.0);
           
       } else {
           
           random1ofHundredNr = EssentialFunctions.getRandomNr(100.0);
       }
       if (!isDead) {
           
           moveTowardsPlayer();
       }    
    }      
}  

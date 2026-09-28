import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ein Gegnertyp, der auf den Spieler schießt.
 */
public class Archer extends Enemies {
    
    private final Player player; 
    private final int shootCooldown = 80; 
    private int cooldownTimer = 0;
    
    /**
     * Sprite Sheets für verschiedene Animationen
     */
    
    private final GreenfootImage[] archerRunningAnimationRight;
    private final GreenfootImage[] archerRunningAnimationLeft;
    private final GreenfootImage[] archerIdleRight;
    private final GreenfootImage[] archerIdleLeft;
    private final GreenfootImage[] archerShootingRight;
    private final GreenfootImage[] archerShootingLeft;
    private final GreenfootImage[] deathAnimation;
    private final int ARCHER_SIZE = 3;
    
    /**
     * Werte für Archer Verhalten
     */
    private final int ARCHER_MOVEMENT_SPEED = 1;
    private final int DISTANCE_SHOOT_PLAYER = 400;
    
    /**
     * Variablen rund um die Animation des Archers
     */
    
    private int animationIndex = 0;
    private int animationIdleIndex = 0;
    private int animationClock = 0;
    private int animationShootingClock = 0;
    
    private boolean isDead = false;
    private boolean isIdle = false;
    
    private final int ANIMATION_SPEED = 5;
    private final int ANIMATION_SHOOTING_SPEED = 15;
    private final int DEATH_ANI_SPEED = 10;
    private final int DESPAWN_TIME_AFTER_DEATH = 30;
    private int deathTime = 0;
    
    /**
     * Konstruktor
     */

    public Archer(Player player) {
        
        this.player = player;
        
        archerRunningAnimationRight = prepareSprites(ARCHER_SIZE, false, "images/archer/running/tile", 90, 95);
        archerRunningAnimationLeft = prepareSprites(ARCHER_SIZE, true, "images/archer/running/tile", 90, 95);
        archerIdleRight = prepareSprites(ARCHER_SIZE, false, "images/archer/idle/tile", 1, 3);
        archerIdleLeft = prepareSprites(ARCHER_SIZE, true, "images/archer/idle/tile", 1, 3);
        archerShootingRight = prepareSprites(ARCHER_SIZE, false, "images/archer/shooting/tile",101, 105);
        archerShootingLeft = prepareSprites(ARCHER_SIZE, true, "images/archer/shooting/tile",101, 105);
        deathAnimation = prepareSprites(ARCHER_SIZE, false, "images/archer/death/tile", 51, 55);
        
        setImage(archerIdleRight[0]);
        
    }
    
    /**
     * Laufanimation des Archers abspielen
     */
    
        private void updateRunningAnimation() {
        
        if (animationIndex < archerRunningAnimationRight.length - 1) {
            
            animationIndex++;
            
        } else {
            
            animationIndex = 0;
        }
        
        if (player.getX() > getX()) {
            
            setImage(archerRunningAnimationRight[animationIndex]);
            
        } else {
            
            setImage(archerRunningAnimationLeft[animationIndex]);
        }
    }
    
    /**
     * Sterbeanimation abspielen
     */
    
        private void runDeathAnimation() {
        
        animationClock++;
        deathTime++;
        
        if (deathTime == 1) {
            Greenfoot.playSound("archerDeath.wav");
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
    
    /**
     * Schießanimation abspielen: Wird aktiv, sobald der Archer "idle" ist
     */
    
    private void updateShootingAnimation() {
        
        if (animationIdleIndex < archerShootingRight.length - 1) {
            
            animationIdleIndex++;
            
        } else {
            
            animationIdleIndex = 0;
        }
        
        if (player.getX() > getX()) {
            
            setImage(archerShootingRight[animationIdleIndex]);
            
        } else {
            
            setImage(archerShootingLeft[animationIdleIndex]);
        }
        
        
    }
    
    /**
     * Prüft in welchem Zustand sich der Archer befindet (idle, death, running ...) und ruft entsprechende
     * Methoden zum Abspielen der Animationen auf.
     * 
     * Prüft die Distanz zum Spieler und setzt anhand dessen den Archer in "idle" Zustand, in dem erst geschossen und
     * gleichzeitig die Schießanimation abgespielt wird.
     */
    
    public void act() {
        
         animationClock++;
         animationShootingClock++;
        
        if (isDead) {
            runDeathAnimation();
        } else if (!isIdle && !isDead && animationClock >= ANIMATION_SPEED) {
            
            updateRunningAnimation();
            animationClock = 0;
            
        } else if (isIdle && !isDead && animationShootingClock >= ANIMATION_SHOOTING_SPEED) {
            
            updateShootingAnimation();
            animationShootingClock = 0;
        }
        
        if (!isDead && isIdle) {
            
            shootAtPlayer(); 
        }
        
        if (!isDead && getDistancePlayerArcher(player, this) > DISTANCE_SHOOT_PLAYER) {
        
        isIdle = false;
        moveTowardsPlayer();
        
        } else {
            
            isIdle = true;
        }
        

        if (!isDead && isHitByPlayerProjectile()) { 
            
            ((MyWorld)getWorld()).addHighscore(4);
            isDead = true;
            animationClock = 0;
        }
    }
    
    /**
     * In Spielerrichtung bewegen
     */
    
    private void moveTowardsPlayer() {
        
            turnTowards(player.getX(), player.getY());
            move(ARCHER_MOVEMENT_SPEED); // Langsamer als Skeleton
            setRotation(0);
    }
    
    /**
     * Auf Spieler schießen -> Ruft ein neues EnemyProjectile-Object in die Spielwelt.
     */
    
    private void shootAtPlayer() {
        
        if (cooldownTimer <= 0) { 
            GreenfootSound shot = new GreenfootSound ("archerShot.wav");
            shot.play();
            shot.setVolume(75);
            getWorld().addObject(new EnemyProjectiles(player, this.getX(), this.getY(), "images/archer/arrows/arrow2.png"), getX(), getY()); 
            cooldownTimer = shootCooldown;
            
        } else {
            
            cooldownTimer--; 
        }
    }
}

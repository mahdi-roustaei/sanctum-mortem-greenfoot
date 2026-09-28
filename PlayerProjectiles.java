import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PlayerProjectiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class PlayerProjectiles extends Projectile {
 
    Player player;
    GreenfootImage projectileImg;
    GreenfootImage[] fireballRaster;
    
    private int initPlayerX;
    private int initPlayerY;
    private final int FIREBALL_SIZE = 1;
    
    private int aniClock = 0;
    private int aniIndex = 0;
    private final int ANIMATION_SPEED = 20;
    private final int PROJECTILE_SPEED = 6;
    
    // Konstruktor der Projektilklasse
    
    public PlayerProjectiles(Player player) {
        
        this.player = player;
        
        initPlayerX = player.getX();
        initPlayerY = player.getY();
        
        fireballRaster = prepareSprites(FIREBALL_SIZE, false, "images/projectiles/player/FB", 1, 5);
        setImage(fireballRaster[0]);
        
        Greenfoot.playSound("magicMissile.mp3");
        setRotation(player.getRotation()- 90);
    }
    
    private void updateAnimation() {
        
        aniIndex++;
        if (aniIndex >= fireballRaster.length) {
            
            aniIndex = 0;
        }
        
        setImage(fireballRaster[aniIndex]);
    }
    
    public void act() {
        
        aniClock++;
        
        if (aniClock >= ANIMATION_SPEED) {
            
            updateAnimation();
            aniClock = 0;
        }
        
        move(PROJECTILE_SPEED);
        
        super.checkWorldEdgeCollision();
    }
    
}


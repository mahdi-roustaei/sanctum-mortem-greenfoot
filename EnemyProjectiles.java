import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EnemyProjectiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EnemyProjectiles extends Projectile {
    
    Player player;
    GreenfootImage projectileImg;
    
    private int enemyX;
    private int enemyY;
    private boolean initialized = false;
    private final int imgWidth = 35;
    private final int imgHeight = 12;
    
    private final int ENEMY_PROJECTILE_SPEED = 5;
    
    public EnemyProjectiles(Player player, int enemyX, int enemyY, String imageFilePath) {
        
        this.player = player;
        
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        
        projectileImg = new GreenfootImage(imageFilePath);
        projectileImg.scale(imgWidth, imgHeight);
        setImage(projectileImg);
        
        setRotation(0);
    }
 
    public void act() {
        
        if (!initialized) {
        
            turnTowards(player.getX(), player.getY());
            initialized = true;
        }
        
        if (isAtEdge()) {
            
            getWorld().removeObject(this);
        }
        
        move(ENEMY_PROJECTILE_SPEED);
    }
}

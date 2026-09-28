import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Implementierung von Projektilen
 * 
 * 
 * @version (14.10.2024)
 */
public class Projectile extends Entity {
 
    public void act() {
        
        checkWorldEdgeCollision();
        checkplayerEnemyProjectileCollision();
        

    }
    
    public void checkWorldEdgeCollision() {
        
            if (isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
    
    public void checkplayerEnemyProjectileCollision() {
        
       
    }
    
    
    
}

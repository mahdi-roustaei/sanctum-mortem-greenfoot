import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Enemies here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Enemies extends Entity {
    
    protected boolean isHitByPlayerProjectile() {
        
        if (isTouching(PlayerProjectiles.class)) {
            return true;
          }
        
        return false;
    }
    
}

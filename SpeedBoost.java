import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SpeedBoost here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpeedBoost extends Potion {
    
    public SpeedBoost() {
        
        GreenfootImage speedPotion = new GreenfootImage("images/items/itemSprites/tile056.png");
        speedPotion.scale(16*POTION_SCALE,16*POTION_SCALE);
        setImage(speedPotion);
        
    }
    
    public void act() {
        
    }
}

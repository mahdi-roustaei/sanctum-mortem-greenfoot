import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


public class HealingPotion extends Potion {
    
    GreenfootImage healingPotion = new GreenfootImage("images/items/itemSprites/tile057.png");
    
    public HealingPotion() {
        
        healingPotion.scale(16*POTION_SCALE, 16*POTION_SCALE);
        setImage(healingPotion);
        
    }
  
    public void act() {
        
        
    }
}

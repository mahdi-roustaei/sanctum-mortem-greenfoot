import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;

/**
 * Write a description of class Entity here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Entity extends Actor {
   
    /**
     * getSpriteRaster(): Selektiert anhand der Parameter die Bild-Dateien aus dem Dateisystem
     */
    
    private GreenfootImage[] getSpriteRaster(String pathToSprite, int startIndex, int endIndex) {
        
        GreenfootImage[] greenFootSprites = new GreenfootImage[endIndex - startIndex + 1];
        int fileIndex = 0;
        String fileName = "";
        
        for (int i = 0; i < endIndex - startIndex + 1; i++) {
            
            if (startIndex + fileIndex < 10) {
                
                fileName = "00" + Integer.toString(startIndex + fileIndex);
                
            } else if (startIndex + fileIndex < 100) {
                
                fileName = "0" + Integer.toString(startIndex + fileIndex);
                
            } else {
                
                fileName = Integer.toString(startIndex + fileIndex);
            }
            
            greenFootSprites[i] = new GreenfootImage(pathToSprite + fileName + ".png");
            System.out.println(pathToSprite + (startIndex + fileIndex) + ".png");
            fileIndex++;
        }
        
        return greenFootSprites;
    }
    
    /**
     * Lädt anhand der getSpriteRaster-Methode die Bild-Dateien aus dem System und passt die Bildgröße sowie Achsenspiegelung (falls gewünscht) an.
     * Anschließende Rückgabe in Form eines GreenfootImage-Arrays
     */
    
    protected GreenfootImage[] prepareSprites(int entitySize, boolean mirrored, String filePath, int fileStartIndex, int fileEndIndex) {
            
        GreenfootImage[] animationRaster;
        
        animationRaster = getSpriteRaster(filePath, fileStartIndex, fileEndIndex);
        
        for (int i = 0; i < animationRaster.length; i++) {
            
            animationRaster[i].scale(animationRaster[i].getWidth() * entitySize, animationRaster[i].getHeight() * entitySize);
        }
            
        if (mirrored) {
                
            for (int i = 0; i < animationRaster.length; i++) {
                    
                animationRaster[i].mirrorHorizontally();
            }
        }
            
        return animationRaster;
    }
    
    /**
     * Berechnet Distanz zwischen Spieler und Archer-Klasse.
     * Methode ist innerhalb eines jeden "Enemies" - Objektes aufrufbar, weil zB. Archer von Entity erbt
     * und diese Methode auf "protected" gesetzt ist.
     */
    
    protected int getDistancePlayerArcher(Player player, Archer enemy) {
        
        int deltaX = Math.max(player.getX(), enemy.getX()) - Math.min(player.getX(), enemy.getX());
        int deltaY = Math.max(player.getY(), enemy.getY()) - Math.min(player.getY(), enemy.getY());
        
        return (int)Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }
    
    protected int getDistancePlayerOrc(Player player, Orc enemy) {
        
        int deltaX = Math.max(player.getX(), enemy.getX()) - Math.min(player.getX(), enemy.getX());
        int deltaY = Math.max(player.getY(), enemy.getY()) - Math.min(player.getY(), enemy.getY());
        
        return (int)Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }
}

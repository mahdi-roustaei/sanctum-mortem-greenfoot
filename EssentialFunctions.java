/**
 * Write a description of class EssentialFunctions here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class EssentialFunctions {
    
    /**
     * getRandomNr(int highestValue) : Gibt eine zufällige Nummer zwischen 1 und dem per Parameter angegebenen Maximalwert aus
     */
    
    public static double getRandomNr(double highestValue) {
        
        int randNrRounded = (int)Math.round(Math.random() * highestValue);
        return randNrRounded;
    }
    
    public static double roundSpawnRate(double value) {
        
        return Math.round(value * 1000000000) / 1000000000.0;
    }
    
    /**
     * Gibt per Zufall entweder 1 oder -1 aus. Gebräuchlich bspw. für zufällige Richtungsumkehrungen von Entities 
     */
    
    public static int getRandomNumberInversion() {
        
        double index = getRandomNr(8);
        
        if (index >= 4) {
            index *= -1;
        }
        
        return (int)index;
    
    }
    

}

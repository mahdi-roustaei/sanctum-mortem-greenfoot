import greenfoot.*;
public class StartScreen extends World {
    public StartScreen() {
        super(1200, 800, 1);
        addObject(new Startknopf(), getWidth() / 2, getHeight() / 2); // Add the start button
    }
}
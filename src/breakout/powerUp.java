package breakout;

import javafx.scene.image.Image;

/*
- the parent class of all powerUps
- power ups are an element within the game hence extension
 */
public class powerUp extends element {
    /*
    - powerUp constructor
    - only requires the image of the power up
     */
    public powerUp(Image image){
        super(image, 0);
    }
}

package breakout;

import javafx.scene.image.Image;

/*
- the parent class of all powerUps
- power ups are an element within the game hence extension
- all powerups share a powerTrigger method that is triggered when the paddle collects the power up,
originally the powerup effects would take place within the main game class but now it all is done within
each respective powerup's class
- I believe this is well designed because it encapsulates the key element thats shared amongst all powerups, some
effect when collected
- the purpose of this code is to provide a common blue print for all powerups
 */
public class powerUp extends element {
    /*
    - powerUp constructor
    - only requires the image of the power up
     */
    public powerUp(Image image){
        super(image, 0);
    }

    public void powerTrigger(){
    }
}

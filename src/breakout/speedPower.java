package breakout;

import javafx.scene.image.Image;

/*
- speedPower is a type of power up when collected reduces the health of all bricks of the level to one
 */
public class speedPower extends powerUp {
    /*
    - speedPower constructor creates the power up
    - only requires the image of the power up
     */
    public speedPower(Image image){
        super(image);

    }
}

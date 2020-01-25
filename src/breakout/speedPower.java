package breakout;

import javafx.scene.image.Image;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/*
- speedPower is a type of power up when collected reduces the health of all bricks of the level to one
- the purpose of adding this class in masterpiece is to demonstrate how it speedPower contains
the same blueprint as the parent powerup class, however the powerTrigger method and its function is unique
to the speedPower class
- I believe this is well designed because it implements the fundamentals of inheritance and polymorphism
 */
public class speedPower extends powerUp {
    /*
    - speedPower constructor creates the power up
    - only requires the image of the power up
     */
    public speedPower(Image image){
        super(image);

    }

    public void powerTrigger(paddle myPaddle, speedPower mySpeedPower, Text hpText, ArrayList<brick> brickList){
        mySpeedPower.setImage(null);
        for(brick i : brickList){
            i.hitPoints = 1;
        }
    }
}

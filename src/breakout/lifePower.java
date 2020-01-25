package breakout;

import javafx.scene.image.Image;
import javafx.scene.text.Text;

/*
- lifePower is a type of power up when collected increases the player's life by one
- the purpose of adding this class in masterpiece is to demonstrate how it lifePower contains
the same blueprint as the parent powerup class, however the powerTrigger method and its function is unique
to the lifePower class
- I believe this is well designed because it implements the fundamentals of inheritance and polymorphism
 */
public class lifePower extends powerUp {
    /*
    - lifePower constructor creates the power up
    - only requires the image of the power up
     */
    public lifePower(Image image){
        super(image);

    }

    public void powerTrigger(paddle myPaddle, lifePower myLifePower, Text hpText){
        myPaddle.hitPoints = myPaddle.hitPoints + 1;
        myLifePower.setImage(null);
        hpText.setText("LIVES: " + myPaddle.hitPoints);
    }

}

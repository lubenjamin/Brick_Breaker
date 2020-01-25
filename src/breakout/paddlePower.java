package breakout;

import javafx.scene.image.Image;
import javafx.scene.text.Text;

/*
- paddlePower is a type of power up when collected increases the size of the player's paddle
- the purpose of adding this class in masterpiece is to demonstrate how it paddlePower contains
the same blueprint as the parent powerup class, however the powerTrigger method and its function is unique
to the paddlePower class
- I believe this is well designed because it implements the fundamentals of inheritance and polymorphism
 */
public class paddlePower extends powerUp {
    /*
    - paddlePower constructor creates the power up
    - only requires the image of the power up
     */
    public paddlePower(Image image) {
        super(image);
    }

    public void powerTrigger(paddle myPaddle, paddlePower myPaddlePower, Image longPaddleImage){
        myPaddle.setImage(longPaddleImage);
        myPaddlePower.setImage(null);
    }
}

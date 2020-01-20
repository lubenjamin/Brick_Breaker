package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
- element is a fundamental object of the game
- ball, paddle, and bricks are all types of elements
- all elements share the parent ImageView due to the appearance of an image hence the extension
- all elements have some form of health hence the hitpoint value
 */
public class element extends ImageView {
    public int hitPoints;

    /*
    - construct an element object
    - pass in an image and health value
     */
    public element(Image image, int hitPoints){
        super(image);
        this.hitPoints = hitPoints;
    }
}

package breakout;

import javafx.scene.image.Image;

/*
- paddle is a game element
- passes in an image that will be displayed as the paddle
 */
public class paddle extends element {
    /*
    - constructs the paddle
    - requires the paddle image and the health of the paddle
     */
    public paddle(Image image, int hitPoints){
        super(image, hitPoints);
    }
}

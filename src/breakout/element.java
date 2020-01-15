package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class element extends ImageView {
    int hitPoints;

    public element(Image image, int hitPoints){
        super(image);
        this.hitPoints = hitPoints;
    }
}

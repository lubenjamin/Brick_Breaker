package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class level extends Group {

    public static final int SIZE = 420;
    public static final Paint BACKGROUND = Color.AZURE;

    ArrayList<brick> brickList = new ArrayList<>();
    Scene newLevel;

    public level(){
        super();
    }

    public Scene setupLevel(Group root, paddle myPaddle, bouncer myBouncer){
        myBouncer.setX(SIZE / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(SIZE / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
        myPaddle.setX(SIZE / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(SIZE - myPaddle.getBoundsInLocal().getHeight());

        root.getChildren().add(myPaddle);
        root.getChildren().add(myBouncer);

        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }

    public ArrayList<brick> getBrickList(){
        return brickList;
    }
}

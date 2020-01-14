package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

import java.util.ArrayList;


public class level1 extends level {
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final String BRICK1_IMAGE = "brick1.gif";
    public static final String BRICK2_IMAGE = "brick2.gif";
    public static final String BRICK3_IMAGE = "brick3.gif";
    public static final String BRICK4_IMAGE = "brick4.gif";
    public static final String BRICK5_IMAGE = "brick5.gif";
    public static final String BRICK6_IMAGE = "brick6.gif";
    public static final String BRICK7_IMAGE = "brick7.gif";
    public static final String BRICK8_IMAGE = "brick8.gif";
    public static final String BRICK9_IMAGE = "brick9.gif";
    public static final String BRICK10_IMAGE = "brick10.gif";

    Image brick1 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK1_IMAGE));
    Image brick2 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK2_IMAGE));
    Image brick3 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK3_IMAGE));
    Image brick4 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK4_IMAGE));
    Image brick5 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK5_IMAGE));
    Image brick6 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK6_IMAGE));
    Image brick7 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK7_IMAGE));
    Image brick8 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK8_IMAGE));
    Image brick9 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK9_IMAGE));
    Image brick10 = new Image(this.getClass().getClassLoader().getResourceAsStream(BRICK10_IMAGE));

    brick myBrick1 = new brick(brick1);
    brick myBrick2 = new brick(brick2);
    brick myBrick3 = new brick(brick3);
    brick myBrick4 = new brick(brick4);

    ArrayList<brick> brickList = new ArrayList<>();
    Group root = new Group();


    public level1(){
        super();
    }

    public Group setupLevel(){
        myBrick2.setX(myBrick1.getBoundsInLocal().getWidth());
        myBrick3.setX(myBrick2.getBoundsInLocal().getWidth() * 2);
        myBrick4.setX(myBrick3.getBoundsInLocal().getWidth() * 3);

        brickList.add(myBrick1);
        brickList.add(myBrick2);
        brickList.add(myBrick3);
        brickList.add(myBrick4);

        root.getChildren().add(myBrick1);
        root.getChildren().add(myBrick2);
        root.getChildren().add(myBrick3);
        root.getChildren().add(myBrick4);

        return root;
    }

    public ArrayList<brick> getBrickList(){
        return brickList;
    }

}

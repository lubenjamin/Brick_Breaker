package breakout;

import javafx.scene.image.Image;

import java.util.ArrayList;

/*
- brick is one of the most common elements within the game
- brick object contains the image, health, and value added to score when destroyed
 */
public class brick extends element{

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

    public int value;
    public ArrayList<Image> brickImageList = new ArrayList<>();

    /*
    - construct the brick object
    - pass in the brick image, health, and score value
     */
    public brick(Image image, int hitPoints, int val){
        super(image, hitPoints);
        value = val;
    }

    /*
    - return the desired brick type
    - take in a brick type value read from the level file that corresponds to brick health
    - brick health and score value are equal
     */
    public brick returnBrick(int brickType){
        switch(brickType){
            case 1:
                return new brick(brick1, 1, 1);
            case 2:
                return new brick(brick2, 2, 2);
            case 3:
                return new brick(brick3, 3, 3);
            case 4:
                return new brick(brick4, 4, 4);
            case 5:
                return new brick(brick5, 5, 5);
            case 6:
                return new brick(brick6, 6, 6);
            case 7:
                return new brick(brick7, 7, 7);
            case 8:
                return new brick(brick8, 8, 8);
            case 9:
                return new brick(brick9, 9, 9);
            case 10:
                return new brick(brick10, 10, 10);
        }

        return new brick(null, 0, 0);
    }

    /*
    - create and return the brickImageList
    - brickImageList is used to change the appearance of a brick when health is reduced
    - index i+1 of the list corresponds with brick health
     */
    public ArrayList<Image> getBrickImageList(){
        brickImageList.add(brick1);
        brickImageList.add(brick2);
        brickImageList.add(brick3);
        brickImageList.add(brick4);
        brickImageList.add(brick5);
        brickImageList.add(brick6);
        brickImageList.add(brick7);
        brickImageList.add(brick8);
        brickImageList.add(brick9);
        brickImageList.add(brick10);

        return brickImageList;
    }


}

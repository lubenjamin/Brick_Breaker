package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
- level creates any level based off a level text file
- I believe it is well designed because it allows the user to easily create levels, modify levels,
and freely customize the appearance of the level
- Where and how power ups are generated can be modified in this class as well, for example if one were to want to have multiple
of the same power up in a level, such modifications can be done within this class
- level class also implements various element objects such as the paddle, ball, and bricks
- The implementation of subroutines mainly for object placement helps clean this code and makes it more reasonable by removing
redundancies that were present in the old code
 */

public class level extends Group {
    private static final int SIZE = 630;
    private static final Paint BACKGROUND = Color.BLACK;
    private ArrayList<brick> brickList = new ArrayList<>();
    private ArrayList<Integer> powerList = new ArrayList<>();
    private Scene newLevel;
    /*
    - construct a basic level
     */
    public level(){
        super();
    }

    /*
    - setupLevel returns a scene with a level to be displayed on the main stage
    - each level has common elements of ball, paddle, and power ups
    - fileName is passed in to read the text file layout of the desired level
    - score is also passed in because score accumulates throughout the game through levels
     */
    public Scene setupLevel(Group root, paddle myPaddle, bouncer myBouncer, String fileName, Text hpText, Text scoreText, int score,
                            lifePower myLifePower, speedPower mySpeedPower, paddlePower myPaddlePower) throws IOException {

        /*
        - reset the position of bouncer and paddle
         */
        center(myPaddle, myBouncer, SIZE);

        /*
        - set the correct position and values of score and life indicators
         */
        hpText.setText("LIVES: " + myPaddle.hitPoints);
        hpText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        hpText.setX(SIZE - hpText.getBoundsInLocal().getWidth());
        hpText.setY(hpText.getBoundsInLocal().getHeight());
        hpText.setFill(Color.RED);

        scoreText.setText("SCORE: " + score);
        scoreText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        scoreText.setY(hpText.getBoundsInLocal().getHeight());
        scoreText.setFill(Color.RED);

        /*
        - add all elements to the group that will be constructed into a scene
         */
        root.getChildren().add(hpText);
        root.getChildren().add(scoreText);
        root.getChildren().add(myPaddle);
        root.getChildren().add(myBouncer);
        root.getChildren().add(myLifePower);
        root.getChildren().add(mySpeedPower);
        root.getChildren().add(myPaddlePower);

        /*
        - read level text file and construct the correct bricks given health values and position within the file
        - add bricks to brickList
         */
        Path source = Paths.get("./resources/"+fileName);
        Scanner sc = new Scanner(source);
        int rows = 10;
        int columns = 6;
        while (sc.hasNextLine()) {
            for (int i = 0; i < 16; i++) {
                String[] line = sc.nextLine().split(" ");
                for (int j = 0; j < 9; j++) {
                    if(Integer.parseInt(line[j]) != 0){
                        brick newBrick = new brick(null, 0, 0).returnBrick(Integer.parseInt(line[j]));
                        newBrick.setX(newBrick.getBoundsInLocal().getWidth() * j);
                        newBrick.setY(newBrick.getBoundsInLocal().getHeight() * i);
                        root.getChildren().add(newBrick);
                        brickList.add(newBrick);
                    }
                }
            }
        }

        /*
        - generate random positions for the three different types of power ups
        - set the power ups in the same position as their corresponding random bricks
         */
        for(int i = 0; i < 3; i++){
            powerList.add(generateRandomInt(brickList.size()));
        }

        centerPowerUp(myLifePower,0);
        centerPowerUp(mySpeedPower,1);
        centerPowerUp(myPaddlePower,2);

        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }

    public static void center(paddle myPaddle, bouncer myBouncer, int size) {
        myBouncer.setX(size / 2 - myBouncer.getBoundsInLocal().getCenterX());
        myBouncer.setY(size - myPaddle.getBoundsInLocal().getHeight() - myBouncer.getBoundsInLocal().getHeight());
        myPaddle.setX(size / 2 - myPaddle.getBoundsInLocal().getCenterX());
        myPaddle.setY(size - myPaddle.getBoundsInLocal().getHeight());
    }

    private void centerPowerUp(powerUp myPowerUp, int type){
        myPowerUp.setX(brickList.get(powerList.get(type)).getX());
        myPowerUp.setY(brickList.get(powerList.get(type)).getY());
    }

    /*
    - return brickList that corresponds with the level which will be used for brick and ball detection
     */
    public ArrayList<brick> getBrickList(){
        return brickList;
    }

    /*
    - generate random integers which are used to find a power ups corresponding bricks
     */
    private static int generateRandomInt(int upperRange){
        Random random = new Random();
        return random.nextInt(upperRange);
    }
}

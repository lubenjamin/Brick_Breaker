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
- level is one of the fundamental classes of the game
- title screen, exit screen, and all levels are all level objects
 */

public class level extends Group {

    public static final int SIZE = 630;
    public static final Paint BACKGROUND = Color.BLACK;

    ArrayList<brick> brickList = new ArrayList<>();
    ArrayList<Integer> powerList = new ArrayList<>();

    Scene newLevel;

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
        myBouncer.setX(SIZE / 2 - myBouncer.getBoundsInLocal().getCenterX());
        myBouncer.setY(SIZE - myPaddle.getBoundsInLocal().getHeight() - myBouncer.getBoundsInLocal().getHeight());
        myPaddle.setX(SIZE / 2 - myPaddle.getBoundsInLocal().getCenterX());
        myPaddle.setY(SIZE - myPaddle.getBoundsInLocal().getHeight());

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

        myLifePower.setX(brickList.get(powerList.get(0)).getX());
        myLifePower.setY(brickList.get(powerList.get(0)).getY());

        mySpeedPower.setX(brickList.get(powerList.get(1)).getX());
        mySpeedPower.setY(brickList.get(powerList.get(1)).getY());

        myPaddlePower.setX(brickList.get(powerList.get(2)).getX());
        myPaddlePower.setY(brickList.get(powerList.get(2)).getY());

        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
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
    public static int generateRandomInt(int upperRange){
        Random random = new Random();
        return random.nextInt(upperRange);
    }

}

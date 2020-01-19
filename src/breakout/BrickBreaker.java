package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import java.util.ArrayList;

public class BrickBreaker extends Application{

    public static final String TITLE = "BrickBreaker";
    public static final int SIZE = 630;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLACK;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final double START_BOUNCER_X_SPEED = 0;
    public static final double START_BOUNCER_Y_SPEED = 300;
    public static final double POWER_Y_DROP_SPEED = 100;
    public static double LIFE_POWER_Y_SPEED = 0;
    public static double SPEED_POWER_Y_SPEED = 0;
    public static double BOUNCER_X_SPEED = START_BOUNCER_X_SPEED;
    public static double BOUNCER_Y_SPEED = START_BOUNCER_Y_SPEED;
    public static int MOVER_SPEED = 7;

    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final String LIFE_POWER_IMAGE = "laserpower.gif";
    public static final String SPEED_POWER_IMAGE = "sizepower.gif";



    Image bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
    Image paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
    Image lifePowerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(LIFE_POWER_IMAGE));
    Image speedPowerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(SPEED_POWER_IMAGE));



    private static Scene myScene;
    private static Stage mainStage;
    private bouncer myBouncer = new bouncer(bouncerImage);
    private paddle myPaddle = new paddle(paddleImage, 3);
    ArrayList<brick> brickList = new ArrayList<>();
    ArrayList<powerUp> powerList = new ArrayList<>();
    Group root = new Group();
    int levelCount = 0;
    Text hpText = new Text();
    Text scoreText = new Text();
    int score = 0;
    private lifePower myLifePower = new lifePower(lifePowerImage);
    private speedPower mySpeedPower = new speedPower(speedPowerImage);


    public void start (Stage stage) throws IOException{
        // attach scene to the stage and display it
        this.mainStage =  stage;
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        mainStage.setScene(myScene);
        mainStage.setTitle(TITLE);
        mainStage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
            try {
                step(SECOND_DELAY);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }


    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) throws IOException {
        // create one top level collection to organize the things in the scene
        titleScreen temp = new titleScreen();
        root = new Group();
        myScene = temp.setupLevel(root);
        mainStage.setScene(myScene);
        myScene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

        return myScene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) throws IOException{
        // update "actors" attributes

        // make bouncer bounce off walls
        if(myBouncer.getX() <= 0 || myBouncer.getX() + myBouncer.getBoundsInLocal().getWidth() >= SIZE){
            BOUNCER_X_SPEED = BOUNCER_X_SPEED * -1;
        }

        if(myBouncer.getY() <= 0){
            BOUNCER_Y_SPEED = BOUNCER_Y_SPEED * -1;
        }

        if(myBouncer.getBoundsInParent().intersects(myPaddle.getBoundsInParent())){
            double bouncerCenter = (myBouncer.getBoundsInParent().getMinX() + myBouncer.getBoundsInParent().getMaxX())/2;
            double paddleCenter = (myPaddle.getBoundsInParent().getMinX() + myPaddle.getBoundsInParent().getMaxX())/2;
            double centerDiff = (bouncerCenter - paddleCenter) / (myPaddle.getBoundsInLocal().getWidth());
            double angle = (centerDiff * 0.9 * Math.PI);
            double speedTotal = Math.pow(Math.pow(BOUNCER_X_SPEED, 2) + Math.pow(BOUNCER_Y_SPEED, 2), 0.5);

            BOUNCER_X_SPEED = speedTotal * Math.sin(angle);
            BOUNCER_Y_SPEED = speedTotal * Math.cos(angle) * -1;
        }

        if(myBouncer.getY() + myBouncer.getBoundsInLocal().getHeight() >= SIZE){
            myPaddle.hitPoints = myPaddle.hitPoints - 1;
            hpText.setText("LIVES: " + myPaddle.hitPoints);
            System.out.println(myPaddle.hitPoints);
            if(myPaddle.hitPoints == 0){
                exitScreen temp = new exitScreen();
                root = new Group();
                myScene = temp.setupLevel(root);
                mainStage.setScene(myScene);
            }
            reset();
        }

        for(int i = 0; i < brickList.size(); i++){
            if(myBouncer.getBoundsInParent().intersects(brickList.get(i).getBoundsInParent())){
                BOUNCER_Y_SPEED = BOUNCER_Y_SPEED * -1;
                brickList.get(i).hitPoints = brickList.get(i).hitPoints - 1;
                if (brickList.get(i).hitPoints == 0) {
                    brickList.get(i).setImage(null);
                    score++;
                    scoreText.setText("SCORE: " + score);
                    System.out.println(myLifePower.getX() + " " + myLifePower.getY() + " " + mySpeedPower.getX()+ " " + mySpeedPower.getY() );
                    if((brickList.get(i).getX() == myLifePower.getX()) && (brickList.get(i).getY() == myLifePower.getY())){
                        LIFE_POWER_Y_SPEED = POWER_Y_DROP_SPEED;
                    }

                    if((brickList.get(i).getX() == mySpeedPower.getX()) && (brickList.get(i).getY() == mySpeedPower.getY())){
                        SPEED_POWER_Y_SPEED = POWER_Y_DROP_SPEED;
                    }

                    brickList.remove(i);
                }
            }
        }


        if(myPaddle.getBoundsInParent().intersects(myLifePower.getBoundsInParent())){
            LIFE_POWER_Y_SPEED = LIFE_POWER_Y_SPEED * -1;
            myPaddle.hitPoints = myPaddle.hitPoints + 1;
            myLifePower.setImage(null);
            hpText.setText("LIVES: " + myPaddle.hitPoints);
        }

        if(myPaddle.getBoundsInParent().intersects(mySpeedPower.getBoundsInParent())){
            SPEED_POWER_Y_SPEED = SPEED_POWER_Y_SPEED * -1;
            mySpeedPower.setImage(null);
            for(brick i : brickList){
                i.hitPoints = 1;
            }
        }

        if(brickList.size() == 0) {
            if(levelCount == 1) {
                makeLevel("level1.txt");
                levelCount = 2;
            } else if(levelCount == 2) {
                makeLevel("level2.txt");
                levelCount = 3;
            } else if(levelCount == 3) {
                makeLevel("level3.txt");
                levelCount = 4;
            }
        }

        myBouncer.setX(myBouncer.getX() + BOUNCER_X_SPEED * elapsedTime);
        myBouncer.setY(myBouncer.getY() + BOUNCER_Y_SPEED * elapsedTime);
        myLifePower.setY(myLifePower.getY() + LIFE_POWER_Y_SPEED * elapsedTime);
        mySpeedPower.setY(mySpeedPower.getY() + SPEED_POWER_Y_SPEED * elapsedTime);
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) throws IOException {
        if (code == KeyCode.RIGHT && myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth() <= SIZE) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT && myPaddle.getX() >= 0) {
            myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
        }

        else if(code == KeyCode.SPACE){
            levelCount++;
        } else if(code == KeyCode.DIGIT1){
            makeLevel("level1.txt");
            levelCount = 2;
            score = 0;
            scoreText.setText("SCORE: " + score);
        } else if(code == KeyCode.R){
            reset();
        }
    }

    private void makeLevel(String levelNumber) throws IOException {
        level temp = new level();
        myBouncer = new bouncer(bouncerImage);
        myPaddle = new paddle(paddleImage, 3);
        myLifePower = new lifePower(lifePowerImage);
        mySpeedPower = new speedPower(speedPowerImage);
        root = new Group();
        myScene = temp.setupLevel(root, myPaddle, myBouncer, levelNumber, hpText, scoreText, score, myLifePower, mySpeedPower);
        myScene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        brickList = temp.getBrickList();
        mainStage.setScene(myScene);
        BOUNCER_X_SPEED = START_BOUNCER_X_SPEED;
        BOUNCER_Y_SPEED = START_BOUNCER_Y_SPEED;
        LIFE_POWER_Y_SPEED = 0;
        SPEED_POWER_Y_SPEED = 0;

    }

    public void reset(){
        myBouncer.setX(0);
        myBouncer.setY(0);
        myPaddle.setX(0);
        myPaddle.setY(0);
        myBouncer.setX(SIZE / 2 - myBouncer.getBoundsInLocal().getCenterX());
        myBouncer.setY(SIZE - myPaddle.getBoundsInLocal().getHeight() - myBouncer.getBoundsInLocal().getHeight());
        myPaddle.setX(SIZE / 2 - myPaddle.getBoundsInLocal().getCenterX());
        myPaddle.setY(SIZE - myPaddle.getBoundsInLocal().getHeight());
        BOUNCER_X_SPEED = START_BOUNCER_X_SPEED;
        BOUNCER_Y_SPEED = START_BOUNCER_Y_SPEED;
    }
    // What to do each time a key is pressed
//    private void handleMouseInput (double x, double y) {
//        if (myGrower.contains(x, y)) {
//            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
//            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
//        }
//    }


    public static void main (String[] args) {
        launch(args);
    }
}

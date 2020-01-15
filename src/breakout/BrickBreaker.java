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
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

import java.util.ArrayList;

public class BrickBreaker extends Application{

    public static final String TITLE = "BrickBreaker";
    public static final int SIZE = 420;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.BLACK;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static double BOUNCER_X_SPEED = 0;
    public static double BOUNCER_Y_SPEED = 130;
    public static final int MOVER_SPEED = 5;

    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";

    Image bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
    Image paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));

    private static Scene myScene;
    private static Stage mainStage;
    private bouncer myBouncer = new bouncer(bouncerImage,0);
    private paddle myPaddle = new paddle(paddleImage,3);
    ArrayList<brick> brickList = new ArrayList<>();
    Group root = new Group();
    int levelCount = 1;


    public void start (Stage stage){
        // attach scene to the stage and display it
        this.mainStage =  stage;
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        mainStage.setScene(myScene);
        mainStage.setTitle(TITLE);
        mainStage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }


    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        level temp = new level1();
        myBouncer = new bouncer(bouncerImage,0);
        myPaddle = new paddle(paddleImage,3);
        root = new Group();
        myScene = temp.setupLevel(root, myPaddle, myBouncer);
        brickList = temp.getBrickList();
        // make some shapes and set their properties

        // x and y represent the top left corner, so center it in window
//        myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
//        myBouncer.setY(height / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
//        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
//        myPaddle.setY(height - myPaddle.getBoundsInLocal().getHeight());

        // order added to the group is the order in which they are drawn
//        root.getChildren().add(myPaddle);
//        root.getChildren().add(myBouncer);

        // create a place to see the shapes
        //Scene scene = new Scene(root, width, height, background);
        // respond to input
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        //scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));

        return myScene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
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
            System.out.println(myPaddle.hitPoints);
            if(myPaddle.hitPoints == 0){
                exitScreen temp = new exitScreen();
                root = new Group();
                myScene = temp.setupLevel(root);
                mainStage.setScene(myScene);
            }
            myBouncer.setX(SIZE / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
            myBouncer.setY(SIZE / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
            BOUNCER_X_SPEED = 0;
            BOUNCER_Y_SPEED = 130;
        }

        for(int i = 0; i < brickList.size(); i++){
            if(myBouncer.getBoundsInParent().intersects(brickList.get(i).getBoundsInParent())){
                BOUNCER_Y_SPEED = BOUNCER_Y_SPEED * -1;
                brickList.get(i).hitPoints = brickList.get(i).hitPoints - 1;
                System.out.println(brickList.get(i).hitPoints);
                if (brickList.get(i).hitPoints == 0) {
                    brickList.get(i).setImage(null);
                    brickList.remove(i);
                }
            }
        }

        if(brickList.size() == 0) {
            levelCount++;
            level temp = new level2();
            myBouncer = new bouncer(bouncerImage,0);
            myPaddle = new paddle(paddleImage,3);
            root = new Group();
            myScene = temp.setupLevel(root, myPaddle, myBouncer);
            myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
            brickList = temp.getBrickList();
            mainStage.setScene(myScene);
            BOUNCER_X_SPEED = 0;
            BOUNCER_Y_SPEED = 130;
        }

        myBouncer.setX(myBouncer.getX() + BOUNCER_X_SPEED * elapsedTime);
        myBouncer.setY(myBouncer.getY() + BOUNCER_Y_SPEED * elapsedTime);

    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT && myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth() != SIZE) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT && myPaddle.getX() != 0) {
            myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
        }
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

package breakout;

/*
Author: Benjamin Lu (bll32)
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class BrickBreaker extends Application{

    private static final String TITLE = "BrickBreaker";
    private static final int SIZE = 630;
    private static final int FRAMES_PER_SECOND = 60;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private static final Paint BACKGROUND = Color.GREY;
    private static final double START_BOUNCER_X_SPEED = 0;
    private static final double START_BOUNCER_Y_SPEED = 300;
    private static final double POWER_Y_DROP_SPEED = 100;
    private static double LIFE_POWER_Y_SPEED = 0;
    private static double SPEED_POWER_Y_SPEED = 0;
    private static double PADDLE_POWER_Y_SPEED = 0;
    private static double BOUNCER_X_SPEED = START_BOUNCER_X_SPEED;
    private static double BOUNCER_Y_SPEED = 0;
    private static int MOVER_SPEED = 10;

    private static final String BOUNCER_IMAGE = "ball.gif";
    private static final String PADDLE_IMAGE = "paddle.gif";
    private static final String LIFE_POWER_IMAGE = "laserpower.gif";
    private static final String SPEED_POWER_IMAGE = "sizepower.gif";
    private static final String PADDLE_POWER_IMAGE = "extraballpower.gif";
    private static final String LONG_PADDLE_IMAGE = "long_paddle.gif";

    Image bouncerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
    Image paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
    Image lifePowerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(LIFE_POWER_IMAGE));
    Image speedPowerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(SPEED_POWER_IMAGE));
    Image paddlePowerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_POWER_IMAGE));
    Image longPaddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(LONG_PADDLE_IMAGE));

    private static Scene myScene;
    private static Stage mainStage;
    private bouncer myBouncer = new bouncer(bouncerImage);
    private paddle myPaddle = new paddle(paddleImage, 3);
    private ArrayList<brick> brickList = new ArrayList<>();
    private ArrayList<Image> brickImageList = new ArrayList<>();
    private Group root = new Group();
    private int levelCount = 0;
    private Text hpText = new Text();
    private Text scoreText = new Text();
    private int score = 0;
    private lifePower myLifePower = new lifePower(lifePowerImage);
    private speedPower mySpeedPower = new speedPower(speedPowerImage);
    private paddlePower myPaddlePower = new paddlePower(paddlePowerImage);
    private int brickKills = 0;
    private AudioClip audioClip = new AudioClip(Paths.get("./resources/WOO.wav").toUri().toString());
    private AudioClip theme = new AudioClip(Paths.get("./resources/Blues.wav").toUri().toString());
    private AudioClip death = new AudioClip(Paths.get("./resources/lego-yoda-death-sound-effect.wav").toUri().toString());

    /*
    - set up the start screen with the title, rules, and directions to start the game
    - method depends on setupGame which queues up the correct scene to display the start
     */
    public void start (Stage stage) throws IOException{
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

    /*
    - sets up the title screen
    - depends on reset which places the paddle and ball in the correct positions
    - returns the scene to be displayed
     */
    private Scene setupGame (int width, int height, Paint background) throws IOException {
        theme.play(50);
        titleScreen temp = new titleScreen();
        brickImageList = new brick(null, 0, 0).getBrickImageList();
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
        reset();
        return myScene;
    }

    /*
    - step is essential for all game interactions and operations
    - goes through time and is constantly looking for detections and game states
    - ball collision with paddle, wall, and bricks
    - determines of all bricks are gone and move to the next level
    - detects power up pick ups and activates their respective powers
    - activates event handlers to detect cheat code activation
     */
    private void step (double elapsedTime) throws IOException{

        /*
        - ball and wall detection
         */
        if(myBouncer.getX() <= 0 || myBouncer.getX() + myBouncer.getBoundsInLocal().getWidth() >= SIZE){
            BOUNCER_X_SPEED = BOUNCER_X_SPEED * -1;
            audioClip.play(50);
        }

        if(myBouncer.getY() <= 0){
            BOUNCER_Y_SPEED = BOUNCER_Y_SPEED * -1;
            audioClip.play(50);
        }

        /*
        - ball and paddle detection
        - ball's resulting direction is dependent on where on the paddle it hits
        - sound is played when collision occurs
         */
        if(myBouncer.getBoundsInParent().intersects(myPaddle.getBoundsInParent())){
            double bouncerCenter = (myBouncer.getBoundsInParent().getMinX() + myBouncer.getBoundsInParent().getMaxX())/2;
            double paddleCenter = (myPaddle.getBoundsInParent().getMinX() + myPaddle.getBoundsInParent().getMaxX())/2;
            double centerDiff = (bouncerCenter - paddleCenter) / (myPaddle.getBoundsInLocal().getWidth());
            double angle = (centerDiff * 0.9 * Math.PI);
            double speedTotal = Math.pow(Math.pow(BOUNCER_X_SPEED, 2) + Math.pow(BOUNCER_Y_SPEED, 2), 0.5);

            BOUNCER_X_SPEED = speedTotal * Math.sin(angle);
            BOUNCER_Y_SPEED = speedTotal * Math.cos(angle) * -1;

            audioClip.play(50);
        }

        /*
        - detect of ball goes out of bounds and reduces lives
        - detect if the player dies and activate the game over screen
         */
        if(myBouncer.getY() + myBouncer.getBoundsInLocal().getHeight() >= SIZE){
            myPaddle.hitPoints = myPaddle.hitPoints - 1;
            hpText.setText("LIVES: " + myPaddle.hitPoints);
            if(myPaddle.hitPoints == 0){
                death.play(50);
                exitScreen temp = new exitScreen();
                myPaddle.setImage(null);
                myBouncer.setImage(null);
                root = new Group();
                myScene = temp.setupLevel(root, score, brickKills);
                mainStage.setScene(myScene);
                myScene.setOnKeyPressed(e -> {
                    try {
                        handleKeyInput(e.getCode());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
            reset();
        }

        /*
        - run through all the bricks on the level to see if any are hit by the ball
        - if the brick is destroyed remove it from the scene and activate a power up if necessary
        - when the brick loses health and is not destroyed update the appearance of the brick
         */
        for(int i = 0; i < brickList.size(); i++){
            if(myBouncer.getBoundsInParent().intersects(brickList.get(i).getBoundsInParent())){
                BOUNCER_Y_SPEED = BOUNCER_Y_SPEED * -1;
                brickList.get(i).hitPoints = brickList.get(i).hitPoints - 1;
                if (brickList.get(i).hitPoints == 0) {
                    audioClip.play(50);
                    brickList.get(i).setImage(null);
                    score = score + brickList.get(i).value;
                    brickKills++;
                    scoreText.setText("SCORE: " + score);
                    if((brickList.get(i).getX() == myLifePower.getX()) && (brickList.get(i).getY() == myLifePower.getY())){
                        LIFE_POWER_Y_SPEED = POWER_Y_DROP_SPEED;
                    }

                    if((brickList.get(i).getX() == mySpeedPower.getX()) && (brickList.get(i).getY() == mySpeedPower.getY())){
                        SPEED_POWER_Y_SPEED = POWER_Y_DROP_SPEED;
                    }

                    if((brickList.get(i).getX() == myPaddlePower.getX()) && (brickList.get(i).getY() == myPaddlePower.getY())){
                        PADDLE_POWER_Y_SPEED = POWER_Y_DROP_SPEED;
                    }
                    brickList.remove(i);
                } else {
                    brickList.get(i).setImage(brickImageList.get(brickList.get(i).hitPoints-1));
                }
            }
        }

        /*
        - detect if the paddle collects the power up and activate the powers when collected
         */
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

        if(myPaddle.getBoundsInParent().intersects(myPaddlePower.getBoundsInParent())){
            PADDLE_POWER_Y_SPEED = PADDLE_POWER_Y_SPEED * -1;
            myPaddle.setImage(longPaddleImage);
            myPaddlePower.setImage(null);
        }

        /*
        - change the appearance of bricks according to reducing health
         */
        if(brickList.size() == 0) {
            if(levelCount == 1) {
                makeLevel("level1.txt");
                themeReset();
                levelCount = 2;
            } else if(levelCount == 2) {
                makeLevel("level2.txt");
                themeReset();
                levelCount = 3;
            } else if(levelCount == 3) {
                makeLevel("level3.txt");
                themeReset();
                levelCount = 4;
            } else if(levelCount == 4) {
                themeReset();
                winScreen temp = new winScreen();
                root = new Group();
                myScene = temp.setupLevel(root, score, brickKills);
                mainStage.setScene(myScene);
                myScene.setOnKeyPressed(e -> {
                    try {
                        handleKeyInput(e.getCode());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }

        /*
        - if no interaction is happening have the ball and power ups move
         */
        myBouncer.setX(myBouncer.getX() + BOUNCER_X_SPEED * elapsedTime);
        myBouncer.setY(myBouncer.getY() + BOUNCER_Y_SPEED * elapsedTime);
        myLifePower.setY(myLifePower.getY() + LIFE_POWER_Y_SPEED * elapsedTime);
        mySpeedPower.setY(mySpeedPower.getY() + SPEED_POWER_Y_SPEED * elapsedTime);
        myPaddlePower.setY(myPaddlePower.getY() + PADDLE_POWER_Y_SPEED * elapsedTime);
    }

    private void handleKeyInput (KeyCode code) throws IOException {
        /*
        - key event handler
        - right and left keys move the paddle
        - space starts the game from the title screen
        - 1,2,3 activate the corresponding levels
        - r key resets the position of ball and paddle
        - f increases the speed of paddle movement
         */
        if (code == KeyCode.RIGHT && myPaddle.getX() + myPaddle.getBoundsInLocal().getWidth() <= SIZE) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        } else if (code == KeyCode.LEFT && myPaddle.getX() >= 0) {
            myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
        } else if(code == KeyCode.SPACE){
            levelCount++;
        } else if(code == KeyCode.DIGIT1){
            makeLevel("level1.txt");
            themeReset();
            levelCount = 2;
        } else if(code == KeyCode.DIGIT2) {
            makeLevel("level2.txt");
            themeReset();
            levelCount = 3;
        } else if(code == KeyCode.DIGIT3) {
            makeLevel("level3.txt");
            themeReset();
            levelCount = 4;
        } else if(code == KeyCode.R){
            reset();
        } else if(code == KeyCode.F){
            MOVER_SPEED = MOVER_SPEED + 5;
        }
    }

    /*
    - construct a desired level by passing in a text file
    - assign the level to the key event handler
    - reset the speeds of all level elements
     */
    private void makeLevel(String levelNumber) throws IOException {
        level temp = new level();
        myBouncer = new bouncer(bouncerImage);
        myPaddle = new paddle(paddleImage, 3);
        myLifePower = new lifePower(lifePowerImage);
        mySpeedPower = new speedPower(speedPowerImage);
        myPaddlePower = new paddlePower(paddlePowerImage);
        root = new Group();
        myScene = temp.setupLevel(root, myPaddle, myBouncer, levelNumber, hpText, scoreText, score, myLifePower, mySpeedPower, myPaddlePower);
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
        PADDLE_POWER_Y_SPEED = 0;

    }

    /*
    - reset the position and speed of all game elements
    - usually called when making a new level
     */
    private void reset(){
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

    private void themeReset(){
        theme.stop();
        theme.play(50);
    }

    /*
    - launch the game
     */
    public static void main (String[] args) {
        launch(args);
    }
}

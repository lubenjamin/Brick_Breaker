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

public class level extends Group {

    public static final int SIZE = 630;
    public static final Paint BACKGROUND = Color.BLACK;

    ArrayList<brick> brickList = new ArrayList<>();
    ArrayList<Integer> powerList = new ArrayList<>();

    Scene newLevel;

    public level(){
        super();
    }

    public Scene setupLevel(Group root, paddle myPaddle, bouncer myBouncer, String fileName, Text hpText, Text scoreText, int score,
                            lifePower myLifePower, speedPower mySpeedPower) throws IOException {

        myBouncer.setX(SIZE / 2 - myBouncer.getBoundsInLocal().getCenterX());
        myBouncer.setY(SIZE - myPaddle.getBoundsInLocal().getHeight() - myBouncer.getBoundsInLocal().getHeight());
        myPaddle.setX(SIZE / 2 - myPaddle.getBoundsInLocal().getCenterX());
        myPaddle.setY(SIZE - myPaddle.getBoundsInLocal().getHeight());

        hpText.setText("LIVES: " + myPaddle.hitPoints);
        hpText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        hpText.setX(SIZE - hpText.getBoundsInLocal().getWidth());
        hpText.setY(hpText.getBoundsInLocal().getHeight());
        hpText.setFill(Color.RED);

        scoreText.setText("SCORE: " + score);
        scoreText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        //scoreText.setX(SIZE - hpText.getBoundsInLocal().getWidth());
        scoreText.setY(hpText.getBoundsInLocal().getHeight());
        scoreText.setFill(Color.RED);

        root.getChildren().add(hpText);
        root.getChildren().add(scoreText);
        root.getChildren().add(myPaddle);
        root.getChildren().add(myBouncer);
        root.getChildren().add(myLifePower);
        root.getChildren().add(mySpeedPower);


        Path source = Paths.get("./resources/"+fileName);
        Scanner sc = new Scanner(source);
        int rows = 10;
        int columns = 6;
        while (sc.hasNextLine()) {
            for (int i = 0; i < 10; i++) {
                String[] line = sc.nextLine().split(" ");
                for (int j = 0; j < 9; j++) {
                    if(Integer.parseInt(line[j]) != 0){
                        brick newBrick = new brick(null, 0).returnBrick(Integer.parseInt(line[j]));
                        newBrick.setX(newBrick.getBoundsInLocal().getWidth() * j);
                        newBrick.setY(newBrick.getBoundsInLocal().getHeight() * i);
                        root.getChildren().add(newBrick);
                        brickList.add(newBrick);

//                        if(Integer.parseInt(line[j]) == 1){
//                            System.out.println("DEBUG");
//                            myLifePower.setX(newBrick.getX());
//                            myLifePower.setY(newBrick.getY());
//                        }
                    }
                }
            }
        }

        for(int i = 0; i < 2; i++){
            powerList.add(generateRandomInt(brickList.size()));
        }

        myLifePower.setX(brickList.get(powerList.get(0)).getX());
        myLifePower.setY(brickList.get(powerList.get(0)).getY());

        mySpeedPower.setX(brickList.get(powerList.get(1)).getX());
        mySpeedPower.setY(brickList.get(powerList.get(1)).getY());


        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }

    public ArrayList<brick> getBrickList(){
        return brickList;
    }

    public ArrayList<Integer> getPowerList(){
        return powerList;
    }

    public static int generateRandomInt(int upperRange){
        Random random = new Random();
        return random.nextInt(upperRange);
    }

}

package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/*
- winScreen is the level that's displayed if the player completes all three levels
- displays the overall game score and the number of bricks destroyed
- provides the player with the option to play again
 */
public class winScreen extends level {
    private static final int SIZE = 630;
    private static final Paint BACKGROUND = Color.BLACK;
    private Scene newLevel;

    /*
    - construct the winScreen object
     */
    public winScreen(){
        super();
    }

    /*
    - create and returns the scene to be displayed
    - takes in the group to add text to, score, and brickKills to be displayed
     */
    public Scene setupLevel(Group root, int score, int brickKills){
        Text winText = new Text("YOU WIN\n\n SCORE: " + score + "\n\n" + "BRICKS KILLED: " + brickKills +
                "\n\n" + "PRESS '1' TO PLAY AGAIN");
        winText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        winText.setFill(Color.WHITE);
        winText.setTextAlignment(TextAlignment.CENTER);
        winText.setX(SIZE / 2 - winText.getBoundsInLocal().getWidth() / 2);
        winText.setY(SIZE / 2 - winText.getBoundsInLocal().getHeight() / 2);

        root = new Group(winText);
        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }
}

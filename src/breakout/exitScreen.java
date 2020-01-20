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
- exitScreen is the type of level that is displayed when the player loses all lives
 */
public class exitScreen extends level {

    private static final int SIZE = 630;
    private static final Paint BACKGROUND = Color.BLACK;
    private Scene newLevel;
    public exitScreen(){
        super();
    }

    /*
    - setupLevel creates the scene and returns the scene to be added to the mainstage
    - takes in a group, score, and brickKills
    - score and brickKills are displayed on the exit screen
    - players have the option to restart game when dead
     */
    public Scene setupLevel(Group root, int score, int brickKills){
        Text endText = new Text("YOU LOSE\n\n SCORE: " + score + "\n\n" + "BRICKS KILLED: " + brickKills +
                "\n\n" + "PRESS '1' TO PLAY AGAIN");
        endText.setTextAlignment(TextAlignment.CENTER);
        endText.setFont(Font.font("Veranda", FontWeight.EXTRA_BOLD, 40));
        endText.setFill(Color.RED);
        endText.setX(SIZE / 2 - endText.getBoundsInLocal().getWidth() / 2);
        endText.setY(SIZE / 2 - endText.getBoundsInLocal().getHeight() / 2);
        root = new Group(endText);
        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }

}

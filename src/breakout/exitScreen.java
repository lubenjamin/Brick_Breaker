package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class exitScreen extends level {

    public static final int SIZE = 420;
    public static final Paint BACKGROUND = Color.WHITE;

    Scene newLevel;


    public exitScreen(){
        super();
    }

    public Scene setupLevel(Group root){
        Text endText = new Text("YOU LOSE :)");
        endText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
        endText.setX(SIZE / 2 - endText.getBoundsInLocal().getWidth() / 2);
        endText.setY(SIZE / 2 - endText.getBoundsInLocal().getHeight() / 2);
        root = new Group(endText);
        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }

}

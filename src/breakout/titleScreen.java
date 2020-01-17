package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class titleScreen extends level {

    public static final int SIZE = 560;
    public static final Paint BACKGROUND = Color.BLACK;

    Scene newLevel;

    public titleScreen(){
        super();
    }

    public Scene setupLevel(Group root){
        Text endText = new Text("BRICK BREAKER");
        endText.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
        endText.setFill(Color.RED);
        endText.setX(SIZE / 2 - endText.getBoundsInLocal().getWidth() / 2);
        endText.setY(SIZE / 2 - endText.getBoundsInLocal().getHeight() / 2);
        root = new Group(endText);
        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }
}

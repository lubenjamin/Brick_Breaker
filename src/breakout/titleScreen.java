package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/*
- title screen is the first level seen when the game is started
- contains the game logo, rules, directions to start, and author name
 */
public class titleScreen extends level {

    private static final int SIZE = 630;
    private static final Paint BACKGROUND = Color.BLACK;
    private static final String LOGO_IMAGE = "logo.gif";
    private Image logoImage = new Image(this.getClass().getClassLoader().getResourceAsStream(LOGO_IMAGE));
    private ImageView myLogo = new ImageView(logoImage);
    private Scene newLevel;

    /*
    - construct the title scene class
     */
    public titleScreen(){
        super();
    }

    /*
    - create and return the titleScene to be displayed
    - takes in a group and adds the title, rules, and author text to the group
    - constructs the scene to be displayed from root
     */
    public Scene setupLevel(Group root){
        ImageView title = myLogo;
        title.setX(SIZE / 2 - title.getBoundsInLocal().getWidth() / 2);

        Text directions = new Text("RULES: \n -DESTROY ALL BRICKS \n -THREE LIVES PER LEVEL\n " +
                "-COMPLETE ALL LEVELS TO WIN");
        directions.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        //directions.setTextAlignment(TextAlignment.CENTER);
        directions.setFill(Color.WHITE);
        directions.setX(SIZE / 2 - directions.getBoundsInLocal().getWidth() / 2);
        directions.setY(title.getBoundsInLocal().getHeight());

        Text start = new Text("(PRESS SPACE TO PLAY)");
        start.setFill(Color.RED);
        start.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        start.setX(SIZE / 2 - start.getBoundsInLocal().getWidth() / 2);
        start.setY(SIZE - start.getBoundsInLocal().getHeight() * 5);

        Text name = new Text("[BY BENJAMIN LU]");
        name.setFill(Color.WHITE);
        name.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        name.setX(SIZE - name.getBoundsInLocal().getWidth());
        name.setY(SIZE - name.getBoundsInLocal().getHeight());

        root = new Group(title);
        root.getChildren().add(directions);
        root.getChildren().add(start);
        root.getChildren().add(name);
        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }
}

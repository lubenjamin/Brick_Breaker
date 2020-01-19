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
import javafx.scene.text.TextAlignment;

public class titleScreen extends level {

    public static final int SIZE = 560;
    public static final Paint BACKGROUND = Color.BLACK;
    public static final String LOGO_IMAGE = "logo.gif";
    Image logoImage = new Image(this.getClass().getClassLoader().getResourceAsStream(LOGO_IMAGE));
    ImageView myLogo = new ImageView(logoImage);




    Scene newLevel;

    public titleScreen(){
        super();
    }

    public Scene setupLevel(Group root){
//        Text title = new Text("BRICK BREAKER\n");
//        title.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 40));
//        title.setFill(Color.BROWN);
        ImageView title = myLogo;
        title.setX(SIZE / 2 - title.getBoundsInLocal().getWidth() / 2);
        //title.setY(title.getBoundsInLocal().getHeight() / 2);

        Text rules = new Text("");

        Text directions = new Text("PRESS SPACE TO START");
        directions.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 20));
        directions.setFill(Color.WHITE);
        directions.setX(SIZE / 2 - directions.getBoundsInLocal().getWidth() / 2);
        directions.setY(SIZE / 2 - title.getBoundsInLocal().getHeight() / 2 + title.getBoundsInLocal().getHeight());

        root = new Group(title);
        root.getChildren().add(directions);
        newLevel = new Scene(root, SIZE, SIZE, BACKGROUND);
        return newLevel;
    }
}

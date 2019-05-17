package trying;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Lenovo
 */
public class Trying extends Application {

    @Override
    public void start(Stage primaryStage) {
        Design d = new Design ();
        UserSubmarine sb = new UserSubmarine ();    
        MyPan mp = new MyPan(500,500);
        MoveSubmarine msm = new MoveSubmarine(sb,mp);
        GamePane gp = new GamePane(mp,sb,msm);
        
        Pane p = new Pane();
        p.getChildren().addAll(d,gp,mp,sb,msm);
        MainMenu m = new MainMenu(p,msm,mp,primaryStage);
        Scene s = new Scene(m);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(s);
        primaryStage.show();
//        msm.requestFocus(); 
//        p.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

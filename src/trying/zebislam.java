package trying;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Islam
 */
class Score {

    private static int iscore = 0;
    private static Property iscoredp = new SimpleIntegerProperty();

    public static void setScore(int iscore) {
        Score.iscore += iscore;
    }

    public static int getScore() {
        return iscore;
    }

    public static int getlvl() {
        return (Score.iscore / 100) + 1;
    }

    public static IntegerProperty getScoreProperty() {
        iscoredp.setValue(iscore);
        return (IntegerProperty) iscoredp;
    }
}

class MoveSubmarine extends Pane {
    private Timeline t1, t2;
    private boolean Move = true;

    MoveSubmarine(UserSubmarine s, MyPan mp) {
        this.setOnKeyPressed(
                (KeyEvent e) -> {
                    if (Move) {
                        if (s.GetPositionX() > 0) {
                            if (e.getCode() == KeyCode.LEFT) {
                                s.SetPositionX(s.GetPositionX() - 5);
                            }
                        }
                        if (s.GetPositionX() < 600) {
                            if (e.getCode() == KeyCode.RIGHT) {
                                s.SetPositionX(s.GetPositionX() + 5);
                            }
                        }
                        if (e.getCode() == KeyCode.SPACE) {
                            Missiles m = new Missiles(s);
                            this.getChildren().add(m);

                        }
                    }
                }
        );

        t1 = new Timeline(new KeyFrame(Duration.millis(100),
                e1 -> {
                    for (Node n : getChildren()) {
                        Missiles m1 = ((Missiles) n);
                        m1.SetPositionY(m1.GetPositionY() + 5);
                    }
                }
        ));
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();
        t2 = new Timeline(new KeyFrame(Duration.millis(10), (e) -> {
            for (Node n : getChildren()) {
                Missiles m1 = ((Missiles) n);
                for (Node nn : mp.getChildren()) {
                    if (nn instanceof AutoSubmarines) {
                        AutoSubmarines i = ((AutoSubmarines) nn);
                        if (i.getImage().contains(m1.GetPositionX(), m1.GetPositionY())) {
                            this.getChildren().remove(n);
                            mp.getChildren().remove(nn);
                            ((AutoSubmarines) nn).stopt();
                            Score.setScore(50);
                            
                        }
                    }
                }
            }
        }));
        t2.setCycleCount(-1);
        t2.play();
    }

    public void stopt() {
        t1.stop();
        t2.stop();
    }

    public void setMove(boolean M) {
        Move = M;
    }
}

class UserSubmarine extends Pane {

    private ImageView iv;
    private int Width = 100;
    private int Height = 50;

    UserSubmarine() {
        iv = new ImageView("file:src/submarine0.png");
        SetPositionX(250);
        SetPositionY(50);
        iv.setFitWidth(Width);
        iv.setFitHeight(Height);
        this.getChildren().add(iv);

    }

    public ImageView getImage() {
        return iv;
    }

    public int getW() {
        return Width;
    }

    public int getH() {
        return Height;
    }

    public double GetPositionX() {
        return iv.getX();
    }

    public double GetPositionY() {
        return iv.getY();
    }

    public void SetPositionX(double newpos) {
        iv.setX(newpos);
    }

    public void SetPositionY(double newpos) {
        iv.setY(newpos);
    }
}

class Missiles extends Pane {

    private ImageView iv;
    private Timeline t1;

    Missiles(UserSubmarine sb) {
        iv = new ImageView("file:src/missile.png");
        iv.setFitHeight(20);
        iv.setFitWidth(10);
        iv.setRotate(270);
        iv.setY(sb.GetPositionY() + 40);
        iv.setX(sb.GetPositionX() + 15);
        this.getChildren().add(iv);
    }

    Missiles() {
        iv = new ImageView("file:src/missile.png");
        iv.setFitHeight(20);
        iv.setFitWidth(10);
        iv.setRotate(270);
        this.getChildren().add(iv);
    }

    public double GetPositionX() {
        return iv.getX();
    }

    public double GetPositionY() {
        return iv.getY();
    }

    public void SetPositionX(double newpos) {
        iv.setX(newpos);
    }

    public void SetPositionY(double newpos) {
        iv.setY(newpos);
    }

    public void stopt() {
        t1.stop();
    }
}


class Design extends Pane {

    private Rectangle r1, r2;

    Design() {
        r1 = new Rectangle(700, 90);
        r1.setFill(Color.rgb(197, 239, 247));
        r2 = new Rectangle(700, 500);
        r2.setFill(Color.rgb(44, 130, 201));
        r2.setY(90);
        this.getChildren().addAll(r1, r2);
    }
}

class GamePane extends Pane {

    private Label score, level;
    private Missiles life;
    private HBox h1;
    private HBox h2;
    private TextField t1 = new TextField("Hello");
    private Timeline tl;
    private int tries = 0;
    private AudioClip ac;
    GamePane(MyPan mp, UserSubmarine sb, MoveSubmarine msm) {
        h2 = Lives(5);
        t1.setLayoutX(100);
        t1.setLayoutY(100);
        level = new Label("Level: 1");
        score = new Label();
        tl = new Timeline(new KeyFrame(Duration.millis(100),
                e -> {
                    Score.getScoreProperty().addListener(ov -> {
                        score.setText(Score.getScore() + "");
                        ac = new AudioClip("file:src/Audio/Explosion_1.mp3");
                        ac.play();
                        if ((((double) Score.getScore()) % 100.0) == 0) {
                            mp.incsubmarinerate(((double) Score.getlvl()) / 10);
                            level.setText("Level : " + Score.getlvl());

                        }
                    });
                    score.setText(Score.getScore() + "");
                    for (Node n : mp.getChildren()) {
                        AutoSubmarines x = ((AutoSubmarines) n);
                        for (Node nn : x.getChildren()) {
                            if (nn instanceof Rocket) {
                                Rocket r = (Rocket) nn;
                                if (sb.getImage().contains(r.getX(), r.getY())) {
                                    ac = new AudioClip("file:src/Audio/Explosion_2.mp3");
                                    ac.play();
                                    tries++;
                                    for (Node n1 : h2.getChildren()) {
                                        if (tries % 2 == 0) {
                                            Missiles m = (Missiles) n1;
                                            h2.getChildren().remove(m);
                                            break;
                                        }
                                    }
                                    x.getChildren().remove(nn);

                                }
                                if (tries >= 10) {
                                    if (tries == 10) {
                                        ac = new AudioClip("file:src/Audio/GameOver.mp3");
                                        ac.play();
                                    }
                                    mp.stopt();
                                    x.stopt();
                                    msm.stopt();
                                    msm.setMove(false);
                                    tries++;
                                    //Game Over
                                }
                            }
                        }
                    }
                }
        ));
        level.setLayoutX(320);
        score.setLayoutX(665);
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        level.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        score.setFont(Font.font("Arial", FontWeight.BOLD, 14));
//        h1 = new HBox();
//        h1.setSpacing(250);
//        h1.getChildren().addAll(h2,level,score);
        this.getChildren().addAll(h2, level, score);
    }

    public HBox Lives(int n) {
        h2 = new HBox();
        for (int i = 0; i < 5; i++) {
            life = new Missiles();
            h2.getChildren().add(life);
        }
        return h2;
    }

    public void setLives(HBox h2) {
        h2.getChildren().remove(life);
    }
}
class MainMenu extends Pane {
    private ImageView iv;
    private ImageView start;
    private ImageView exit;
    private VBox v;
    private AudioClip ac;
    MainMenu(Pane p,MoveSubmarine msm,MyPan mp,Stage primaryStage){
        iv = new ImageView ("file:src/sub.jpeg");
        start = new ImageView("file:src/start.png");
        exit = new ImageView("file:src/exit.png");
        ac = new AudioClip("file:src/Audio/Explosion_1.mp3");
        v = new VBox();
        iv.setFitWidth(700);
        iv.setFitHeight(500);
        start.setFitWidth(200);
        start.setFitHeight(100);        
        exit.setFitWidth(200);
        exit.setFitHeight(100);
        start.setOnMouseClicked(
        e->{
            ac.play();
            this.getChildren().removeAll(iv,v);
            this.getChildren().add(p);
            mp.playtimelines();
            msm.requestFocus();
                    }
        );
        exit.setOnMouseClicked(
        e->{
            ac.play();
            primaryStage.close();
        }
        );
        v.setLayoutX(180);
        v.setLayoutY(100);
        v.getChildren().addAll(start,exit);
        this.getChildren().addAll(iv,v);
    }
}
package trying;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
class MyPan extends Pane {

    private double submarinerate = 3000, width, height;
    private Timeline submarinegenerator = new Timeline();
    private AutoSubmarines asm;
    public MyPan(double width, double height) {
        this.height = height;
        this.width = width;
        submarinegenerator.getKeyFrames().add(new KeyFrame(Duration.millis(submarinerate), (e) -> {
            asm = new AutoSubmarines(width, height);
            this.getChildren().add(asm);
        }));
        submarinegenerator.setCycleCount(-1);

    }

    public void playtimelines() { //produce marines
        submarinegenerator.play();
    }
    public void stopt(){//stop producing marines
        submarinegenerator.stop();
    }
    public void incsubmarinerate(double dec){
        submarinegenerator.setRate(1+dec);
        System.out.println(submarinegenerator.getRate());
    }
}

class AutoSubmarines extends Pane {

    private Image[] iv = {new Image("file:src/submarine1.png"),
        new Image("file:src/submarine2.png"), new Image("file:src/submarine3.png")};
    private ImageView i;
    private double submarineheight = 50, submarinewidth = 100, submarinemotionrate = 100, speed = 5, width, height, rocketsrate = 2000;
    private Timeline submarinemotion, rocketsgenerator;

    public AutoSubmarines(double width, double height) {
        this.width = width;
        this.height = height;
        i = new ImageView();
        i.setImage(iv[(int) (Math.random() * 3)]);
        i.setFitHeight(this.submarineheight);
        i.setFitWidth(this.submarinewidth);
        i.setX(-this.submarinewidth);
        i.setY((Math.random() * (this.height / 2)) + (this.height / 2) - (this.submarineheight));
        this.submarinemotion = new Timeline(new KeyFrame(Duration.millis(this.submarinemotionrate), (e) -> {
            i.setX(i.getX() + this.speed);
        }));
        rocketsgenerator = new Timeline(new KeyFrame(Duration.millis(this.rocketsrate), (ee) -> {
            Rocket r = new Rocket(this);
            this.getChildren().add(r);
        }));
        this.getChildren().add(i);
        rocketsgenerator.setCycleCount(-1);
        rocketsgenerator.play();
        submarinemotion.setCycleCount(-1);
        this.starttimelines();
    }
    public double getX() {
        return this.i.getX();
    }

    public double getY() {
        return this.i.getY();
    }
     
    public ImageView getImage(){
        return this.i;
    }
    //any new submarine does not get influence from this methods
    public void starttimelines() { //starts moving of the current submarines
        this.submarinemotion.play();
    }

    public void stopt() { //stops current submarines from moving
        this.submarinemotion.stop();
        this.rocketsgenerator.stop();
    }
}


class Rocket extends ImageView {

    private Image iv = new Image("file:src/missile.png");
    private double rocketwidth = 10, rocketheight = 20, rocketrate = 100;
    Timeline rocketmotion;

    public Rocket(AutoSubmarines asm) {
        this.setImage(iv);
        this.setFitHeight(this.rocketheight);
        this.setFitWidth(this.rocketwidth);
        this.setX(asm.getX());
        this.setY(asm.getY());
        rocketmotion = new Timeline(new KeyFrame(Duration.millis(rocketrate), (e) -> {
            this.setY(this.getY() - 10);
        }));
        rocketmotion.setCycleCount(-1);
        rocketmotion.play();
    }
}


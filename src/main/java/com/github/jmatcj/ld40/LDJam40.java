package com.github.jmatcj.ld40;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LDJam40 extends Application {

    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(200, 200);
        Scene scene = new Scene(new Group(canvas));
        primaryStage.setScene(scene);

        Button bt = new Button(Resources.STONE, new Image("/images/Button_15-128.png"));

        scene.setOnMouseClicked(event -> {
            if (event.getX() > 10 && event.getX() < 138) {
                if (event.getY() > 10 && event.getY() < 138) {
                    bt.onClick();
                }
            }
        });


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                canvas.getGraphicsContext2D().setFill(Color.RED);
                canvas.getGraphicsContext2D().fillRect(10, 10, 10, 10);
                canvas.getGraphicsContext2D().drawImage(bt.getImage(), 10, 10);
            }
        }.start();

        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {

    }

    public static void main(String[] args) {
        launch(LDJam40.class, args);
    }
}

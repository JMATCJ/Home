package com.github.jmatcj.ld40;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                canvas.getGraphicsContext2D().setFill(Color.RED);
                canvas.getGraphicsContext2D().fillRect(10, 10, 10, 10);
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

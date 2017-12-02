package com.github.jmatcj.ld40;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class LDJam40 extends Application {

    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas();
        Scene scene = new Scene(new Group(canvas));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {

    }

    public static void main(String[] args) {
        launch(LDJam40.class, args);
    }
}

package com.github.jmatcj.ld40;

import com.github.jmatcj.ld40.data.Resources;
import com.github.jmatcj.ld40.gui.Button;
import com.github.jmatcj.ld40.util.AssetLoader;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LDJam40 extends Application {
    private Game game;

    @Override
    public void init() throws Exception {
        // Load resources
        // At this stage, the stage (heh) hasn't been shown yet
        AssetLoader.initialize();
        game = new Game();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1280, 720);
        Scene scene = new Scene(new Group(canvas));
        primaryStage.setScene(scene);

        Button bt = new Button(Resources.FOOD, AssetLoader.getImage("test_button.png"));

        scene.setOnMouseClicked(event -> {
            if (event.getX() > bt.getX() && event.getX() < bt.getImage().getWidth() + bt.getX()) {
                if (event.getY() > bt.getY() && event.getY() < bt.getImage().getHeight() + bt.getY()) {
                    bt.click(game);
                }
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                canvas.getGraphicsContext2D().setFill(Color.RED);
                canvas.getGraphicsContext2D().fillRect(10, 10, 10, 10);
                bt.draw(canvas.getGraphicsContext2D(), 100, 100);
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

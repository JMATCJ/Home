package com.github.jmatcj.ld40;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.util.AssetLoader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class LDJam40 extends Application {
    private AnimationTimer gameLoop;
    private Game game;

    @Override
    public void init() throws Exception {
        // Load resources
        // At this stage, the stage (heh) hasn't been shown yet
        AssetLoader.initialize(getParameters().getRaw().contains("-nomusic"));
        game = new Game(getParameters().getRaw().contains("-nomusic"));
        Planet.debug(getParameters().getRaw().contains("-debug"));
        game.setNoCooldown(getParameters().getRaw().contains("-nocooldown"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1280, 720);
        Scene scene = new Scene(new Group(canvas));
        primaryStage.setScene(scene);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        scene.setOnMouseClicked(event -> game.onClick(event));

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.update(now);

                gc.clearRect(0, 0, 1280, 720);
                gc.drawImage(game.getCurrentPlanet().getBackground(), 0, 0);
                game.draw(gc);
            }
        };
        gameLoop.start();

        primaryStage.show();

        game.playTheme();
    }

    @Override
    public void stop() throws Exception {
        gameLoop.stop();
    }

    public static void main(String[] args) {
        launch(LDJam40.class, args);
    }
}

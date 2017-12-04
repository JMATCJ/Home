package com.github.jmatcj.ld40;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.util.AssetLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LDJam40 extends Application {
    private Timeline gameLoop;
    private Game game;

    @Override
    public void init() throws Exception {
        // Load resources
        // At this stage, the stage (heh) hasn't been shown yet
        AssetLoader.initialize(getParameters().getRaw().contains("-nomusic"));
        game = new Game();
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

        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame loop = new KeyFrame(Duration.millis(16.67), event -> { // ~60FPS
            long now = System.nanoTime();
            game.update(now);

            gc.clearRect(0, 0, 1280, 720);
            gc.drawImage(game.getCurrentPlanet().getBackground(), 0, 0);
            game.draw(gc);
        });

        gameLoop.getKeyFrames().add(loop);
        gameLoop.play();

        primaryStage.show();

        //TODO: FIX THIS DAMNIT
        if (!getParameters().getRaw().contains("-nomusic")) {
            MediaPlayer player = new MediaPlayer(AssetLoader.getMusic("planet_one_theme.mp3"));
            player.setVolume(0.2);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
        }
    }

    @Override
    public void stop() throws Exception {
        gameLoop.stop();
    }

    public static void main(String[] args) {
        launch(LDJam40.class, args);
    }
}

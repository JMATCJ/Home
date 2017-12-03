package com.github.jmatcj.ld40;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.gui.Button;
import com.github.jmatcj.ld40.gui.Text;
import com.github.jmatcj.ld40.util.AssetLoader;
import java.util.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LDJam40 extends Application {
    private AnimationTimer gameLoop;
    private Game game;

    @Override
    public void init() throws Exception {
        // Load resources
        // At this stage, the stage (heh) hasn't been shown yet
        AssetLoader.initialize(getParameters().getRaw().contains("-nomusic"));
        game = new Game();
        Planet.debug(getParameters().getRaw().contains("-debug"));
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
                new Text(Color.BLACK, 48, 10, 40).draw(gc, "Planet: " + game.getCurrentPlanet().getName());
                //gc.fillRect(890, 530, 50, 5);
                for (Map.Entry<Button, Text> e : game.getButtonsOnDisplay().entrySet()) {
                    Button bt = e.getKey();
                    gc.drawImage(bt.getImage(), bt.getX(), bt.getY());
                    if (e.getValue() != null) { // Special case for the "Jump to next planet button"
                        e.getValue().draw(gc, bt.getResource().toString() + " " + game.getResource(bt.getResource()));
                    }
                    if (bt.inCooldown()) {
                        gc.fillRect(bt.getX(), bt.getY(), ((now - bt.getCooldownStart()) / ((double)bt.getCooldownTime())) * bt.getImage().getWidth(), 5);
                    }
                }
            }
        };

        gameLoop.start();

        primaryStage.show();

        //TODO: Look into OGG later.
        if (!getParameters().getRaw().contains("-nomusic")) {
            MediaPlayer player = new MediaPlayer(AssetLoader.getMusic("main.mp3"));
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

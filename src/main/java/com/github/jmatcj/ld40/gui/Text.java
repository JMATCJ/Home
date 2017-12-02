package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.util.AssetLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text {
    private GraphicsContext gc;

    public Text(GraphicsContext gc, Color c, int fontSize) {
        this.gc = gc;
        gc.setFill(c);
        gc.setFont(Font.loadFont(AssetLoader.getFontLoc(), fontSize));
    }

    public void draw(String text, int x, int y) {
        gc.fillText(text, x, y);
    }
}

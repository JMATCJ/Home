package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.util.AssetLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text {
    private String str;
    private int x;
    private int y;

    public Text(GraphicsContext gc, String text, Color c, int fontSize, int x, int y) {
        this.str = text;
        this.x = x;
        this.y = y;
        gc.setFill(c);
        gc.setFont(Font.loadFont(AssetLoader.getFontLoc(), fontSize));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

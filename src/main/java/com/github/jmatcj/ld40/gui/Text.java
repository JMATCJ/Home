package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.util.AssetLoader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text {
    private GraphicsContext gc;
    private String str;
    private int x;
    private int y;
    private Color color;
    private int fontSize;

    public Text(String text, Color c, int fontSize, int x, int y) {
        this.str = text;
        this.x = x;
        this.y = y;
        this.color = c;
        this.fontSize = fontSize;
    }

    public void draw(GraphicsContext gc) {
        this.gc = gc;
        gc.setFill(color);
        gc.setFont(Font.loadFont(AssetLoader.getFontLoc(), fontSize));
        gc.fillText(str, x, y);

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

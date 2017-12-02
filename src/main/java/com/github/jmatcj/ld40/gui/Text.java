package com.github.jmatcj.ld40.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text {
    private GraphicsContext gc;


    public Text(GraphicsContext gc, Color c, String font, int fontSize) {
        this.gc = gc;
        gc.setFill(c);
        gc.setFont(Font.font(font, fontSize));
    }

    public void draw(String text, int x, int y) {
        gc.fillText(text, x, y);
    }
}

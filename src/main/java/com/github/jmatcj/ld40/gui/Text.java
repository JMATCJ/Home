package com.github.jmatcj.ld40.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text {
    private Color fillColor = Color.BLACK;
    private Font font = Font.font("Times New Roman");


    public Text(GraphicsContext gc) {
        gc.setFill(fillColor);
        gc.setFont(font);
    }

    public void draw(GraphicsContext gc, String text, int x, int y) {
        gc.fillText(text, x, y);
    }
}

package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.tick.Updatable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Button implements Updatable {
    private Image image;
    private int buttonX;
    private int buttonY;

    public Button(Image i, int x, int y) {
        image = i;
        buttonX = x;
        buttonY = y;
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return buttonX;
    }

    public int getY() {
        return buttonY;
    }

    public boolean click(MouseEvent e, Game g) {
        return false; // Does nothing by default
    }

    public void update(Game g, long ns) {} // Does nothing by default

    protected boolean inBounds(double x, double y) {
        return x >= this.buttonX && x <= this.buttonX + image.getWidth() && y >= this.buttonY && y <= this.buttonY + image.getHeight();
    }
}

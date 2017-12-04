package com.github.jmatcj.ld40.gui.button;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.gui.Drawable;
import com.github.jmatcj.ld40.tick.Updatable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Button implements Updatable, Drawable {
    protected Image image;
    protected int buttonX;
    protected int buttonY;

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

    @Override
    public void update(Game g, long ns) {} // Does nothing by default

    @Override
    public void draw(GraphicsContext gc, Game g) {
        gc.drawImage(image, buttonX, buttonY);
    }

    protected boolean inBounds(double x, double y) {
        return x >= this.buttonX && x <= this.buttonX + image.getWidth() && y >= this.buttonY && y <= this.buttonY + image.getHeight();
    }
}

package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.data.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Button {
    private Image image;
    private Resources resource;
    private int buttonX;
    private int buttonY;

    public Button(Resources r, Image i) {
        resource = r;
        image = i;
    }

    public Image getImage() {
        return image;
    }

    public Resources getResource() {
        return resource;
    }

    public int getX() {
        return buttonX;
    }

    public int getY() {
        return buttonY;
    }

    public void click() {
        System.out.println("Button was pressed, resource: " + resource.toString());
    }

    public void draw(GraphicsContext gc, int x, int y) {
        buttonX = x;
        buttonY = y;
        gc.drawImage(image, x, y);
    }
}

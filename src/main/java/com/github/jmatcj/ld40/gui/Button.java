package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Resources;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Button {
    private Image image;
    private Resources resource;
    private int buttonX;
    private int buttonY;
    private int resourceAmount;
    private long cooldown;
    private long timePassed = 0L;

    public Button(Resources r, Image i, long cd) {
        resource = r;
        image = i;
        resourceAmount = 1;
        cooldown = cd;
    }

    public void setResourceAmount(int i) {
        resourceAmount = i;
    }

    public int getResourceAmount() {
        return resourceAmount;
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

    public void update(long ns) {
        timePassed += ns;
    }

    public void click(Game g) {
        if (timePassed >= cooldown) {
            g.addResource(resource, resourceAmount);
            timePassed = 0L;
        }
    }

    public void draw(GraphicsContext gc, int x, int y) {
        buttonX = x;
        buttonY = y;
        gc.drawImage(image, x, y);
    }
}

package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Resources;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class Button {
    private Image image;
    private Resources resource;
    private int buttonX;
    private int buttonY;
    private int resourceAmount;
    private long cooldownTime;
    private long cooldownStart;
    private boolean inCooldown;

    public Button(Resources r, Image i, long cd, int x, int y) {
        resource = r;
        image = i;
        resourceAmount = 1;
        cooldownTime = cd;
        buttonX = x;
        buttonY = y;
        cooldownStart = -1;
        inCooldown = false;
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
        if (inCooldown) {
            if (cooldownStart != -1) {
                if (ns >= cooldownStart + cooldownTime) {
                    inCooldown = false;
                    cooldownStart = -1;
                }
            } else {
                cooldownStart = ns;
            }
        }
    }

    public void click(MouseEvent e, Game g) {
        if (!inCooldown && inBounds(e.getX(), e.getY())) {
            g.addResource(resource, resourceAmount);
            inCooldown = true;
        }
    }

    private boolean inBounds(double x, double y) {
        return x >= this.buttonX && x <= this.buttonX + image.getWidth() && y >= this.buttonY && y <= this.buttonY + image.getHeight();
    }
}

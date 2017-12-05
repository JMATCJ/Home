package com.github.jmatcj.ld40.gui.button;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ResourceButton extends Button {
    private int ordinal;
    private Resource resource;
    private Planet planet;
    private int resourceAmount;
    private int cooldownTime; // in seconds
    private long cooldownStart;
    private long currentNS;
    private boolean inCooldown;

    ResourceButton(int ordinal, Planet p, Resource r, Image i, int cooldownInSec, int x, int y) {
        super(i, x, y);
        planet = p;
        resource = r;
        resourceAmount = 1;
        cooldownTime = cooldownInSec;
        cooldownStart = -1;
        inCooldown = false;
        this.ordinal = ordinal;
    }

    public int ordinal() {
        return ordinal;
    }

    public Resource getResource() {
        return resource;
    }

    public Planet getPlanet() {
        return planet;
    }

    public long getCooldownStart() {
        return cooldownStart;
    }

    public long getCurrentNS() {
        return currentNS;
    }

    public boolean isInCooldown() {
        return inCooldown;
    }

    public void setCurrentNS(long currentNS) {
        this.currentNS = currentNS;
    }

    public void startCooldown(long startNS) {
        inCooldown = true;
        cooldownStart = startNS;
    }

    @Override
    public boolean click(MouseEvent e, Game g) {
        if (!inCooldown && inBounds(e.getX(), e.getY())) {
            if (!g.isNoCooldown()) {
                inCooldown = true;
            } else {
                g.addResource(resource, resourceAmount);
            }
            return true;
        }
        return false;
    }

    @Override
    public void update(Game g, long ns) {
        if (g.isNoCooldown()) {
            inCooldown = false;
        } else if (inCooldown) {
            currentNS = ns;
            if (cooldownStart != -1) {
                if (Util.hasTimeElapsed(cooldownStart, ns, cooldownTime)) {
                    inCooldown = false;
                    cooldownStart = -1;
                    g.addResource(resource, resourceAmount);
                }
            } else {
                cooldownStart = ns;
            }
        }
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        super.draw(gc, g); // Draws the actual button
        if (inCooldown) {
            gc.setFill(Color.BLACK);
            gc.fillRect(buttonX, buttonY, ((currentNS - cooldownStart) / ((double)Util.timeInNS(cooldownTime))) * image.getWidth(), 5); // Cooldown bar
        }
        Util.drawText(gc, Color.BLACK, 48, TextAlignment.RIGHT, resource.name() + " " + g.getResource(resource), 1270, Game.RES_Y_VALUES[ordinal % 4]); // Right-sidebar text
    }
}

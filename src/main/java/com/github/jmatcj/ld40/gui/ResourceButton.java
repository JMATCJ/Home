package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.util.AssetLoader;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class ResourceButton extends Button {
    private int ordinal;
    private Resource resource;
    private Planet planet;
    private int resourceAmount;
    private long cooldownTime;
    private long cooldownStart;
    private boolean inCooldown;

    ResourceButton(int ordinal, Planet p, Resource r, Image i, long cd, int x, int y) {
        super(i, x, y);
        planet = p;
        resource = r;
        resourceAmount = 1;
        cooldownTime = cd;
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

    public boolean inCooldown() {
        return inCooldown;
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public long getCooldownStart() {
        return cooldownStart;
    }

    public void startCooldown(long startNS) {
        inCooldown = true;
        cooldownStart = startNS;
    }

    @Override
    public boolean click(MouseEvent e, Game g) {
        if (!inCooldown && inBounds(e.getX(), e.getY())) {
            g.addResource(resource, resourceAmount);
            inCooldown = true;
            return true;
        }
        return false;
    }

    @Override
    public void update(Game g, long ns) {
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
}

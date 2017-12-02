package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resources;
import com.github.jmatcj.ld40.util.AssetLoader;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public enum Button {
    FOOD_ONE(Planet.XEONUS, Resources.FOOD, AssetLoader.getImage("button_food_one.png"), Util.timeInNS(2), 850, 500),
    STONE(Planet.XEONUS, Resources.STONE, AssetLoader.getImage("button_stone.png"), Util.timeInNS(5), 850, 300),
    IRON(Planet.XEONUS, Resources.IRON, AssetLoader.getImage("button_iron.png"), Util.timeInNS(10), 550, 300),
    CARBON(Planet.XEONUS, Resources.CARBON, AssetLoader.getImage("button_carbon.png"), Util.timeInNS(15), 550, 500),
    FOOD_TWO(Planet.DASKOTH, Resources.FOOD, AssetLoader.getImage("button_food_two.png"), Util.timeInNS(2), 850, 500),
    COPPER(Planet.DASKOTH, Resources.COPPER, AssetLoader.getImage("button_copper.png"), Util.timeInNS(5), 850, 300),
    SILICON(Planet.DASKOTH, Resources.SILICON, AssetLoader.getImage("button_silicon.png"), Util.timeInNS(10), 550, 300),
    TITANIUM(Planet.DASKOTH, Resources.TITANIUM, AssetLoader.getImage("button_titanium.png"), Util.timeInNS(15), 550, 500),
    FOOD_THREE(Planet.LEYMIA, Resources.FOOD, AssetLoader.getImage("button_food_three.png"), Util.timeInNS(2), 850, 500),
    HEXAPHESTRITE(Planet.LEYMIA, Resources.HEXAPHESTRITE, AssetLoader.getImage("button_hexaphestrite.png"), Util.timeInNS(5), 850, 300),
    ISOBELGOL(Planet.LEYMIA, Resources.ISOBELGOL, AssetLoader.getImage("button_isobelgol.png"), Util.timeInNS(10), 550, 300),
    SELDROLE(Planet.LEYMIA, Resources.SELDROLE, AssetLoader.getImage("button_seldrole.png"), Util.timeInNS(15), 550, 500);

    private Image image;
    private Resources resource;
    private Planet planet;
    private int buttonX;
    private int buttonY;
    private int resourceAmount;
    private long cooldownTime;
    private long cooldownStart;
    private boolean inCooldown;

    public static Button getButtonByResource(Planet planet, Resources resource) {
        for (Button b : values()) {
            if (b.getResource() == resource && b.getPlanet() == planet) {
                return b;
            }
        }
        return null;
    }

    Button(Planet p, Resources r, Image i, long cd, int x, int y) {
        planet = p;
        resource = r;
        image = i;
        resourceAmount = 1;
        cooldownTime = cd;
        buttonX = x;
        buttonY = y;
        cooldownStart = -1;
        inCooldown = false;
    }

    public Image getImage() {
        return image;
    }

    public Resources getResource() {
        return resource;
    }

    public Planet getPlanet() {
        return planet;
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

package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.util.AssetLoader;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public enum Button {
    FOOD_ONE(Planet.XEONUS, Resource.FOOD, AssetLoader.getImage("button_food_one.png"), Util.timeInNS(2), 850, 500),
    STONE(Planet.XEONUS, Resource.STONE, AssetLoader.getImage("button_stone.png"), Util.timeInNS(5), 900, 300),
    IRON(Planet.XEONUS, Resource.IRON, AssetLoader.getImage("button_iron.png"), Util.timeInNS(10), 800, 150),
    CARBON(Planet.XEONUS, Resource.CARBON, AssetLoader.getImage("button_carbon.png"), Util.timeInNS(15), 700, 50),
    FOOD_TWO(Planet.DASKOTH, Resource.FOOD, AssetLoader.getImage("button_food_two.png"), Util.timeInNS(2), 850, 500),
    COPPER(Planet.DASKOTH, Resource.COPPER, AssetLoader.getImage("button_copper.png"), Util.timeInNS(5), 850, 300),
    SILICON(Planet.DASKOTH, Resource.SILICON, AssetLoader.getImage("button_silicon.png"), Util.timeInNS(10), 550, 300),
    TITANIUM(Planet.DASKOTH, Resource.TITANIUM, AssetLoader.getImage("button_titanium.png"), Util.timeInNS(15), 550, 500),
    FOOD_THREE(Planet.LEYMIA, Resource.FOOD, AssetLoader.getImage("button_food_two.png"), Util.timeInNS(2), 850, 500),
    HEXAPHESTRITE(Planet.LEYMIA, Resource.HEXAPHESTRITE, AssetLoader.getImage("button_hexa.png"), Util.timeInNS(5), 850, 300),
    ISOBELGOL(Planet.LEYMIA, Resource.ISOBELGOL, AssetLoader.getImage("button_isol.png"), Util.timeInNS(10), 550, 300),
    SELDROLE(Planet.LEYMIA, Resource.SELDROLE, AssetLoader.getImage("button_seld.png"), Util.timeInNS(15), 550, 500),
    CONFIRM_JUMP(null, null, AssetLoader.getImage("test_button.png"), 0, 350, 0) {
        @Override
        public void click(MouseEvent e, Game g) {
            if (inBounds(e.getX(), e.getY())) {
                System.out.println("I was clicked!");
                g.nextPlanet();
            }
        }
    };

    private Image image;
    private Resource resource;
    private Planet planet;
    private int buttonX;
    private int buttonY;
    private int resourceAmount;
    private long cooldownTime;
    private long cooldownStart;
    private boolean inCooldown;

    public static Button getButtonByResource(Planet planet, Resource resource) {
        for (Button b : values()) {
            if (b.getResource() == resource && b.getPlanet() == planet) {
                return b;
            }
        }
        return null;
    }

    Button(Planet p, Resource r, Image i, long cd, int x, int y) {
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

    public Resource getResource() {
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

    public boolean inCooldown() {
        return inCooldown;
    }

    public long getCooldownTime() {
        return cooldownTime;
    }

    public long getCooldownStart() {
        return cooldownStart;
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

    protected boolean inBounds(double x, double y) {
        return x >= this.buttonX && x <= this.buttonX + image.getWidth() && y >= this.buttonY && y <= this.buttonY + image.getHeight();
    }
}

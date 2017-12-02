package com.github.jmatcj.ld40.data;

import com.github.jmatcj.ld40.util.AssetLoader;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.image.Image;

public enum Button {
    FOOD_ONE(Resources.FOOD, AssetLoader.getImage("button_food_one.png"), Util.timeInNS(5), 850, 500),
    STONE(Resources.STONE, AssetLoader.getImage("button_stone.png"), Util.timeInNS(10), 850, 300),
    IRON(Resources.IRON, AssetLoader.getImage("button_iron.png"), Util.timeInNS(15), 550, 300),
    CARBON(Resources.CARBON, AssetLoader.getImage("button_carbon.png"), Util.timeInNS(20), 550, 500),
    FOOD_TWO(Resources.FOOD, AssetLoader.getImage("button_food_two.png"), Util.timeInNS(5), 850, 500),
    COPPER(Resources.COPPER, AssetLoader.getImage("button_copper.png"), Util.timeInNS(10), 850, 300),
    SILICON(Resources.SILICON, AssetLoader.getImage("button_silicon.png"), Util.timeInNS(15), 550, 300),
    TITANIUM(Resources.TITANIUM, AssetLoader.getImage("button_titanium.png"), Util.timeInNS(20), 550, 500),
    FOOD_THREE(Resources.FOOD, AssetLoader.getImage("button_food_three.png"), Util.timeInNS(5), 850, 500),
    HEXAPHESTRITE(Resources.HEXAPHESTRITE, AssetLoader.getImage("button_hexaphestrite.png"), Util.timeInNS(10), 850, 300),
    ISOBELGOL(Resources.ISOBELGOL, AssetLoader.getImage("button_isobelgol.png"), Util.timeInNS(15), 550, 300),
    SELDROLE(Resources.SELDROLE, AssetLoader.getImage("button_seldrole.png"), Util.timeInNS(20), 550, 500);

    private Resources resource;
    private Image image;
    private long cooldown;
    private int buttonX;
    private int buttonY;

    Button(Resources r, Image i, long cd, int x, int y) {
        this.resource = r;
        this.image = i;
        this.cooldown = cd;
        this.buttonX = x;
        this.buttonY = y;
    }
}

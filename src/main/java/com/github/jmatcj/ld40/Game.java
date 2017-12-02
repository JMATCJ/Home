package com.github.jmatcj.ld40;

import static com.github.jmatcj.ld40.data.Planet.XEONUS;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resources;
import com.github.jmatcj.ld40.gui.Button;
import com.github.jmatcj.ld40.gui.Text;
import com.github.jmatcj.ld40.util.AssetLoader;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private static final int[] RES_Y_VALUES = {65, 105, 145, 185};

    private Long startNS;
    private Planet currentPlanet;
    private Map<Resources, Integer> collected;
    private Map<Button, Text> btnsToDisplay;

    public Game() {
        currentPlanet = XEONUS;
        collected = new EnumMap<>(Resources.class);
        for (Resources r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
        btnsToDisplay = new HashMap<>();
        btnsToDisplay.put(Button.FOOD_ONE, new Text(Color.BLACK, 48, 1050, 65));
    }

    public void addResource(Resources resources, int amount) {
        if (collected.containsKey(resources)) {
            int cur = collected.get(resources);
            collected.put(resources, cur + amount);
        }
        if (collected.get(resources) == currentPlanet.getMoveOnAmountFor(resources)) {
            //Button bt = new Button(Resources.STONE, AssetLoader.getImage("button_stone.png"), Util.timeInNS(10), 850, 300);
            Button cur = Button.getButtonByResource(currentPlanet, resources);
            Button next = Button.values()[cur.ordinal() + 1];
            btnsToDisplay.put(next, new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[next.ordinal() % 4]));
        }
    }

    public int getResource(Resources r) {
        return collected.get(r);
    }

    public Map<Button, Text> getButtonsOnDisplay() {
        return btnsToDisplay;
    }

    public void onClick(MouseEvent e) {
        for (Button b : btnsToDisplay.keySet()) {
            b.click(e, this);
        }
    }

    public void update(long ns) {
        if (startNS == null) {
            startNS = ns;
        }
        for (Button b : btnsToDisplay.keySet()) {
            b.update(ns);
        }
    }
}

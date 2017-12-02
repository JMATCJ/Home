package com.github.jmatcj.ld40;

import static com.github.jmatcj.ld40.data.Planet.*;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resources;
import com.github.jmatcj.ld40.gui.Button;
import com.github.jmatcj.ld40.util.AssetLoader;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.input.MouseEvent;

public class Game {
    private Long startNS;
    private Planet currentPlanet;
    private Map<Resources, Integer> collected;
    private Set<Button> btnsToDisplay;

    public Game() {
        currentPlanet = XEONUS;
        collected = new EnumMap<>(Resources.class);
        for (Resources r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
        btnsToDisplay = new HashSet<>();
        btnsToDisplay.add(new Button(Resources.FOOD, AssetLoader.getImage("button_food_one.png"), 5000000000L, 850, 500));
    }

    public void addResource(Resources resources, int amount) {
        if (collected.containsKey(resources)) {
            int cur = collected.get(resources);
            collected.put(resources, cur + amount);
        }
    }

    public int getResource(Resources r) {
        return collected.get(r);
    }

    public Set<Button> getButtonsOnDisplay() {
        return btnsToDisplay;
    }

    public void onClick(MouseEvent e) {
        for (Button b : btnsToDisplay) {
            b.click(e, this);
        }
    }

    public void update(long ns) {
        if (startNS == null) {
            startNS = ns;
        }
        for (Button b : btnsToDisplay) {
            b.update(ns);
        }
    }
}

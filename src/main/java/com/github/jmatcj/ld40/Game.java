package com.github.jmatcj.ld40;

import static com.github.jmatcj.ld40.data.Resources.*;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resources;
import java.util.EnumMap;
import java.util.Map;

public class Game {
    private Planet currentPlanet;
    private Map<Resources, Integer> collected;

    public Game() {
        currentPlanet = new Planet("Xeonus", FOOD, STONE, IRON, CARBON);
        collected = new EnumMap<>(Resources.class);
        for (Resources r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
    }

    public void addResource(Resources resources, int amount) {
        if (collected.containsKey(resources)) {
            int cur = collected.get(resources);
            collected.put(resources, cur + amount);
        }
    }
}

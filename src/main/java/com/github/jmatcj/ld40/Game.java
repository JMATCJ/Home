package com.github.jmatcj.ld40;

import static com.github.jmatcj.ld40.data.Planet.XEONUS;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.gui.Button;
import com.github.jmatcj.ld40.gui.Text;
import com.github.jmatcj.ld40.util.Util;
import java.util.EnumMap;
import java.util.Map;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Game {
	//comment
    private static final int[] RES_Y_VALUES = {65, 105, 145, 185};

    private Long startNS;
    private Planet currentPlanet;
    private Map<Resource, Integer> collected;
    private Map<Button, Text> btnsToDisplay;
    int time = 0;
    boolean purchasedHarvester = false;

    public Game() {
        currentPlanet = XEONUS;
        collected = new EnumMap<>(Resource.class);
        for (Resource r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
        btnsToDisplay = new EnumMap<>(Button.class);
        btnsToDisplay.put(Button.FOOD_ONE, new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[0]));
    }

    public void addResource(Resource resource, int amount) {
        if (collected.containsKey(resource)) {
            int cur = collected.get(resource);
            collected.put(resource, cur + amount);
        }
        if (collected.get(resource) == currentPlanet.getMoveOnAmountFor(resource)) {
            Button cur = Button.getButtonByResource(currentPlanet, resource);
            Button next = Button.values()[cur.ordinal() + 1];
            if (next.getPlanet() == currentPlanet) {
                btnsToDisplay.put(next, new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[next.ordinal() % 4]));
            } else {
                btnsToDisplay.put(Button.CONFIRM_JUMP, null);
            }
        }
    }

    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    public void nextPlanet() {
        currentPlanet = Planet.values()[currentPlanet.ordinal() + 1];
        collected.clear();
        for (Resource r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
        btnsToDisplay.clear();
        btnsToDisplay.put(Button.getButtonByResource(currentPlanet, Resource.FOOD), new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[0]));
    }

    public int getResource(Resource r) {
        return collected.get(r);
    }

    public Map<Button, Text> getButtonsOnDisplay() {
        return btnsToDisplay;
    }

    public void toggleHarvester() {
        purchasedHarvester = true;
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
        if (btnsToDisplay.size() > 1) { // They've gotten a new resource
        	time++;
            if (time % 1800 == 0) { //30 * 60 for 30 seconds and it gets called 60 times a second
            	if (!(collected.get(Resource.FOOD) < 1)) {
            		addResource(Resource.FOOD, -1);
            	}
            }
        }

        if (collected.get(Resource.FOOD) >= 50) {
            btnsToDisplay.put(Button.HARVESTER, null);
        }

        if (purchasedHarvester) {
        	if (time % 120 == 0) {
        		addResource(Resource.FOOD, +1);
        	}
        }
    }
}

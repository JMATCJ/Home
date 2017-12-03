package com.github.jmatcj.ld40;

import static com.github.jmatcj.ld40.data.Planet.XEONUS;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.data.Upgrade;
import com.github.jmatcj.ld40.gui.Button;
import com.github.jmatcj.ld40.gui.Buttons;
import com.github.jmatcj.ld40.gui.ResourceButton;
import com.github.jmatcj.ld40.gui.Text;
import com.github.jmatcj.ld40.tick.FoodEater;
import com.github.jmatcj.ld40.tick.Updatable;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Game {
    private static final int[] RES_Y_VALUES = {65, 105, 145, 185};

    private Long startNS;
    private Planet currentPlanet;
    private Set<Updatable> updateListeners;
    private Map<Resource, Integer> collected;
    private Map<Button, Text> btnsToDisplay;
    private Set<Upgrade> upgradesPurchased;

    public Game() {
        currentPlanet = XEONUS;
        collected = new EnumMap<>(Resource.class);
        for (Resource r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
        upgradesPurchased = EnumSet.noneOf(Upgrade.class);
        updateListeners = new HashSet<>();
        addUpdateListener(new FoodEater());
        btnsToDisplay = new HashMap<>();
        addButton(Buttons.FOOD_ONE, new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[0]));
    }

    public void addResource(Resource resource, int amount) {
        if (collected.containsKey(resource)) {
            int cur = collected.get(resource);
            collected.put(resource, cur + amount);
        }
        if (collected.get(resource) == currentPlanet.getMoveOnAmountFor(resource)) {
            ResourceButton cur = Buttons.getResourceButton(currentPlanet, resource);
            ResourceButton next = Buttons.RESOURCE_BUTTONS[cur.ordinal() + 1];
            if (next.getPlanet() == currentPlanet) {
                addButton(next, new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[next.ordinal() % 4]));
            } else {
                addButton(Buttons.CONFIRM_JUMP, null);
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
        btnsToDisplay.forEach((button, text) -> removeButton(button));
        addButton(Buttons.getResourceButton(currentPlanet, Resource.FOOD), new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[0]));
    }

    public int getResource(Resource r) {
        return collected.get(r);
    }

    public Map<Button, Text> getButtonsOnDisplay() {
        return btnsToDisplay;
    }

    public void addButton(Button b, Text t) {
        btnsToDisplay.put(b, t);
        updateListeners.add(b);
    }

    public void removeButton(Button b) {
        btnsToDisplay.remove(b);
        updateListeners.remove(b);
    }

    public void onClick(MouseEvent e) {
        for (Button b : btnsToDisplay.keySet()) {
            if (b.click(e, this)) {
                break;
            }
        }
    }

    public void applyUpgrade(Upgrade upgrade) {
        upgradesPurchased.add(upgrade);
        updateListeners.add(upgrade);
    }

    public boolean addUpdateListener(Updatable updatable) {
        return updateListeners.add(updatable);
    }

    public boolean removeUpdateListener(Updatable updatable) {
        return updateListeners.remove(updatable);
    }

    public void update(long ns) {
        if (startNS == null) {
            startNS = ns;
        }

        updateListeners.forEach(u -> u.update(this, ns));

        for (Upgrade u : Upgrade.values()) {
            if (u.canUnlock(this)) {
                addButton(Buttons.UPGRADE_BUTTONS.get(u), null);
            }
        }
    }
}

package com.github.jmatcj.ld40;

import static com.github.jmatcj.ld40.data.Planet.XEONUS;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.data.Upgrade;
import com.github.jmatcj.ld40.gui.Button;
import com.github.jmatcj.ld40.gui.Buttons;
import com.github.jmatcj.ld40.gui.ResourceButton;
import com.github.jmatcj.ld40.gui.Text;
import com.github.jmatcj.ld40.gui.UpgradeButton;
import com.github.jmatcj.ld40.tick.FoodEater;
import com.github.jmatcj.ld40.tick.Updatable;
import java.util.ConcurrentModificationException;
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

    private boolean noCooldown;
    private Long startNS;
    private Planet currentPlanet;
    private Set<Updatable> updateListeners;
    private Map<Resource, Integer> collected;
    private Map<Button, Text> btnsToDisplay;

    public Game() {
        noCooldown = false;
        currentPlanet = XEONUS;
        collected = new EnumMap<>(Resource.class);
        for (Resource r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
        updateListeners = new HashSet<>();
        addUpdateListener(new FoodEater());
        btnsToDisplay = new HashMap<>();
        addButton(Buttons.FOOD_ONE, new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[0]));
    }

    public boolean isNoCooldown() {
        return noCooldown;
    }

    public void setNoCooldown(boolean noCooldown) {
        this.noCooldown = noCooldown;
        if (noCooldown) {
            System.out.println("-nocooldown specified. All cooldowns will be ignored.");
        }
    }

    public void addResource(Resource resource, int amount) {
        if (collected.containsKey(resource)) {
            int cur = collected.get(resource);
            collected.put(resource, cur + amount);
        }
        if (collected.get(resource) == currentPlanet.getMoveOnAmountFor(resource)) {
            ResourceButton cur = Buttons.getResourceButton(currentPlanet, resource);
            int nextIndex = cur.ordinal() + 1;
            if (nextIndex < Buttons.RESOURCE_BUTTONS.length) {
                ResourceButton next = Buttons.RESOURCE_BUTTONS[nextIndex];
                if (next.getPlanet() == currentPlanet) {
                    addButton(next, new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[next.ordinal() % 4]));
                } else {
                    if (next.getPlanet() == Planet.DASKOTH) {
                        addButton(Buttons.CONFIRM_JUMP_ONE, null);
                    } else if (next.getPlanet() == Planet.LEYMIA) {
                        addButton(Buttons.CONFIRM_JUMP_TWO, null);
                    } else {
                        addButton(Buttons.CONFIRM_JUMP_THREE, null);
                    }
                }
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
        try { btnsToDisplay.forEach((button, text) -> removeButton(button)); } catch (ConcurrentModificationException cme) {} // Throws CME, but works if suppressed.
        addButton(Buttons.getResourceButton(currentPlanet, Resource.FOOD), new Text(Color.BLACK, 48, 1050, RES_Y_VALUES[0]));
    }

    public int getResource(Resource r) {
        return collected.getOrDefault(r, -1);
    }

    public Map<Button, Text> getButtonsOnDisplay() {
        return btnsToDisplay;
    }

    public void addButton(Button b, Text t) {
        btnsToDisplay.put(b, t);
        addUpdateListener(b);
    }

    public void removeButton(Button b) {
        btnsToDisplay.remove(b);
        removeUpdateListener(b);
    }

    public void onClick(MouseEvent e) {
        for (Button b : btnsToDisplay.keySet()) {
            if (b.click(e, this)) {
                break;
            }
        }
    }

    public void applyUpgrade(Upgrade upgrade) {
        if (upgrade.ordinal() > 0) {
            Upgrade prevUp = Upgrade.values()[upgrade.ordinal() - 1];
            if (prevUp.getResThatUpgradeIsFor() == upgrade.getResThatUpgradeIsFor()) {
                removeUpdateListener(prevUp);
            }
        }
        addUpdateListener(upgrade);
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
                UpgradeButton bt = Buttons.UPGRADE_BUTTONS.get(u);
                if (!bt.hasBeenPurchased()) {
                    addButton(bt, null);
                }
            }
        }
    }
}

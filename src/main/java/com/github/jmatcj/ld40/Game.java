package com.github.jmatcj.ld40;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.data.Upgrade;
import com.github.jmatcj.ld40.gui.Drawable;
import com.github.jmatcj.ld40.gui.PlanetText;
import com.github.jmatcj.ld40.gui.button.Button;
import com.github.jmatcj.ld40.gui.button.Buttons;
import com.github.jmatcj.ld40.gui.button.ResourceButton;
import com.github.jmatcj.ld40.gui.button.UpgradeButton;
import com.github.jmatcj.ld40.tick.FoodEater;
import com.github.jmatcj.ld40.tick.Updatable;
import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class Game {
    public static final int[] RES_Y_VALUES = {40, 80, 120, 160};

    private boolean noCooldown;
    private Long startNS;
    private Planet currentPlanet;
    private Set<Updatable> updateListeners;
    private Set<Drawable> drawables;
    private Map<Resource, Integer> collected;

    public Game() {
        noCooldown = false;
        currentPlanet = Planet.XEONUS;
        collected = new EnumMap<>(Resource.class);
        for (Resource r : currentPlanet.getResources()) {
            collected.put(r, 0);
        }
        updateListeners = new HashSet<>();
        drawables = new HashSet<>();
        FoodEater fe = new FoodEater();
        addUpdateListener(fe);
        addDrawListener(fe);
        addDrawListener(new PlanetText());
        addButton(Buttons.FOOD_ONE);
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
                    addButton(next);
                } else {
                    if (next.getPlanet() == Planet.DASKOTH) {
                        addButton(Buttons.CONFIRM_JUMP_ONE);
                    } else if (next.getPlanet() == Planet.LEYMIA) {
                        addButton(Buttons.CONFIRM_JUMP_TWO);
                    }
                }
            } else {
                addButton(Buttons.CONFIRM_JUMP_THREE);
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
        try { drawables.stream().filter(d -> d instanceof Button).forEach(d -> removeButton((Button)d)); } catch (ConcurrentModificationException cme) {} // Throws CME, but works if suppressed.
        addButton(Buttons.getResourceButton(currentPlanet, Resource.FOOD));
    }

    public int getResource(Resource r) {
        return collected.getOrDefault(r, -1);
    }

    public long getNumOfResourcesOnDisplay() {
        return drawables.stream().filter(d -> d instanceof Button).count();
    }

    public void addButton(Button b) {
        addUpdateListener(b);
        addDrawListener(b);
    }

    public void removeButton(Button b) {
        removeUpdateListener(b);
        removeDrawListener(b);
    }

    public void onClick(MouseEvent e) {
        for (Drawable d : drawables) {
            if (d instanceof Button && ((Button)d).click(e, this)) {
                return;
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

    void update(long ns) {
        if (startNS == null) {
            startNS = ns;
        }

        updateListeners.forEach(u -> u.update(this, ns));

        for (Upgrade u : Upgrade.values()) {
            if (u.canUnlock(this)) {
                UpgradeButton bt = Buttons.UPGRADE_BUTTONS.get(u);
                if (!bt.hasBeenPurchased()) {
                    addButton(bt);
                }
            }
        }
    }

    public boolean addDrawListener(Drawable drawable) {
        return drawables.add(drawable);
    }

    public boolean removeDrawListener(Drawable drawable) {
        return drawables.remove(drawable);
    }

    void draw(GraphicsContext gc) {
        drawables.forEach(d -> d.draw(gc, this));
    }
}

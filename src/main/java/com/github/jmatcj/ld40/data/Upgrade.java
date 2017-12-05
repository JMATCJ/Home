package com.github.jmatcj.ld40.data;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.gui.button.Buttons;
import com.github.jmatcj.ld40.tick.Updatable;
import com.github.jmatcj.ld40.util.Util;
import java.util.EnumMap;
import java.util.Map;

public enum Upgrade implements Updatable {
    HARVESTERFOOD1(3, 1, Resource.FOOD, Resource.FOOD, 50, Resource.STONE, 20),
    HARVESTERFOOD2(2, 1, Resource.FOOD, Resource.FOOD, 125, Resource.STONE, 35),
    QUARRY1(5, 1, Resource.STONE, Resource.IRON, 30, Resource.STONE, 100),
    QUARRY2(3, 1, Resource.STONE, Resource.IRON, 75, Resource.STONE, 250),
    QUARRY3(10, 1, Resource.IRON, Resource.CARBON, 30, Resource.IRON, 100),
    QUARRY4(5, 1, Resource.IRON, Resource.CARBON, 75, Resource.IRON, 250),
    QUARRY5(20, 1, Resource.CARBON, Resource.IRON, 150),
    QUARRY6(10, 1, Resource.CARBON, Resource.IRON, 350);

    private int secBetweenCycles;
    private int numToAdd;
    private Resource rToAdd;
    private long startNS;
    private Map<Resource, Integer> requirements;

    Upgrade(int secBetweenCycles, int numToAdd, Resource rToAdd, Resource r1, Integer amt1) {
        this(secBetweenCycles, numToAdd, rToAdd, r1, amt1, null, null, null, null, null, null);
    }

    Upgrade(int secBetweenCycles, int numToAdd, Resource rToAdd, Resource r1, Integer amt1, Resource r2, Integer amt2) {
        this(secBetweenCycles, numToAdd, rToAdd, r1, amt1, r2, amt2, null, null, null, null);
    }

    Upgrade(int secBetweenCycles, int numToAdd, Resource rToAdd, Resource r1, Integer amt1, Resource r2, Integer amt2, Resource r3, Integer amt3) {
        this(secBetweenCycles, numToAdd, rToAdd, r1, amt1, r2, amt2, r3, amt3, null, null);
    }

    Upgrade(int secBetweenCycles, int numToAdd, Resource rToAdd, Resource r1, Integer amt1, Resource r2, Integer amt2, Resource r3, Integer amt3, Resource r4, Integer amt4) {
        this.secBetweenCycles = secBetweenCycles;
        this.numToAdd = numToAdd;
        this.rToAdd = rToAdd;
        this.startNS = 0L;
        requirements = new EnumMap<>(Resource.class);
        requirements.put(r1, amt1);
        if (r2 != null) {
            requirements.put(r2, amt2);
            if (r3 != null) {
                requirements.put(r3, amt3);
                if (r4 != null) {
                    requirements.put(r4, amt4);
                }
            }
        }
    }

    public Resource getResThatUpgradeIsFor() {
        return rToAdd;
    }

    public boolean canUnlock(Game g) {
        for (Map.Entry<Resource, Integer> e : requirements.entrySet()) {
            if (g.getResource(e.getKey()) < e.getValue()) {
                return false;
            }
        }
        return true;
    }

    // It is assumed when calling this method that the upgrade can be applied
    public void removeResources(Game g) {
        for (Map.Entry<Resource, Integer> e : requirements.entrySet()) {
            g.addResource(e.getKey(), -e.getValue());
        }
    }

    @Override
    public void update(Game g, long ns) {
        if (startNS == 0) {
            startNS = ns;
        } else if (Util.hasTimeElapsed(startNS, ns, secBetweenCycles)) {
            Buttons.getResourceButton(g.getCurrentPlanet(), rToAdd).startCooldown(ns);
            startNS = ns;
        }
    }
}

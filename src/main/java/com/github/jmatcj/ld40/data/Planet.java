package com.github.jmatcj.ld40.data;

import java.util.EnumMap;
import java.util.Map;

import static com.github.jmatcj.ld40.data.Resources.*;

public enum Planet {
    XEONUS("Xeonus", FOOD, 20, STONE, 50, IRON, 100, CARBON, 200),
    DASKOTH("Daskoth", FOOD, 10, COPPER, 150, SILICON, 250, TITANIUM, 400),
    LEYMIA("Leymia", FOOD, 10, HEXAPHESTRITE, 350, ISOBELGOL, 550, SELDROLE, 800);

    private String name;
    // The list is ordered
    private Resources[] resources;
    // The amount needed of a specified resource to move on to the next point
    private Map<Resources, Integer> moveOnAmount;

    // Resources should always be 4, so that's why I'm going array
    Planet(String name, Resources r1, int moa1, Resources r2, int moa2, Resources r3, int moa3, Resources r4, int moa4) {
        this.name = name;
        this.resources = new Resources[]{r1, r2, r3, r4};
        this.moveOnAmount = new EnumMap<>(Resources.class);
        this.moveOnAmount.put(r1, moa1);
        this.moveOnAmount.put(r2, moa2);
        this.moveOnAmount.put(r3, moa3);
        this.moveOnAmount.put(r4, moa4);
    }

    public String getName() {
        return name;
    }

    public Resources[] getResources() {
        return resources;
    }

    public int getMoveOnAmountFor(Resources resource) {
        return moveOnAmount.getOrDefault(resource, -1);
    }
}

package com.github.jmatcj.ld40.data;

import static com.github.jmatcj.ld40.data.Resource.*;

import java.util.EnumMap;
import java.util.Map;

public enum Planet {
    XEONUS("Xeonus", FOOD, 20, STONE, 50, IRON, 100, CARBON, 200),
    DASKOTH("Daskoth", FOOD, 10, COPPER, 150, SILICON, 250, TITANIUM, 400),
    LEYMIA("Leymia", FOOD, 10, HEXAPHESTRITE, 350, ISOBELGOL, 550, SELDROLE, 800);

    private String name;
    // The list is ordered
    private Resource[] resources;
    // The amount needed of a specified resource to move on to the next point
    private Map<Resource, Integer> moveOnAmount;

    // Resource should always be 4, so that's why I'm going array
    Planet(String name, Resource r1, int moa1, Resource r2, int moa2, Resource r3, int moa3, Resource r4, int moa4) {
        this.name = name;
        this.resources = new Resource[]{r1, r2, r3, r4};
        this.moveOnAmount = new EnumMap<>(Resource.class);
        this.moveOnAmount.put(r1, moa1);
        this.moveOnAmount.put(r2, moa2);
        this.moveOnAmount.put(r3, moa3);
        this.moveOnAmount.put(r4, moa4);
    }

    public String getName() {
        return name;
    }

    public Resource[] getResources() {
        return resources;
    }

    public int getMoveOnAmountFor(Resource resource) {
        return moveOnAmount.getOrDefault(resource, -1);
    }

    public static void debug(boolean isDebug) {
        if (isDebug) {
            System.out.println("\"-debug\" specified. Setting all moveOnAmounts to 1...");
            for (Planet p : values()) {
                for (Resource r : p.resources) {
                    p.moveOnAmount.put(r, 1);
                }
            }
        }
    }
}

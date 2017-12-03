package com.github.jmatcj.ld40.data;

import com.github.jmatcj.ld40.Game;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public enum Upgrade {
    HARVESTERFOOD1(Resource.FOOD, 5, Resource.STONE, 2),
    HARVESTERFOOD2(Resource.FOOD, 150, Resource.STONE, 50);

    private Map<Resource, Integer> requirements;

    Upgrade(Resource r1, Integer amt1) {
        this(r1, amt1, null, null, null, null, null, null);
    }

    Upgrade(Resource r1, Integer amt1, Resource r2, Integer amt2) {
        this(r1, amt1, r2, amt2, null, null, null, null);
    }

    Upgrade(Resource r1, Integer amt1, Resource r2, Integer amt2, Resource r3, Integer amt3) {
        this(r1, amt1, r2, amt2, r3, amt3, null, null);
    }

    Upgrade(Resource r1, Integer amt1, Resource r2, Integer amt2, Resource r3, Integer amt3, Resource r4, Integer amt4) {
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

    public Set<Map.Entry<Resource, Integer>> getRequirements() {
        return requirements.entrySet();
    }
}

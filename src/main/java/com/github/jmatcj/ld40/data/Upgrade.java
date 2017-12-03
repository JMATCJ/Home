package com.github.jmatcj.ld40.data;

public enum Upgrade {
    HARVESTERFOOD(Resource.FOOD, 50);

    private Resource resNeeded;
    private int amtNeeded;

    Upgrade(Resource resNeeded, int amtNeeded) {
        this.resNeeded = resNeeded;
        this.amtNeeded = amtNeeded;
    }

    public boolean canUnlock(Resource r, int amtOnHand) {
        return r == resNeeded && amtOnHand >= amtNeeded;
    }

    public Resource getResourceNeeded() {
        return resNeeded;
    }

    public int getAmountNeeded() {
        return amtNeeded;
    }
}

package com.github.jmatcj.ld40.data;

public class Planet {
    private String name;
    // The list is ordered
    private Resources[] resources;

    // Resources should always be 4, so that's why I'm going array
    public Planet(String name, Resources... resources) {
        this.name = name;
        this.resources = resources;
    }

    public String getName() {
        return name;
    }

    public Resources[] getResources() {
        return resources;
    }
}

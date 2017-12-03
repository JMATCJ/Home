package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.data.Upgrade;
import com.github.jmatcj.ld40.util.AssetLoader;
import com.github.jmatcj.ld40.util.Util;
import java.util.EnumMap;
import java.util.Map;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public final class Buttons {
    public static final ResourceButton FOOD_ONE = new ResourceButton(0, Planet.XEONUS, Resource.FOOD, AssetLoader.getImage("button_food_one.png"), Util.timeInNS(2), 850, 500);
    public static final ResourceButton STONE = new ResourceButton(1, Planet.XEONUS, Resource.STONE, AssetLoader.getImage("button_stone.png"), Util.timeInNS(5), 900, 300);
    public static final ResourceButton IRON = new ResourceButton(2, Planet.XEONUS, Resource.IRON, AssetLoader.getImage("button_iron.png"), Util.timeInNS(10), 800, 150);
    public static final ResourceButton CARBON = new ResourceButton(3, Planet.XEONUS, Resource.CARBON, AssetLoader.getImage("button_carbon.png"), Util.timeInNS(15), 700, 50);
    public static final ResourceButton FOOD_TWO = new ResourceButton(4, Planet.DASKOTH, Resource.FOOD, AssetLoader.getImage("button_food_two.png"), Util.timeInNS(2), 525, 400);
    public static final ResourceButton COPPER = new ResourceButton(5, Planet.DASKOTH, Resource.COPPER, AssetLoader.getImage("button_copper.png"), Util.timeInNS(5), 800, 200);
    public static final ResourceButton SILICON = new ResourceButton(6, Planet.DASKOTH, Resource.SILICON, AssetLoader.getImage("button_silicon.png"), Util.timeInNS(10), 400, 175);
    public static final ResourceButton TITANIUM = new ResourceButton(7, Planet.DASKOTH, Resource.TITANIUM, AssetLoader.getImage("button_titanium.png"), Util.timeInNS(15), 150, 450);
    public static final ResourceButton FOOD_THREE = new ResourceButton(8, Planet.LEYMIA, Resource.FOOD, AssetLoader.getImage("button_food_two.png"), Util.timeInNS(2), 850, 325);
    public static final ResourceButton HEXAPHESTRITE = new ResourceButton(9, Planet.LEYMIA, Resource.HEXAPHESTRITE, AssetLoader.getImage("button_hexa.png"), Util.timeInNS(5), 850, 300);
    public static final ResourceButton ISOBELGOL = new ResourceButton(10, Planet.LEYMIA, Resource.ISOBELGOL, AssetLoader.getImage("button_isol.png"), Util.timeInNS(10), 550, 300);
    public static final ResourceButton SELDROLE = new ResourceButton(11, Planet.LEYMIA, Resource.SELDROLE, AssetLoader.getImage("button_seld.png"), Util.timeInNS(15), 550, 500);

    public static final ResourceButton[] RESOURCE_BUTTONS = {FOOD_ONE, STONE, IRON, CARBON, FOOD_TWO, COPPER, SILICON, TITANIUM, FOOD_THREE, HEXAPHESTRITE, ISOBELGOL, SELDROLE};

    public static final UpgradeButton HARVESTERFOOD1 = new UpgradeButton(Upgrade.HARVESTERFOOD1, AssetLoader.getImage("test_button.png"), 50, 100);
    public static final UpgradeButton HARVESTERFOOD2 = new UpgradeButton(Upgrade.HARVESTERFOOD2, AssetLoader.getImage("button_isol.png"), 50, 50);
    public static final UpgradeButton QUARRY1 = new UpgradeButton(Upgrade.QUARRY1, AssetLoader.getImage("test_button.png"), 50, 50);
    public static final UpgradeButton QUARRY2 = new UpgradeButton(Upgrade.QUARRY2, AssetLoader.getImage("test_button.png"), 50, 50);
    public static final UpgradeButton QUARRY3 = new UpgradeButton(Upgrade.QUARRY3, AssetLoader.getImage("test_button.png"), 50, 50);
    public static final UpgradeButton QUARRY4 = new UpgradeButton(Upgrade.QUARRY4, AssetLoader.getImage("test_button.png"), 50, 50);
    public static final UpgradeButton QUARRY5 = new UpgradeButton(Upgrade.QUARRY5, AssetLoader.getImage("test_button.png"), 50, 50);
    public static final UpgradeButton QUARRY6 = new UpgradeButton(Upgrade.QUARRY6, AssetLoader.getImage("test_button.png"), 50, 50);

    public static final Map<Upgrade, Button> UPGRADE_BUTTONS = new EnumMap<>(Upgrade.class);
    static {
        UPGRADE_BUTTONS.put(Upgrade.HARVESTERFOOD1, HARVESTERFOOD1);
        UPGRADE_BUTTONS.put(Upgrade.HARVESTERFOOD2, HARVESTERFOOD2);
        UPGRADE_BUTTONS.put(Upgrade.QUARRY1, QUARRY1);
        UPGRADE_BUTTONS.put(Upgrade.QUARRY2, QUARRY2);
        UPGRADE_BUTTONS.put(Upgrade.QUARRY3, QUARRY3);
        UPGRADE_BUTTONS.put(Upgrade.QUARRY4, QUARRY4);
        UPGRADE_BUTTONS.put(Upgrade.QUARRY5, QUARRY5);
        UPGRADE_BUTTONS.put(Upgrade.QUARRY6, QUARRY6);
    }

    public static final Button CONFIRM_JUMP_ONE = new Button(AssetLoader.getImage("button_planet_two.png"), 0, 40) {
        @Override
        public boolean click(MouseEvent e, Game g) {
            if (inBounds(e.getX(), e.getY())) {
                g.nextPlanet();
                return true;
            }
            return false;
        }
    };

    public static final Button CONFIRM_JUMP_TWO = new Button(AssetLoader.getImage("button_planet_three.png"), 0, 40) {
        @Override
        public boolean click(MouseEvent e, Game g) {
            if (inBounds(e.getX(), e.getY())) {
                g.nextPlanet();
                return true;
            }
            return false;
        }
    };

    public static final Button CONFIRM_JUMP_THREE = new Button(AssetLoader.getImage("test_button.png"), 0, 40) {
        @Override
        public boolean click(MouseEvent e, Game g) {
            if (inBounds(e.getX(), e.getY())) {
                g.nextPlanet();
                return true;
            }
            return false;
        }
    };

    /*public static final ResourceButton HARVESTER = new ResourceButton(null, null, AssetLoader.getImage("test_button.png"), 0, 50, 50) {
        @Override
        public void click(MouseEvent e, Game g) {
            if (inBounds(e.getX(), e.getY())) {
                if (g.getResource(Resource.FOOD) >= 50) {
                    g.addResource(Resource.FOOD, -50);
                    g.toggleHarvester();
                    g.getButtonsOnDisplay().remove(Button.HARVESTER);
                }
            }
        }
    };*/

    public static ResourceButton getResourceButton(Planet planet, Resource resource) {
        for (ResourceButton b : RESOURCE_BUTTONS) {
            if (b.getResource() == resource && b.getPlanet() == planet) {
                return b;
            }
        }
        return null;
    }
}

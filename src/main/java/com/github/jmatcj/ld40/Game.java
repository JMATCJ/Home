package com.github.jmatcj.ld40;

import com.github.jmatcj.ld40.data.Planet;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.data.Upgrade;
import com.github.jmatcj.ld40.gui.Drawable;
import com.github.jmatcj.ld40.gui.FinalText;
import com.github.jmatcj.ld40.gui.PlanetText;
import com.github.jmatcj.ld40.gui.button.Button;
import com.github.jmatcj.ld40.gui.button.Buttons;
import com.github.jmatcj.ld40.gui.button.ResourceButton;
import com.github.jmatcj.ld40.gui.button.UpgradeButton;
import com.github.jmatcj.ld40.tick.FoodEater;
import com.github.jmatcj.ld40.tick.Updatable;
import com.github.jmatcj.ld40.util.OS;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;

public class Game {
    public static final int[] RES_Y_VALUES = {40, 80, 120, 160};

    private boolean noCooldown;
    private boolean noMedia;
    private Path dataDir;
    private Planet currentPlanet;
    private Set<Updatable> updateListeners;
    private Set<Drawable> drawables;
    private Map<Resource, Integer> collected;
    private MediaPlayer player;
    private boolean readyToJump;

    public Game(boolean newGame, boolean noMedia) {
        noCooldown = false;
        this.noMedia = noMedia;
        dataDir = OS.getDataDir();
        try {
            Files.createDirectories(dataDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateListeners = new HashSet<>();
        drawables = new HashSet<>();
        try {
            if (newGame || !loadData()) {
                newGame();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            newGame();
        }
        addDrawListener(new PlanetText());
        readyToJump = false;
    }

    void newGame() {
        updateListeners.clear();
        drawables.clear();
        currentPlanet = Planet.XEONUS;
        collected = new EnumMap<>(Resource.class);
        Arrays.stream(currentPlanet.getResources()).forEach(r -> collected.put(r, 0));
        FoodEater fe = new FoodEater();
        addUpdateListener(fe);
        addDrawListener(fe);
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

    void saveData() throws IOException {
        Files.deleteIfExists(dataDir.resolve("save.dat"));
        if (currentPlanet != Planet.EARTH) {
            FileOutputStream fos = new FileOutputStream(dataDir.resolve("save.dat").toFile());
            GZIPOutputStream gz = new GZIPOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(gz);
            oos.writeObject(currentPlanet);
            oos.writeObject(collected);
            oos.writeLong(drawables.stream().filter(d -> d instanceof ResourceButton).count());
            for (Drawable d : drawables) {
                if (d instanceof ResourceButton) {
                    ResourceButton rb = (ResourceButton)d;
                    oos.writeInt(rb.ordinal());
                    oos.writeBoolean(rb.isInCooldown());
                    if (rb.isInCooldown()) {
                        oos.writeLong(rb.getCooldownStart());
                        oos.writeLong(rb.getCurrentNS());
                    }
                }
            }
            for (Map.Entry<Upgrade, UpgradeButton> e : Buttons.UPGRADE_BUTTONS.entrySet()) {
                oos.writeObject(e.getKey());
                oos.writeBoolean(e.getValue().hasBeenPurchased());
            }
            oos.writeLong(updateListeners.stream().filter(u -> !(u instanceof Button)).count());
            for (Updatable u : updateListeners) {
                if (!(u instanceof Button)) {
                    oos.writeObject(u);
                }
            }
            oos.writeBoolean(readyToJump);
            oos.flush();
            oos.close();
            fos.close();
        }
    }

    boolean loadData() throws IOException, ClassNotFoundException {
        if (Files.exists(dataDir.resolve("save.dat"))) {
            FileInputStream fis = new FileInputStream(dataDir.resolve("save.dat").toFile());
            GZIPInputStream gz = new GZIPInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(gz);
            currentPlanet = (Planet)ois.readObject();
            collected = (Map<Resource, Integer>)ois.readObject();
            long numOfBts = ois.readLong();
            for (int i = 0; i < numOfBts; i++) {
                ResourceButton rb = Buttons.RESOURCE_BUTTONS[ois.readInt()];
                if (ois.readBoolean()) { // isInCooldown
                    rb.startCooldown(ois.readLong());
                    rb.setCurrentNS(ois.readLong());
                }
                addButton(rb);
            }
            for (int i = 0; i < Buttons.UPGRADE_BUTTONS.size(); i++) {
                Buttons.UPGRADE_BUTTONS.get(ois.readObject()).setPurchased(ois.readBoolean());
            }
            long numOfU = ois.readLong();
            for (int i = 0; i < numOfU; i++) {
                addUpdateListener((Updatable)ois.readObject());
            }
            if (ois.readBoolean()) { // readyToJump
                switch (currentPlanet) {
                    case XEONUS:
                        addButton(Buttons.CONFIRM_JUMP_ONE);
                        break;
                    case DASKOTH:
                        addButton(Buttons.CONFIRM_JUMP_TWO);
                        break;
                    case LEYMIA:
                        addButton(Buttons.CONFIRM_JUMP_THREE);
                        break;
                }
            }
            ois.close();
            fis.close();
            return true;
        }
        return false;
    }

    void playTheme() {
        if (!noMedia) {
            player = new MediaPlayer(currentPlanet.getTheme());
            player.setVolume(0.2);
            player.setCycleCount(MediaPlayer.INDEFINITE);
            player.play();
        }
    }

    public void addResource(Resource resource, int amount) {
        if (collected.containsKey(resource)) {
            int cur = collected.get(resource);
            collected.put(resource, cur + amount);
        }
        if (collected.get(resource) == currentPlanet.getMoveOnAmountFor(resource)) {
            ResourceButton next = getNextButton(Buttons.getResourceButton(currentPlanet, resource));
            if (next != null) {
                if (next.getPlanet() == currentPlanet) {
                    addButton(next);
                } else {
                    if (next.getPlanet() == Planet.DASKOTH) {
                        addButton(Buttons.CONFIRM_JUMP_ONE);
                        readyToJump = true;
                    } else if (next.getPlanet() == Planet.LEYMIA) {
                        addButton(Buttons.CONFIRM_JUMP_TWO);
                        readyToJump = true;
                    }
                }
            } else {
                addButton(Buttons.CONFIRM_JUMP_THREE);
                readyToJump = true;
            }
        }
    }

    private ResourceButton getNextButton(ResourceButton current) {
        int nextIndex = current.ordinal() + 1;
        if (nextIndex < Buttons.RESOURCE_BUTTONS.length) {
            return Buttons.RESOURCE_BUTTONS[nextIndex];
        } else {
            return null;
        }
    }

    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    public void nextPlanet() {
        readyToJump = false;
        currentPlanet = Planet.values()[currentPlanet.ordinal() + 1];
        collected.clear();
        if (currentPlanet != Planet.EARTH) {
            Arrays.stream(currentPlanet.getResources()).forEach(r -> collected.put(r, 0));
            try { drawables.stream().filter(d -> d instanceof Button).forEach(d -> removeButton((Button)d)); } catch (ConcurrentModificationException cme) {} // Throws CME, but works if suppressed.
            addButton(Buttons.getResourceButton(currentPlanet, Resource.FOOD));
        } else {
            drawables.clear();
            updateListeners.clear();
            addDrawListener(new FinalText());
        }
        if (!noMedia) {
            player.stop();
            playTheme();
        }
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
        try { updateListeners.forEach(u -> u.update(this, ns)); } catch (ConcurrentModificationException cme) {} // Swallow it; it works anyways

        for (Upgrade u : Upgrade.values()) {
            UpgradeButton bt = Buttons.UPGRADE_BUTTONS.get(u);
            if (u.canUnlock(this)) {
                if (!bt.hasBeenPurchased()) {
                    addButton(bt);
                }
            } else if (drawables.contains(bt)) { // If we dropped below the threshold
                removeButton(bt);
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

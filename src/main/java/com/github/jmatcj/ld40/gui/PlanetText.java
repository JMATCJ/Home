package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;

public class PlanetText implements Drawable {

    @Override
    public void draw(GraphicsContext gc, Game g) {
        Util.drawText(gc, g.getCurrentPlanet().getTextColor(), 48, TextAlignment.LEFT, "Planet: " + g.getCurrentPlanet().getName(), 10, 40);
    }
}

package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.util.Util;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class FinalText implements Drawable {

    @Override
    public void draw(GraphicsContext gc, Game g) {
        Util.drawText(gc, Color.WHITE, 46, TextAlignment.LEFT, "You have safely returned home", 10, 40);
        Util.drawText(gc, Color.WHITE, 46, TextAlignment.LEFT, "Thanks for playing!\n", 10, 280);
        Util.drawText(gc, Color.WHITE, 36, TextAlignment.LEFT, "Made by Team JMCJ:\nParker: Coding\nTrevor: Coding\nJack: Coding\nDavid: Art & Music\nKurtis: Spiritual Encouragement", 10, 320);
        Util.drawText(gc, Color.WHITE, 46, TextAlignment.LEFT, "Made for Ludum Dare 40", 10, 710);
    }
}

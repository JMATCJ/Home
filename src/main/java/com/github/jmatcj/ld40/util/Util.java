package com.github.jmatcj.ld40.util;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Util {

    /**
     * Returns the number of nanoseconds for a number of seconds.
     * @param sec The number of seconds to get nanoseconds for.
     * @return The number of nanoseconds for the specified number of seconds.
     */
    public static long timeInNS(int sec) {
        return sec * 1000000000L;
    }

    /**
     * Calculates if a specific amount of time has passed.
     * @param startNS The start time in nanoseconds.
     * @param curNS The current time in nanoseconds.
     * @param sec The number of seconds you want to pass for this to be true.
     * @return true if the specified number of seconds have passed, false otherwise.
     */
    public static boolean hasTimeElapsed(long startNS, long curNS, int sec) {
        return startNS + Util.timeInNS(sec) <= curNS;
    }

    /**
     * Renders text to the screen. The application default font (Code Bold) will be used.
     * @param gc The GraphicsContext for the Canvas to be drawn to.
     * @param fontColor The color of the font.
     * @param fontSize The size of the font.
     * @param alignment The alignment of the text.
     * @param text The String of text to be drawn to the screen.
     * @param x The X location to start drawing at.
     * @param y The Y location to start drawing at.
     */
    public static void drawText(GraphicsContext gc, Color fontColor, int fontSize, TextAlignment alignment, String text, int x, int y) {
        gc.setFill(fontColor);
        gc.setFont(Font.loadFont(AssetLoader.getFontLoc(), fontSize));
        gc.setTextAlign(alignment);
        gc.fillText(text, x, y);
    }
}

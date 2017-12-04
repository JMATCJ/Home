package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    void draw(GraphicsContext gc, Game g);
}

package com.github.jmatcj.ld40.tick;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.gui.Drawable;
import com.github.jmatcj.ld40.util.Util;
import java.io.Serializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class FoodEater implements Updatable, Drawable, Serializable {
    private long startNS;
    private long currentNS;
    private boolean drawText;

    public FoodEater() {
        this.startNS = 0L;
        this.drawText = false;
    }

    @Override
    public void update(Game g, long ns) {
        if (g.getNumOfResourcesOnDisplay() > 1) { // They've gotten a new resource
            currentNS = ns;
            if (startNS == 0L) {
                startNS = ns;
            } else if(Util.hasTimeElapsed(startNS, ns, 30)) {
                if (g.getResource(Resource.FOOD) >= 1) {
                    g.addResource(Resource.FOOD, -1);
                    drawText = true;
                }
                startNS = ns;
            }
        } else {
            startNS = 0L;
        }
    }

    @Override
    public void draw(GraphicsContext gc, Game g) {
        if (drawText && !Util.hasTimeElapsed(startNS, currentNS, 5)) {
            Util.drawText(gc, Color.BLACK, 36, TextAlignment.CENTER, "One Food has been consumed", 640, 700);
        }
    }
}

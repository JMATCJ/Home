package com.github.jmatcj.ld40.tick;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.util.Util;

public class FoodEater implements Updatable {
    private long startNS;

    public FoodEater() {
        this.startNS = 0L;
    }

    @Override
    public void update(Game g, long ns) {
        if (g.getButtonsOnDisplay().size() > 1) { // They've gotten a new resource
            if (startNS == 0L) {
                startNS = ns;
            } else if(Util.hasTimeElapsed(startNS, ns, 30)) {
                if (g.getResource(Resource.FOOD) >= 1) {
                    g.addResource(Resource.FOOD, -1);
                }
                startNS = ns;
            }
        } else {
            startNS = 0L;
        }
    }
}

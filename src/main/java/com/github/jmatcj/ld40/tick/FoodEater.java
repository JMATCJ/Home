package com.github.jmatcj.ld40.tick;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Resource;
import com.github.jmatcj.ld40.util.Util;

public class FoodEater implements Updatable {
    private Game game;
    private long startNS;

    public FoodEater(Game game) {
        this.game = game;
        this.startNS = 0L;
    }

    @Override
    public void update(long ns) {
        if (game.getButtonsOnDisplay().size() > 1) { // They've gotten a new resource
            if (startNS == 0L) {
                startNS = ns;
            } else if(startNS + Util.timeInNS(30) <= ns) {
                if (game.getResource(Resource.FOOD) >= 1) {
                    game.addResource(Resource.FOOD, -1);
                }
                startNS = ns;
            }
        } else {
            startNS = 0L;
        }
    }
}

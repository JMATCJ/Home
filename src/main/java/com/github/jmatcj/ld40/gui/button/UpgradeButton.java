package com.github.jmatcj.ld40.gui.button;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Upgrade;
import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class UpgradeButton extends Button implements Serializable {
    private Upgrade upgrade;
    private boolean purchased;

    UpgradeButton(Upgrade upgrade, Image image, int x, int y) {
        super(image, x, y);
        this.upgrade = upgrade;
        this.purchased = false;
    }

    @Override
    public boolean click(MouseEvent e, Game g) {
        if (inBounds(e.getX(), e.getY()) && upgrade.canUnlock(g)) {
            upgrade.removeResources(g);
            g.applyUpgrade(upgrade);
            g.removeButton(this);
            purchased = true;
            return true;
        }
        return false;
    }

    public boolean hasBeenPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }
}

package com.github.jmatcj.ld40.gui;

import com.github.jmatcj.ld40.Game;
import com.github.jmatcj.ld40.data.Upgrade;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class UpgradeButton extends Button {
    private Upgrade upgrade;

    UpgradeButton(Upgrade upgrade, Image image, int x, int y) {
        super(image, x, y);
        this.upgrade = upgrade;
    }

    @Override
    public boolean click(MouseEvent e, Game g) {
        return false;
    }
}

package com.github.jmatcj.ld40;

import javafx.scene.image.Image;

public class Button {
    private Image image;
    private Resources resource;

    public Button(Resources r, Image i) {
        resource = r;
        image = i;
    }

    public Image getImage() {
        return image;
    }

    public Resources getResource() {
        return resource;
    }

    public void onClick() {
        System.out.println("Button was pressed");
    }
}

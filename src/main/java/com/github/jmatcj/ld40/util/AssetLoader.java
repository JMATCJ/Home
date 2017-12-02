package com.github.jmatcj.ld40.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class AssetLoader {
    private static final String ASSETS_DIR = "/assets/ld40/";
    private static final String IMAGES_DIR = ASSETS_DIR + "images/";
    private static final String MUSIC_DIR = ASSETS_DIR + "music/";
    // name:Image pairs
    private static Map<String, Image> images;
    private static Map<String, Media> music;

    public static void initialize() throws IOException {
        // IMAGES
        images = new HashMap<>();
        InputStream is = AssetLoader.class.getResourceAsStream(IMAGES_DIR);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(s -> {
            System.out.println(s + " being loaded...");
            images.put(s, new Image(IMAGES_DIR + s));
        });
        br.close();
        is.close();
        // MUSIC
        music = new HashMap<>();
        is = AssetLoader.class.getResourceAsStream(MUSIC_DIR);
        br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(s -> {
            System.out.println(s + " being loaded...");
            music.put(s, new Media(AssetLoader.MUSIC_DIR + s));
        });
    }

    public static Image getImage(String name) {
        return images.getOrDefault(name, null);
    }
}

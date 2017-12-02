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
    private static final String FONT_LOC = ASSETS_DIR + "font/code_bold.otf";
    // name:Image pairs
    private static Map<String, Image> images;
    private static Map<String, Media> music;

    public static void initialize(boolean noMusic) throws IOException {
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
        if (!noMusic) {
            is = AssetLoader.class.getResourceAsStream(MUSIC_DIR);
            br = new BufferedReader(new InputStreamReader(is));
            br.lines().forEach(s -> {
                if (!s.endsWith(".ogg")) {
                    System.out.println(s + " being loaded...");
                    music.put(s, new Media(AssetLoader.class.getResource(MUSIC_DIR + s).toExternalForm()));
                }
            });
            br.close();
            is.close();
        }
    }

    public static Image getImage(String name) {
        return images.getOrDefault(name, null);
    }

    public static Media getMusic(String name) {
        return music.getOrDefault(name, null);
    }

    public static String getFontLoc() {
        return AssetLoader.class.getResource(FONT_LOC).toExternalForm();
    }
}

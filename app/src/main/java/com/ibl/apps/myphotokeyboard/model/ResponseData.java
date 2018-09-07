package com.ibl.apps.myphotokeyboard.model;

/**
 * Created by iblinfotech on 02/05/17.
 */

public class ResponseData {

    private Sound sound;

    private Wallpaper wallpaper;

    private Fonts fonts;

    private DefaultColor color;

    public DefaultColor getColor() {
        return color;
    }

    public void setColor(DefaultColor color) {
        this.color = color;
    }

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Wallpaper getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(Wallpaper wallpaper) {
        this.wallpaper = wallpaper;
    }

    public Fonts getFonts() {
        return fonts;
    }

    public void setFonts(Fonts fonts) {
        this.fonts = fonts;
    }
}

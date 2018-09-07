package com.ibl.apps.myphotokeyboard.model;

/**
 * Created by iblinfotech on 02/05/17.
 */

public class Wallpaper {
    private WallpaperPaid paid;

    private WallpaperFree free;

    public WallpaperPaid getPaid() {
        return paid;
    }

    public void setPaid(WallpaperPaid paid) {
        this.paid = paid;
    }

    public WallpaperFree getFree() {
        return free;
    }

    public void setFree(WallpaperFree free) {
        this.free = free;
    }

}

package com.ibl.apps.myphotokeyboard.model;

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

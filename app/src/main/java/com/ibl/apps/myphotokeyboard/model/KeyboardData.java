package com.ibl.apps.myphotokeyboard.model;

import android.graphics.Bitmap;

/**
 * Created by iblinfotech on 25/05/17.
 */

public class KeyboardData {

    String isColor;
    int keyboardBgImage;
    int keyboardColorCode;
    String keyRadius;
    String keyStroke;
    String keyOpacity;
    int keyBgColor;
    String fontColor;
    String fontName;
    boolean isSelected;
    String soundStatus;
    int soundName;
    int selectcolor;
    int selecttextwallpaper;
    int selectwallpaper;
    int selview;
    String bitmapback;

    public String getBitmapback() {
        return bitmapback;
    }

    public void setBitmapback(String bitmapback) {
        this.bitmapback = bitmapback;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getIsColor() {
        return isColor;
    }

    public void setIsColor(String isColor) {
        this.isColor = isColor;
    }

    public int getKeyboardBgImage() {
        return keyboardBgImage;
    }

    public void setKeyboardBgImage(int keyboardBgImage) {
        this.keyboardBgImage = keyboardBgImage;
    }

    public int getKeyboardColorCode() {
        return keyboardColorCode;
    }

    public void setKeyboardColorCode(int keyboardColorCode) {
        this.keyboardColorCode = keyboardColorCode;
    }

    public String getKeyRadius() {
        return keyRadius;
    }

    public void setKeyRadius(String keyRadius) {
        this.keyRadius = keyRadius;
    }

    public String getKeyStroke() {
        return keyStroke;
    }

    public void setKeyStroke(String keyStroke) {
        this.keyStroke = keyStroke;
    }

    public String getKeyOpacity() {
        return keyOpacity;
    }

    public void setKeyOpacity(String keyOpacity) {
        this.keyOpacity = keyOpacity;
    }

    public int getKeyBgColor() {
        return keyBgColor;
    }

    public void setKeyBgColor(int keyBgColor) {
        this.keyBgColor = keyBgColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getSoundStatus() {
        return soundStatus;
    }

    public void setSoundStatus(String soundStatus) {
        this.soundStatus = soundStatus;
    }

    public int getSoundName() {
        return soundName;
    }

    public void setSoundName(int soundName) {
        this.soundName = soundName;
    }

    public int getSelectcolor() {
        return selectcolor;
    }

    public void setSelectcolor(int selectcolor) {
        this.selectcolor = selectcolor;
    }

    public int getSelecttextwallpaper() {
        return selecttextwallpaper;
    }

    public void setSelecttextwallpaper(int selecttextwallpaper) {
        this.selecttextwallpaper = selecttextwallpaper;
    }

    public int getSelectwallpaper() {
        return selectwallpaper;
    }

    public void setSelectwallpaper(int selectwallpaper) {
        this.selectwallpaper = selectwallpaper;
    }

    public int getSelview() {
        return selview;
    }

    public void setSelview(int selview) {
        this.selview = selview;
    }

    @Override
    public String toString() {
        return "KeyboardData{" +
                "isColor='" + isColor + '\'' +
                ", keyboardBgImage=" + keyboardBgImage +
                ", keyboardColorCode=" + keyboardColorCode +
                ", keyRadius='" + keyRadius + '\'' +
                ", keyStroke='" + keyStroke + '\'' +
                ", keyOpacity='" + keyOpacity + '\'' +
                ", keyBgColor=" + keyBgColor +
                ", fontColor='" + fontColor + '\'' +
                ", fontName='" + fontName + '\'' +
                ", isSelected=" + isSelected +
                ", soundStatus='" + soundStatus + '\'' +
                ", soundName=" + soundName +
                ", selectcolor=" + selectcolor +
                ", selecttextwallpaper=" + selecttextwallpaper +
                ", selectwallpaper=" + selectwallpaper +
                ", selview=" + selview +
                ", bitmapback='" + bitmapback + '\'' +
                '}';
    }
}

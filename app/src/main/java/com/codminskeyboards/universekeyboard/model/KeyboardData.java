package com.codminskeyboards.universekeyboard.model;

public class KeyboardData {

    private int keyboardBgImage;
    private int keyboardColorCode;
    private int keyRadius;
    private int keyStroke;
    private int keyOpacity;
    private int keyBgColor;
    private String fontColor;
    private String fontName;
    private boolean isSelected;
    private boolean soundStatus;
    private int soundName;
    private int selectcolor;
    private int selectwallpaper;
    private int selview;
    private String bitmapback;

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

    public int getKeyRadius() {
        return keyRadius;
    }

    public void setKeyRadius(int keyRadius) {
        this.keyRadius = keyRadius;
    }

    public int getKeyStroke() {
        return keyStroke;
    }

    public void setKeyStroke(int keyStroke) {
        this.keyStroke = keyStroke;
    }

    public int getKeyOpacity() {
        return keyOpacity;
    }

    public void setKeyOpacity(int keyOpacity) {
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

    public boolean getSoundStatus() {
        return soundStatus;
    }

    public void setSoundStatus(boolean soundStatus) {
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
                ", selectwallpaper=" + selectwallpaper +
                ", selview=" + selview +
                ", bitmapback='" + bitmapback + '\'' +
                '}';
    }
}

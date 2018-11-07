package com.codminskeyboards.universekeyboard.model;

public class KeyboardData {

    private String isColor;
    private int keyboardBgImage;
    private int keyboardColorCode;
    private float keyRadius;
    private int keyStroke;
    private int keyOpacity;
    private int keyBgColor;
    private String fontColor;
    private String fontName;
    private boolean isSelected;
    private String soundStatus;
    private int soundName;
    private int selectcolor;
    private int selecttextwallpaper;
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

    public float getKeyRadius() {
        return keyRadius;
    }

    public void setKeyRadius(Float keyRadius) {
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
                ", selectwallpaper=" + selectwallpaper +
                ", selview=" + selview +
                ", bitmapback='" + bitmapback + '\'' +
                '}';
    }
}

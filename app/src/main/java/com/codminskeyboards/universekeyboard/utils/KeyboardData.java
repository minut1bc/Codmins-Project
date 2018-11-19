package com.codminskeyboards.universekeyboard.utils;

public class KeyboardData {

    private int keyboardBackground;
    private int keyRadius;
    private int keyStroke;
    private int keyOpacity;
    private int keyColor;
    private int fontColor;
    private int fontId;
    private boolean isSelected;
    private boolean soundStatus;
    private int soundId;
    private int vibrationValue;
    private int colorPosition;
    private int backgroundPosition;
    private int drawableOrColor;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getKeyboardBackground() {
        return keyboardBackground;
    }

    public void setKeyboardBackground(int keyboardBackground) {
        this.keyboardBackground = keyboardBackground;
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

    public int getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(int keyColor) {
        this.keyColor = keyColor;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public int getFontId() {
        return fontId;
    }

    public void setFontId(int fontId) {
        this.fontId = fontId;
    }

    public boolean getSoundStatus() {
        return soundStatus;
    }

    public void setSoundStatus(boolean soundStatus) {
        this.soundStatus = soundStatus;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public int getVibrationValue() {
        return vibrationValue;
    }

    public void setVibrationValue(int vibrationValue) {
        this.vibrationValue = vibrationValue;
    }

    public int getColorPosition() {
        return colorPosition;
    }

    public void setColorPosition(int colorPosition) {
        this.colorPosition = colorPosition;
    }

    public int getBackgroundPosition() {
        return backgroundPosition;
    }

    public void setBackgroundPosition(int backgroundPosition) {
        this.backgroundPosition = backgroundPosition;
    }

    public int getDrawableOrColor() {
        return drawableOrColor;
    }

    public void setDrawableOrColor(int drawableOrColor) {
        this.drawableOrColor = drawableOrColor;
    }
}

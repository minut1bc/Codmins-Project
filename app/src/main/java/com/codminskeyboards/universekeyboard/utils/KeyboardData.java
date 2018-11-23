package com.codminskeyboards.universekeyboard.utils;

public class KeyboardData {

    private boolean isSelected;

    private boolean backgroundIsDrawable;
    private int backgroundPosition;
    private int backgroundColorPosition;
    private int keyRadius;
    private int keyStroke;
    private int keyOpacity;
    private int keyColorPosition;
    private int fontPosition;
    private int fontColorPosition;
    private int vibrationValue;
    private int soundPosition;
    private boolean soundOn;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }


    public boolean getBackgroundIsDrawable() {
        return backgroundIsDrawable;
    }

    public void setBackgroundIsDrawable(boolean backgroundIsDrawable) {
        this.backgroundIsDrawable = backgroundIsDrawable;
    }

    public int getBackgroundPosition() {
        return backgroundPosition;
    }

    public void setBackgroundPosition(int backgroundPosition) {
        this.backgroundPosition = backgroundPosition;
    }

    public int getBackgroundColorPosition() {
        return backgroundColorPosition;
    }

    public void setBackgroundColorPosition(int backgroundColorPosition) {
        this.backgroundColorPosition = backgroundColorPosition;
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

    public int getKeyColorPosition() {
        return keyColorPosition;
    }

    public void setKeyColorPosition(int keyColorPosition) {
        this.keyColorPosition = keyColorPosition;
    }

    public int getFontPosition() {
        return fontPosition;
    }

    public void setFontPosition(int fontPosition) {
        this.fontPosition = fontPosition;
    }

    public int getFontColorPosition() {
        return fontColorPosition;
    }

    public void setFontColorPosition(int fontColorPosition) {
        this.fontColorPosition = fontColorPosition;
    }

    public int getVibrationValue() {
        return vibrationValue;
    }

    public void setVibrationValue(int vibrationValue) {
        this.vibrationValue = vibrationValue;
    }

    public int getSoundPosition() {
        return soundPosition;
    }

    public void setSoundPosition(int soundPosition) {
        this.soundPosition = soundPosition;
    }

    public boolean getSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }
}

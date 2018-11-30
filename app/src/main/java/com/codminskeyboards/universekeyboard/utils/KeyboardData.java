package com.codminskeyboards.universekeyboard.utils;

public class KeyboardData {

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

    public static KeyboardData defaultKeyboard() {
        KeyboardData defaultKeyboardData = new KeyboardData();
        defaultKeyboardData.setBackgroundIsDrawable(true);
        defaultKeyboardData.setBackgroundPosition(0);
        defaultKeyboardData.setBackgroundColorPosition(0);
        defaultKeyboardData.setKeyRadius(34);
        defaultKeyboardData.setKeyStroke(1);
        defaultKeyboardData.setKeyOpacity(64);
        defaultKeyboardData.setKeyColorPosition(1);
        defaultKeyboardData.setFontPosition(0);
        defaultKeyboardData.setFontColorPosition(1);
        defaultKeyboardData.setVibrationValue(0);
        defaultKeyboardData.setSoundPosition(0);
        defaultKeyboardData.setSoundOn(false);

        return defaultKeyboardData;
    }

    public static String serialize(KeyboardData keyboardData) {

        return keyboardData.getBackgroundIsDrawable() + ","
                + keyboardData.getBackgroundPosition() + ","
                + keyboardData.getBackgroundColorPosition() + ","
                + keyboardData.getKeyRadius() + ","
                + keyboardData.getKeyStroke() + ","
                + keyboardData.getKeyOpacity() + ","
                + keyboardData.getKeyColorPosition() + ","
                + keyboardData.getFontPosition() + ","
                + keyboardData.getFontColorPosition() + ","
                + keyboardData.getVibrationValue() + ","
                + keyboardData.getSoundPosition() + ","
                + keyboardData.getSoundOn();
    }

    public static KeyboardData deserialize(String serialized) {
        String[] serializedData = serialized.split(",");
        KeyboardData keyboardData = new KeyboardData();
        keyboardData.setBackgroundIsDrawable(Boolean.valueOf(serializedData[0]));
        keyboardData.setBackgroundPosition(Integer.valueOf(serializedData[1]));
        keyboardData.setBackgroundColorPosition(Integer.valueOf(serializedData[2]));
        keyboardData.setKeyRadius(Integer.valueOf(serializedData[3]));
        keyboardData.setKeyStroke(Integer.valueOf(serializedData[4]));
        keyboardData.setKeyOpacity(Integer.valueOf(serializedData[5]));
        keyboardData.setKeyColorPosition(Integer.valueOf(serializedData[6]));
        keyboardData.setFontPosition(Integer.valueOf(serializedData[7]));
        keyboardData.setFontColorPosition(Integer.valueOf(serializedData[8]));
        keyboardData.setVibrationValue(Integer.valueOf(serializedData[9]));
        keyboardData.setSoundPosition(Integer.valueOf(serializedData[10]));
        keyboardData.setSoundOn(Boolean.valueOf(serializedData[11]));
        return keyboardData;
    }
}

package com.ibl.apps.myphotokeyboard.model;


public class NewSoundData {
    private boolean selected;

    private int resourceId;

    public NewSoundData(int resourceId, boolean selected) {
        this.selected = selected;
        this.resourceId = resourceId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}

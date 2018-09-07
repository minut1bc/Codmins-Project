package com.ibl.apps.myphotokeyboard.model;

/**
 * Created by iblinfotech on 8/29/17.
 */

public class NewSoundData {
    boolean selected;

    public NewSoundData(int resourceId,boolean selected) {
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

    int resourceId;
}

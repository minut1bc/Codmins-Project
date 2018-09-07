package com.ibl.apps.myphotokeyboard.model;

/**
 * Created by iblinfotech on 8/30/17.
 */

public class NewColorData {

    private int id;
    private int colorResourseId;
    private boolean isSelected;

    public NewColorData(int id, int colorResourseId, boolean isSelected, boolean isPaid) {
        this.id = id;
        this.colorResourseId = colorResourseId;
        this.isSelected = isSelected;
        this.isPaid = isPaid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColorResourseId() {
        return colorResourseId;
    }

    public void setColorResourseId(int colorResourseId) {
        this.colorResourseId = colorResourseId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    private boolean isPaid;


}


package com.ibl.apps.myphotokeyboard.model;

public class Free {
    private String id;

    private String title;

    private String sound_url;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String isPaid() {
        return isPaid;
    }

    public void setPaid(String paid) {
        isPaid = paid;
    }

    private String isPaid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSound_url() {
        return sound_url;
    }

    public void setSound_url(String sound_url) {
        this.sound_url = sound_url;
    }

}

package com.ibl.apps.myphotokeyboard.model;


public class FontsPaid {

    private String id;

    private String title;

    private String font_url;
    private boolean isSelected;
    public String isPaid() {
        return isPaid;
    }

    public void setPaid(String paid) {
        isPaid = paid;
    }

    private String isPaid;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    public String getFont_url() {
        return font_url;
    }

    public void setFont_url(String font_url) {
        this.font_url = font_url;
    }

}

package com.ibl.apps.myphotokeyboard.model;

public class DefaultColorFree {
    private String id;

    private String color_code;

    private String type;

    private boolean selected;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor_code() {
        return color_code;
    }

    public void setColor_code(String color_code) {
        this.color_code = color_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

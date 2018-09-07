package com.ibl.apps.myphotokeyboard.model;

import java.util.ArrayList;

public class WallpaperPaid {
    private ArrayList<Textual> textual;

    private ArrayList<Color> color;

    public ArrayList<Textual> getTextual ()
    {
        return textual;
    }

    public void setTextual (ArrayList<Textual> textual)
    {
        this.textual = textual;
    }

    public ArrayList<Color> getColor ()
    {
        return color;
    }

    public void setColor (ArrayList<Color> color)
    {
        this.color = color;
    }
}

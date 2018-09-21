package com.codminskeyboards.universekeyboard.model;

import java.util.ArrayList;


public class DefaultColor {

    private ArrayList<DefaultColorFree> paid;

    private ArrayList<DefaultColorFree> free;

    public ArrayList<DefaultColorFree> getPaid ()
    {
        return paid;
    }

    public void setPaid (ArrayList<DefaultColorFree> paid)
    {
        this.paid = paid;
    }

    public ArrayList<DefaultColorFree> getFree ()
    {
        return free;
    }

    public void setFree (ArrayList<DefaultColorFree> free)
    {
        this.free = free;
    }
}

package com.ibl.apps.myphotokeyboard.model;

import java.util.ArrayList;

public class Fonts {

    private ArrayList<FontsPaid> paid=new ArrayList<>();

    private ArrayList<FontsPaid> free;

    public ArrayList<FontsPaid> getPaid ()
    {
        return paid;
    }

    public void setPaid (ArrayList<FontsPaid> paid)
    {
        this.paid = paid;
    }

    public ArrayList<FontsPaid> getFree ()
    {
        return free;
    }

    public void setFree (ArrayList<FontsPaid> free)
    {
        this.free = free;
    }
}

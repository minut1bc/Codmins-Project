package com.ibl.apps.myphotokeyboard.model;

import java.util.ArrayList;

public class Sound {

    private ArrayList<Paid> paid;

    private ArrayList<Free> free;

    public ArrayList<Paid> getPaid() {
        return paid;
    }

    public void setPaid(ArrayList<Paid> paid) {
        this.paid = paid;
    }

    public ArrayList<Free> getFree() {
        return free;
    }

    public void setFree(ArrayList<Free> free) {
        this.free = free;
    }
}

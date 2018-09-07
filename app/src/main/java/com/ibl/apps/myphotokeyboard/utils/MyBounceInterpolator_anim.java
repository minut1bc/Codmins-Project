package com.ibl.apps.myphotokeyboard.utils;

/**
 * Created by iblinfotech on 03/07/18.
 */

public class MyBounceInterpolator_anim implements android.view.animation.Interpolator {


    private double mAmplitude = 1;
    private double mFrequency = 10;


    public MyBounceInterpolator_anim(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    @Override
    public float getInterpolation(float time) {

        return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1);
    }
}
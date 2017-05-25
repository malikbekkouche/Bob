package com.example.malik.bob.Objects;

/**
 * Created by malik on 05-05-2017.
 */

public class Chirp {
    private double temp;
    private int moist;
    private double light;

    public Chirp(double temp, int moist, double light) {
        this.temp = temp;
        this.moist = moist;
        this.light = light;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public int getMoist() {
        return moist;
    }

    public void setMoist(int moist) {
        this.moist = moist;
    }

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }
}

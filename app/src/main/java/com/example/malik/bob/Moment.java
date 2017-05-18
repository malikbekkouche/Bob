package com.example.malik.bob;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by malik on 26-04-2017.
 */

public class Moment extends RealmObject implements Serializable{
    public long getId() {
        return id;
    }

    public void setId(long id) {

        this.id = id;
    }

    @PrimaryKey
    private long id;
    private String date;
    private String name;
    private int moisture;
    private double light, temperature;
    private byte[] photo;

    public Moment(){}

    public Moment(String date, String name, int moisture, double light, double temperature) {
        this.date = date;
        this.name = name;
        this.moisture = moisture;
        this.light = light;
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getMoisture() {
        return moisture;
    }

    public double getLight() {
        return light;
    }

    public double getTemperature() {
        return temperature;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoisture(int moisture) {
        this.moisture = moisture;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
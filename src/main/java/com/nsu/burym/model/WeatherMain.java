package com.nsu.burym.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMain {
    private double temp;
    private double feels_like;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    @Override
    public String toString() {
        return "WeatherMain{" +
                "temp=" + temp +
                ", feels_like=" + feels_like +
                '}';
    }
}

package com.christopherhield.act2;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private double hourlyRate;

    Person(String name, double hourlyRate) {
        this.name = name;
        this.hourlyRate = hourlyRate;
    }

    void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    double getHourlyRate() {
        return hourlyRate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Person:\n" + name + ", " +  hourlyRate;
    }
}

package com.christopherhield.notes;

import androidx.annotation.NonNull;

public class Product {

    private String name;
    private String description;

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String toString() {
        return name + ": " + description;
    }
}

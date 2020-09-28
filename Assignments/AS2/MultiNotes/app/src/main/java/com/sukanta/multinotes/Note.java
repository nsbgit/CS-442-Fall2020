package com.sukanta.multinotes;

import androidx.annotation.NonNull;

import java.util.Date;

public class Note {
    private String title;
    private String text;
    private Date lastUpdatedTime;

    Note() {}

    Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.lastUpdatedTime = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    @NonNull
    @Override
    public String toString() {
        return "Last Updated on: " + this.lastUpdatedTime + "\nTitle: " + this.title + "\nText: " + this.text;
    }
}

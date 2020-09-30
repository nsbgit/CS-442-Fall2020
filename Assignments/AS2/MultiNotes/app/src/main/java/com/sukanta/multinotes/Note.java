package com.sukanta.multinotes;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note implements Serializable {
    private String title;
    private String text;
    private Date lastUpdatedTime;

    Note() {
        this.title = "";
        this.text = "";
        this.lastUpdatedTime = new Date();
    }

    Note(String title, String text) {
        this.title = title;
        this.text = text;
        this.lastUpdatedTime = new Date();
    }

    public String getTitle() {
        return title;
    }

    public String getTitle80Char() {
        if (this.title.length() > 80) {
            return String.format("%."+ 80 +"s...", this.title);
        } else {
            return this.title;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public String getText80Char() {
        if (this.text.length() > 80) {
            return String.format("%."+ 80 +"s...", this.text);
        } else {
            return this.text;
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getLastUpdatedTimeFormatted() {
        DateFormat dateFormat = new SimpleDateFormat("E MMM dd, hh:mm aa", Locale.US);
        return dateFormat.format(this.lastUpdatedTime);
    }

    @NonNull
    @Override
    public String toString() {
        return "Last Updated on: " + this.lastUpdatedTime + "\nTitle: " + this.title + "\nText: " + this.text;
    }

    public void save(String title, String text) {
        this.title = title;
        this.text = text;
        this.lastUpdatedTime = new Date();
    }
}

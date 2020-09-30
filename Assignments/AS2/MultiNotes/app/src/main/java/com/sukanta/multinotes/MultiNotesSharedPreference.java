package com.sukanta.multinotes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MultiNotesSharedPreference {
    private static final String PREFS_KEY = "MULTI-NOTES_PREFS_KEY";
    private SharedPreferences prefs;

    MultiNotesSharedPreference(Activity activity) {
        super();

        prefs = activity.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
    }

    public void save(String key, String text) {
        Editor editor =prefs.edit();
        editor.putString(key, text);
        editor.apply();
    }

    public String getValue(String key) {
        return prefs.getString(key, "");
    }

    public void removeValue(String key) {
        Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clearAll() {
        Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}

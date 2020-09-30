package com.sukanta.multinotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etText;
    private String oldTitle = "";
    private String oldText = "";
    private Note note;
    private int pos;
    private static final String NOTE_KEY = "NOTE";
    private static final String POSITION_KEY = "POSITION";
    private static final String TAG = "EditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getData();
        InitializeScreenItems();
    }

    private void getData() {
        try {
            if (getIntent().hasExtra(NOTE_KEY)) {
                note = (Note) getIntent().getSerializableExtra(NOTE_KEY);
            } else {
                note = new Note();
            }
            if (getIntent().hasExtra(POSITION_KEY)) {
                pos = getIntent().getIntExtra(POSITION_KEY, -1);
            } else {
                pos = -1;
            }
        } catch (Exception e) {
            Log.e(TAG, "getData: ", e);
            note = new Note();
        }
    }

    private void InitializeScreenItems() {
        etTitle = findViewById(R.id.etTitle);
        etText = findViewById(R.id.etText);
        etText.setMovementMethod(new ScrollingMovementMethod());

        oldTitle = note.getTitle();
        oldText = note.getText();

        etTitle.setText(oldTitle);
        etText.setText(oldText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSave:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        if (isChanged())
            note.save(etTitle.getText().toString(), etText.getText().toString());
        //return object

        Intent intent = new Intent();
        intent.putExtra(NOTE_KEY, note);
        intent.putExtra(POSITION_KEY, pos);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private boolean isChanged() {
        if(oldTitle.equals("") && oldText.equals("")) {
            return true;
        }
        else if (!oldTitle.equals(etTitle.getText().toString())
                || !oldText.equals(etText.getText().toString())) {
            // update the record
            return true;
        }
        return false;
    }
}
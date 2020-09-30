package com.sukanta.multinotes;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getData();
        InitializeScreenItems();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        if (isChanged())
            showAlertDialog();
        super.onBackPressed();
    }

    private void getData() {
        Log.d(TAG, "getData: ");
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
        Log.d(TAG, "InitializeScreenItems: ");
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
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.action_edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        if (item.getItemId() == R.id.mSave) {
            saveNote();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {
        try {
            Log.d(TAG, "saveNote: ");
            if (isChanged()) {
                if (etTitle.getText().toString().equals("")) {
                    Log.d(TAG, "saveNote: Not saved as title is blank");
                    Toast.makeText(this, "Un-titled activity was not saved", Toast.LENGTH_LONG).show();
                    exitWithoutSaving();
                    return;
                }
                note.save(etTitle.getText().toString(), etText.getText().toString());
            }

            Intent intent = new Intent();
            intent.putExtra(NOTE_KEY, note);
            intent.putExtra(POSITION_KEY, pos);
            setResult(Activity.RESULT_OK, intent);
            Log.d(TAG, "saveNote: finish() called after save");
            finish();
        } catch (Exception e) {
            Log.e(TAG, "saveNote: ", e);
        }
    }

    private void exitWithoutSaving() {
        Log.d(TAG, "exitWithoutSaving: ");
        setResult(Activity.RESULT_CANCELED);
        Log.d(TAG, "exitWithoutSaving: finish called");
        finish();
    }

    private boolean isChanged() {
        Log.d(TAG, "isChanged: ");
        if (!oldTitle.equals(etTitle.getText().toString())
                || !oldText.equals(etText.getText().toString())) {
            Log.d(TAG, "isChanged: Unsaved changes");
            return true;
        }
        return false;
    }

    public void showAlertDialog() {
        try {
            Log.d(TAG, "showAlertDialog: ");
            // Simple Ok & Cancel dialog - no view used.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String s = "Your note is not saved!\n" +
                    "Save note ‘" + etTitle.getText() + "’?";

            //builder.setIcon(R.drawable.icon1);
            builder.setTitle(s)
                    .setMessage("My message")
                    .setNegativeButton("NO1", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: NO1");
                            //dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton("YES1", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: YES1");
                            //dialogInterface.dismiss();
                        }
                    }).show();

                /*
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onClick YES: id: " + id);
                saveNote();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onClick NO: id: " + id);
            }
        });

        //builder.setMessage("Delete Note '" + note.getTitle() + "'?");
        String s = "Your note is not saved!\n" +
                "Save note ‘" + etTitle.getText() + "’?";
        builder.setTitle("Do you want to save?");

        AlertDialog dialog = builder.create();
        dialog.show();

                 */
        } catch (Exception e) {
            Log.e(TAG, "showAlertDialog: ", e);
        }
    }
}
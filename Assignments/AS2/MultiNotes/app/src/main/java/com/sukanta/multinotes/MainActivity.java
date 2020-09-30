package com.sukanta.multinotes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {
    private static final int NOTE_REQUEST = 1000;
    private static final String TAG = "MainActivity";
    private static final String NOTE_KEY = "NOTE";
    private static final String POSITION_KEY = "POSITION";
    private final List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadFile();

        InitializeScreenItems();
    }

    @Override
    protected void onPostResume() {
        Log.d(TAG, "onPostResume: ");
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        saveNoteListToJson();
        super.onPause();
    }

    private void InitializeScreenItems() {
        Log.d(TAG, "InitializeScreenItems: ");
        recyclerView = findViewById(R.id.rvRecycler);
        // Data to recyclerview adapter
        notesAdapter = new NotesAdapter(noteList, this);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Make some data - not always needed - just used to fill list
        //CreateDummyData();



        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.app_name) + " (" + (noteList != null ? noteList.size() : 0) + ")");
    }

    private void CreateDummyData() {
        Log.d(TAG, "CreateDummyData: ");
        noteList.clear();
        for (int i = 0; i < 5; i++) {
            noteList.add(new Note("Title " + i, "Here is the text " + i));
        }
        saveNoteListToJson();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()) {
            case R.id.mInfo:
                goToAboutActivity();
                return true;
            case R.id.mAdd:
                goToEditActivity(-1); // New Entry
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: ");
        int position = recyclerView.getChildLayoutPosition(view);
        goToEditActivity(position);
    }

    @Override
    public boolean onLongClick(View view) {
        Log.d(TAG, "onLongClick: ");
        /*
        int position = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(position);
        Toast.makeText(view.getContext(), "LONG " + note.toString(), Toast.LENGTH_SHORT).show();
         */
        showAlertDialog(view);
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        Toast.makeText(this, "The back button was pressed - Bye!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void goToAboutActivity() {
        Log.d(TAG, "goToAboutActivity: ");
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        //intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
        startActivity(intent);
    }

    public void goToEditActivity(int position) {
        Log.d(TAG, "goToEditActivity: ");
        Note note;
        if (position < 0) {
            // New Entry
            note = new Note();
        }
        else {
            note = noteList.get(position);
        }

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(NOTE_KEY, note);
        intent.putExtra(POSITION_KEY, position);
        startActivityForResult(intent, NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + "\nresultCode: " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);

        Note note;
        int position;
        if (requestCode == NOTE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    if (data.hasExtra(NOTE_KEY)) {
                        note = (Note) data.getSerializableExtra(NOTE_KEY);

                        if (note != null) {
                            if (data.hasExtra(POSITION_KEY)) {
                                position = data.getIntExtra(POSITION_KEY, -2);

                                if(position < 0)
                                    noteList.add(0, note);
                                else
                                    noteList.set(position, note);

                                sort();
                                notesAdapter.notifyDataSetChanged();
                                saveNoteListToJson();
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadFile() {
        Log.d(TAG, "loadFile: ");
        try {
            InputStream fileInputStream = getApplicationContext().
                    openFileInput(getString(R.string.file_name));
            JsonHelper jsonHelper = new JsonHelper();
            jsonHelper.readJsonStream(fileInputStream, noteList);
            sort();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveNoteListToJson() {
        Log.d(TAG, "saveNoteListToJson: Note Saving to JSON Started");
        try {
            FileOutputStream fileOutputStream = getApplicationContext().
                    openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

            JsonHelper jsonHelper = new JsonHelper();
            jsonHelper.writeJsonStream(fileOutputStream, noteList);

            /// You do not need to do the below - it's just
            /// a way to print out the JSON that is created.
            ///
            /*
            StringWriter sw = new StringWriter();
            writer = new JsonWriter(sw);
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("name").value(product.getName());
            writer.name("description").value(product.getDescription());
            writer.endObject();
            writer.close();
            Log.d(TAG, "saveProduct: JSON:\n" + sw.toString());

             */
            ///
            ////////////////////////////////////////////////

            Log.d(TAG, "saveNoteListToJson: JSON file Updated at " + new Date().toString());
        } catch (Exception e) {
            Log.e(TAG, "saveNoteListToJson: Error: " + e.getMessage(), e);
        }
        Log.d(TAG, "saveNoteListToJson: Notes Saving to JSON Finished");
    }

    public void sort() {
        Log.d(TAG, "sort: ");
        Collections.sort(noteList, new SortByLastUpdatedTimeDesc());
    }

    public void showAlertDialog(View view) {
        Log.d(TAG, "showAlertDialog: ");
        // Simple Ok & Cancel dialog - no view used.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int pos = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(pos);

        //builder.setIcon(R.drawable.icon1);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onClick YES: id: " + id);
                noteList.remove(pos);
                sort();
                notesAdapter.notifyDataSetChanged();
                saveNoteListToJson();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "onClick NO: id: " + id);
            }
        });

        //builder.setMessage("Delete Note '" + note.getTitle() + "'?");
        builder.setTitle("Delete Note '" + note.getTitle() + "'?");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

class SortByLastUpdatedTimeDesc implements Comparator<Note>
{
    @Override
    public int compare(Note first, Note second) {
        //return first.getLastUpdatedTime().compareTo(second.getLastUpdatedTime());
        return second.getLastUpdatedTime().compareTo(first.getLastUpdatedTime());
    }
}


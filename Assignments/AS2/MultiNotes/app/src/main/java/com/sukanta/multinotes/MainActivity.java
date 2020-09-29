package com.sukanta.multinotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {
    private static final int NOTE_REQUEST = 1000;
    private final List<Note> noteList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        InitializeScreenItems();
    }

    private void InitializeScreenItems() {
        recyclerView = findViewById(R.id.rvRecycler);
        // Data to recyclerview adapter
        NotesAdapter notesAdapter = new NotesAdapter(noteList, this);
        recyclerView.setAdapter(notesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Make some data - not always needed - just used to fill list
        CreateDummyData();

        getSupportActionBar().setTitle(getString(R.string.app_name) + " (" + noteList.size() + ")");
    }

    private void CreateDummyData() {
        for (int i = 0; i < 50; i++) {
            noteList.add(new Note("Title " + i, "Here is the text " + i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mInfo:
                goToAboutActivity();
                return true;
            case R.id.mAdd:
                Toast.makeText(this, "Add Clicked", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(position);
        Toast.makeText(view.getContext(), "SHORT " + note.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view) {
        int position = recyclerView.getChildLayoutPosition(view);
        Note note = noteList.get(position);
        Toast.makeText(view.getContext(), "LONG " + note.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "The back button was pressed - Bye!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void goToAboutActivity() {
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
        //intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
        startActivity(intent);
    }

    public void goToEditActivity() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("NOTE", "");
        startActivityForResult(intent, NOTE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == NOTE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.hasExtra("NOTE")) {
                    String status = data.getStringExtra("NOTE");
                    nameText.setVisibility(View.INVISIBLE);
                    responseText.setText(String.format("Hello %s!%n%nI'm glad you are %s.", name, status));
                    findViewById(R.id.button3).setVisibility(View.INVISIBLE);
                    greetText.setText(R.string.i_know_you);
                }
            }
        }
 */
    }
}
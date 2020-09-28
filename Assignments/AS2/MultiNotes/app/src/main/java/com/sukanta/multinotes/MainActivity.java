package com.sukanta.multinotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                Toast.makeText(this, "Info Clicked", Toast.LENGTH_LONG).show();
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
}
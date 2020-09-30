package com.sukanta.multinotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private List<Note> noteList;
    private MainActivity mainActivity;
    private static final String TAG = "NotesAdapter";

    NotesAdapter() {}
    NotesAdapter(List<Note> noteList, MainActivity mainActivity) {
        this.noteList = noteList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);
        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        try {
            Note note = noteList.get(position);

            holder.tvTitle.setText(note.getTitle());
            holder.tvLastUpdatedTime.setText(note.getLastUpdatedTimeFormatted());
            holder.tvText.setText(note.getText80Char());
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: ", e);
        }
    }

    @Override
    public int getItemCount() {
        try {
            return noteList.size();
        } catch (Exception e) {
            return 0;
        }
    }
}

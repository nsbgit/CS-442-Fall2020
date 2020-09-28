package com.sukanta.multinotes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    TextView tvTitle;
    TextView tvLastUpdatedTime;
    TextView tvText;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvTitle =itemView.findViewById(R.id.tvTitle);
        this.tvLastUpdatedTime = itemView.findViewById(R.id.tvLastUpdatedTime);
        this.tvText = itemView.findViewById(R.id.tvText);
    }
}

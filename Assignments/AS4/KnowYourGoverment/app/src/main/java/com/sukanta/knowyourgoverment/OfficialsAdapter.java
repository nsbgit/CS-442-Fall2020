package com.sukanta.knowyourgoverment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OfficialsAdapter extends RecyclerView.Adapter<OfficialsViewHolder> {
    private List<Official> officialList;
    private MainActivity mainActivity;
    private static final String TAG = "OfficialsAdapter";

    OfficialsAdapter(List<Official> officialList, MainActivity mainActivity) {
        this.officialList = officialList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public OfficialsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.officials_row, parent, false);

        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener(mainActivity);

        return new OfficialsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialsViewHolder holder, int position) {
        Official official = officialList.get(position);

        String officeName = official.getOfficeName();
        String officialNameAndParty = official.getOfficialName() + " (" + official.getParty() + ")";

        holder.tvOfficeName.setText(officeName);
        holder.tvOfficialNameAndParty.setText(officialNameAndParty);
    }

    @Override
    public int getItemCount() {
        return officialList.size();
    }
}

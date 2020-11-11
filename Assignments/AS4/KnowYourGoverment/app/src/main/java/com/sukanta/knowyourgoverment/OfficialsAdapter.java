package com.sukanta.knowyourgoverment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        try {
            Official official = officialList.get(position);

            holder.tvOfficeName.setText(official.getOfficeName());
            holder.tvOfficialNameAndParty.setText(official.getOfficialName() + " (" + official.getParty() + ")");
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: ", e);
        }
    }

    @Override
    public int getItemCount() {
        try {
            return officialList.size();
        } catch (Exception e) {
            return 0;
        }
    }
}

package com.sukanta.knowyourgoverment;

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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

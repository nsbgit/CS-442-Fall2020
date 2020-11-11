package com.sukanta.knowyourgoverment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfficialsViewHolder  extends RecyclerView.ViewHolder {
    TextView tvOfficeName;
    TextView tvOfficialNameAndParty;

    public OfficialsViewHolder(@NonNull View itemView) {
        super(itemView);

        this.tvOfficeName = itemView.findViewById(R.id.tvOfficeName);
        this.tvOfficialNameAndParty = itemView.findViewById(R.id.tvOfficialNameAndParty);
    }
}

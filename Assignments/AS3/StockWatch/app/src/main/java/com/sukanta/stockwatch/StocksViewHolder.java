package com.sukanta.stockwatch;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class StocksViewHolder extends ViewHolder {

    public TextView companyName;
    public TextView companySymbol;
    public TextView price;
    public TextView priceChange;
    public TextView changePercentage;
    public ImageView arrow;
    public View dividerView;

    public StocksViewHolder(@NonNull View itemView) {
        super(itemView);
        companyName = itemView.findViewById(R.id.companynameTextView);
        companySymbol = itemView.findViewById(R.id.companysymbolTextView);
        price = itemView.findViewById(R.id.priceTextView);
        priceChange = itemView.findViewById(R.id.pricechangeTextView);
        changePercentage = itemView.findViewById(R.id.changepercentageTextView);
        arrow = itemView.findViewById(R.id.arrowImageView);
        dividerView = itemView.findViewById(R.id.divider);
    }
}

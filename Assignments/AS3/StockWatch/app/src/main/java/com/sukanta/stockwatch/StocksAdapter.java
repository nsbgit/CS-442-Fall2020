package com.sukanta.stockwatch;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class StocksAdapter extends RecyclerView.Adapter<StocksViewHolder> {

    private List<Stock> list;
    private MainActivity local_mainActivity;

    public StocksAdapter(List<Stock> stockList, MainActivity mainActivity){
        this.list = stockList;
        local_mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public StocksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_row,parent,false);
        view.setOnClickListener(local_mainActivity);
        view.setOnLongClickListener(local_mainActivity);
        return new StocksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StocksViewHolder holder, int position) {
        Stock stock = list.get(position);
        if (stock.getPriceChange() < 0){
            setRedColor(holder);
        }
        else {
            setGreenColor(holder);
        }
        setDetails(holder, stock);
    }

    private void setDetails(StocksViewHolder holder, Stock stock) {
        holder.companyName.setText(stock.getCompanyName());
        holder.companySymbol.setText(stock.getStockSymbol());
        holder.price.setText(String.format(Locale.US, "%.2f", stock.getPrice()));
        holder.priceChange.setText(String.format(Locale.US, "%.2f", stock.getPriceChange()));
        holder.changePercentage.setText(String.format(Locale.US, "(%.2f%%)", stock.getChangePercentage()));
    }

    private void setGreenColor(StocksViewHolder holder) {
        holder.companyName.setTextColor(Color.GREEN);
        holder.companySymbol.setTextColor(Color.GREEN);
        holder.price.setTextColor(Color.GREEN);
        holder.priceChange.setTextColor(Color.GREEN);
        holder.changePercentage.setTextColor(Color.GREEN);
        holder.dividerView.setBackgroundColor(Color.GREEN);
        holder.arrow.setImageResource(R.drawable.baseline_arrow_drop_up_black_48);
        holder.arrow.setColorFilter(Color.GREEN);
    }

    private void setRedColor(StocksViewHolder holder) {
        holder.companyName.setTextColor(Color.RED);
        holder.companySymbol.setTextColor(Color.RED);
        holder.price.setTextColor(Color.RED);
        holder.priceChange.setTextColor(Color.RED);
        holder.changePercentage.setTextColor(Color.RED);
        holder.dividerView.setBackgroundColor(Color.RED);
        holder.arrow.setImageResource(R.drawable.baseline_arrow_drop_down_black_48);
        holder.arrow.setColorFilter(Color.RED);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    
    
}

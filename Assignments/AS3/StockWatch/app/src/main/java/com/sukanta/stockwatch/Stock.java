package com.sukanta.stockwatch;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Stock implements Serializable {
    private String stockSymbol;
    private String companyName;
    private double price;
    private double priceChange;
    private double changePercentage;

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(double priceChange) {
        this.priceChange = priceChange;
    }

    public double getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(double changePercentage) {
        this.changePercentage = changePercentage;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;
        if (obj == null || obj.getClass() != getClass()) {
            result = false;
        } else {
            Stock stock = (Stock) obj;
            if (this.stockSymbol.equals(stock.stockSymbol)) {
                result = true;
            }
        }
        return result;
    }
}

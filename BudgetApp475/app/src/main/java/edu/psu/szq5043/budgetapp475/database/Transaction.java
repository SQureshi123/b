package edu.psu.szq5043.budgetapp475.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double amount;

    private String category;

    private String date;

    public String getCategory() {
        return category;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }

    // getters and setters
}


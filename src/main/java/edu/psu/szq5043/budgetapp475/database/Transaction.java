package edu.psu.szq5043.budgetapp475.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transactions")
public class Transaction {
    private String title;
    @PrimaryKey(autoGenerate = true)
    private int id;

    private double amount;

    private String category;

    private String date;

    public Transaction(String title, double amount, String category, Date date) {
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = String.valueOf(date);
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = String.valueOf(date);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }


    // getters and setters
}


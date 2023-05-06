package edu.psu.szq5043.budgetapp475.database;

import java.util.Date;

public class ExpenseTransaction extends Transaction {
    // Add any additional fields or methods specific to ExpenseTransaction here

    public ExpenseTransaction(double amount, Date date, String category) {
        super(amount, date, category);
    }
}

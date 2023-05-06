package edu.psu.szq5043.budgetapp475;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

public class OverviewActivity extends AppCompatActivity {

    private TextView incomeTextView, expensesTextView, balanceTextView;

    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // Initialize views
        incomeTextView = findViewById(R.id.incomeTextView);
        expensesTextView = findViewById(R.id.expensesTextView);

        // Initialize ViewModel
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Observe income, expenses, and balance LiveData and update UI accordingly
        transactionViewModel.getTotalIncome().observe(this, totalIncome -> {
            if (totalIncome != null) {
                incomeTextView.setText("$" + String.format("%.2f", totalIncome));
            } else {
                incomeTextView.setText("$0.00");
            }
        });

        transactionViewModel.getBalanceLiveData().observe(this, balance -> {
            if (balance != null) {
                balanceTextView.setText("$" + String.format("%.2f", balance));
            } else {
                balanceTextView.setText("$0.00");
            }
        });
    }
}
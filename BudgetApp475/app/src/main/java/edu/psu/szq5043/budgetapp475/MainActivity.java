package edu.psu.szq5043.budgetapp475;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Transaction;
import edu.psu.szq5043.budgetapp475.TransactionViewModel;
import edu.psu.szq5043.budgetapp475.database.TransactionDatabase;
import edu.psu.szq5043.budgetapp475.TransactionAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView balanceTextView;
    private Button addTransactionButton;
    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;
    private TextView mBalanceTextView;


    private TransactionViewModel transactionViewModel;

    private double currentBalance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBalanceTextView = findViewById(R.id.balance_text_view);

        balanceTextView = findViewById(R.id.balanceTextView);
        addTransactionButton = findViewById(R.id.addTransactionButton);

        transactionRecyclerView = findViewById(R.id.transactionRecyclerView);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        transactionAdapter = new TransactionAdapter(new ArrayList<>());
        transactionRecyclerView.setAdapter(transactionAdapter);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addTransactionButton.setOnClickListener(new View.OnClickListener() {

            public void onChanged(List<SurfaceControl.Transaction> transactions) {
                transactionAdapter.submitList(transactions);
                updateBalance(transactions);
            }
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTransactionActivity.class);
                startActivity(intent);
            }
        });

        transactionViewModel.getAllTransactions().observe(this, transactions -> {
            if (transactions != null && !transactions.isEmpty()) {
                List<Transaction> transactionList = (List<SurfaceControl.Transaction>) new ArrayList<Transaction>(transactions);
                transactionAdapter.setData(transactionList);
                currentBalance = calculateCurrentBalance(transactionList);
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                balanceTextView.setText("$" + decimalFormat.format(currentBalance));
            } else {
                transactionAdapter.setData(new ArrayList<>());
                balanceTextView.setText("$0.00");
            }
        });
    }
    public void updateBalance(List<SurfaceControl.Transaction> transactions) {
        double totalExpenses = TransactionViewModel.getTotalExpenses();
        double totalIncome = TransactionViewModel.getTotalIncome();
        double balance = totalIncome - totalExpenses;

        // Update the balance TextView with the new balance
        mBalanceTextView.setText(String.format(Locale.getDefault(), "%.2f", balance));
    }


    private double calculateCurrentBalance(List<SurfaceControl.Transaction> transactionList) {
        double balance = 0;
        for (SurfaceControl.Transaction transaction : transactionList) {
            if (transaction.getType() == TransactionType.EXPENSE) {
                balance -= transaction.getAmount();
            } else if (transaction.getType() == TransactionType.INCOME) {
                balance += transaction.getAmount();
            }
        }
        return balance;
    }
}

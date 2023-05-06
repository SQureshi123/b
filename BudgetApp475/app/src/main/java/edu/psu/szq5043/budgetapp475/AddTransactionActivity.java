package edu.psu.szq5043.budgetapp475;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Transaction;
import edu.psu.szq5043.budgetapp475.TransactionViewModel;
import edu.psu.szq5043.budgetapp475.database.TransactionDatabase;
import edu.psu.szq5043.budgetapp475.TransactionAdapter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText amountEditText;
    private Spinner categorySpinner;
    private Button saveButton;
    private Button cancelButton;
    private TransactionDatabase dbHelper;
    private TransactionViewModel mTransactionViewModel;
    private EditText mAmountEditText;
    private EditText mCategoryEditText;
    private EditText mDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        mTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Initialize views
        amountEditText = findViewById(R.id.amountEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        // Initialize the EditText views
        mAmountEditText = findViewById(R.id.amount_edit_text);
        mCategoryEditText = findViewById(R.id.category_edit_text);
        mDateEditText = findViewById(R.id.date_edit_text);
        // Initialize database helper
        dbHelper = new TransactionDatabase(this);

        // Set click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
            }
        });

        // Set click listener for cancel button
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Show a DatePickerDialog
                    showDatePickerDialog();
                }
            }
        });
    }
    private void showDatePickerDialog() {
        // Create a Calendar instance and set it to the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog and show it
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Set the selected date to the mDateEditText
                String dateString = (month + 1) + "/" + dayOfMonth + "/" + year;
                mDateEditText.setText(dateString);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveTransaction(double amount, String category, String date) {
        Transaction newTransaction = new Transaction(amount, category, date);
        mTransactionViewModel.insert((edu.psu.szq5043.budgetapp475.database.Transaction) newTransaction);
        updateBalance();
        Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void updateBalance() {
        float income = mTransactionViewModel.getTotalIncome();
        float expenses = mTransactionViewModel.getTotalExpenses();
        float balance = income - expenses;
        mBalanceTextView.setText(String.format(Locale.getDefault(), "%.2f", balance));
    }



}

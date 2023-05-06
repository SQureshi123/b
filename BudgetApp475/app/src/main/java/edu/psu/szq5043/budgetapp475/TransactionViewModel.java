package edu.psu.szq5043.budgetapp475;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.view.SurfaceControl;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import edu.psu.szq5043.budgetapp475.database.Transaction;
import edu.psu.szq5043.budgetapp475.TransactionViewModel;
import edu.psu.szq5043.budgetapp475.database.TransactionDatabase;
import edu.psu.szq5043.budgetapp475.TransactionAdapter;


import java.util.ArrayList;
import java.util.List;

import edu.psu.szq5043.budgetapp475.database.TransactionDao;
import edu.psu.szq5043.budgetapp475.database.TransactionDatabase;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionDao transactionDao;
    private LiveData<List<androidx.room.Transaction>> allTransactions;
    private double amount;

    public TransactionViewModel(Application application) {
        super(application);
        TransactionDatabase database = TransactionDatabase.getInstance(application);
        transactionDao = database.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
    }

    public double getTotalExpenses() {
        double totalExpenses = 0;
        for (Transaction transaction : Transactions) {
            if (transaction.getAmount() < 0) {
                totalExpenses += transaction.getAmount();
            }
        }
        return totalExpenses;
    }
    public LiveData<Double> getTotalIncome() {
        LiveData<List<Transaction>> incomeTransactions = Transformations.map(allTransactions, input -> {
            List<Transaction> incomeList = new ArrayList<>();
            for (androidx.room.Transaction t : input) {
                if (t.getAmount() > 0) {
                    incomeList.add(t);
                }
            }
            return incomeList;
        });

        return Transformations.map(incomeTransactions, input -> {
            double totalIncome = 0;
            for (Transaction t : input) {
                totalIncome += t.getAmount();
            }
            return totalIncome;
        });
    }
    public double getAmount() {
        return amount;
    }
    public void insert(Transaction transaction) {
        new InsertTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public void update(Transaction transaction) {
        new UpdateTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public void delete(Transaction transaction) {
        new DeleteTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public LiveData<List<SurfaceControl.Transaction>> getAllTransactions() {
        return allTransactions;
    }

    private static class InsertTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private InsertTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                transactionDao.insert((Transaction) transactions[0]);
            }
            return null;
        }
    }

    private static class UpdateTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private UpdateTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDao.update(transactions[0]);
            return null;
        }
    }

    private static class DeleteTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDao transactionDao;

        private DeleteTransactionAsyncTask(TransactionDao transactionDao) {
            this.transactionDao = transactionDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                transactionDao.delete((Transaction) transactions[0]);
            }
            return null;
        }
    }
}

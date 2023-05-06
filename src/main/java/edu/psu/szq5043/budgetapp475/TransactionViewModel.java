package edu.psu.szq5043.budgetapp475;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import edu.psu.szq5043.budgetapp475.database.Transaction;
import edu.psu.szq5043.budgetapp475.database.TransactionDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.psu.szq5043.budgetapp475.database.TransactionDao;

public class TransactionViewModel extends AndroidViewModel {
    private TransactionDao transactionDao;
    private static LiveData<List<androidx.room.Transaction>> allTransactions;
    private double amount;

    public TransactionViewModel(Application application) {
        super(application);
        TransactionDatabase database = TransactionDatabase.getInstance(application);
        transactionDao = database.transactionDao();
        allTransactions = transactionDao.getAllTransactions();
    }

    public static double getTotalExpenses() {
        double totalExpenses = 0;
        Transaction[] Transactions = new Transaction[0];
        for (Transaction transaction : Transactions) {
            if (transaction.getAmount() < 0) {
                totalExpenses += transaction.getAmount();
            }
        }
        return totalExpenses;
    }
    public static LiveData<Double> getTotalIncome() {
        LiveData<List<Transaction>> incomeTransactions = Transformations.map(allTransactions, TransactionViewModel::apply);

        return Transformations.map(incomeTransactions, input -> {
            double totalIncome = 0;
            for (Transaction t : input) {
                totalIncome += t.getAmount();
            }
            return totalIncome;
        });
    }

    private static List<Transaction> apply(List<androidx.room.Transaction> input) {
        List<Transaction> incomeList = new ArrayList<>();
        for (androidx.room.Transaction t : input) {
            if (t.getAmount() > 0) {
                incomeList.add((Transaction) t);
            }
        }
        return incomeList;
    }

    public void insert(Transaction transaction) {
        new InsertTransactionAsyncTask(transactionDao).execute(transaction);
    }
    public void Transaction(String name, double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void update(Transaction transaction) {
        new UpdateTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public void delete(Transaction transaction) {
        new DeleteTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public LiveData<List<androidx.room.Transaction>> getAllTransactions() {
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
                transactionDao.insert((androidx.room.Transaction) transactions[0]);
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
            transactionDao.update((androidx.room.Transaction) transactions[0]);
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
                transactionDao.delete((androidx.room.Transaction) transactions[0]);
            }
            return null;
        }
    }
    public LiveData<Double> getBalanceLiveData() {
        LiveData<Double> mBalanceLiveData = null;
        if (mBalanceLiveData == null) {
            mBalanceLiveData = new MutableLiveData<>(0.0);
        }
        return mBalanceLiveData;
    }

    public Double getCurrentBalance() {
        LiveData<Double> mBalanceLiveData = null;
        return mBalanceLiveData.getValue();
    }

}

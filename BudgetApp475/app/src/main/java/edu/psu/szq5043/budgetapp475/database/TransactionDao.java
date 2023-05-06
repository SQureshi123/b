package edu.psu.szq5043.budgetapp475.database;

import android.view.SurfaceControl;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM transactions_table")
    void deleteAllTransactions();

    @Query("SELECT * FROM transactions_table ORDER BY date DESC")
    LiveData<List<Transaction>> getAllTransactions();
}


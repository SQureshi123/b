package edu.psu.szq5043.budgetapp475.database;

import android.view.SurfaceControl;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import edu.psu.szq5043.budgetapp475.AddTransactionActivity;

@Database(entities = {SurfaceControl.Transaction.class}, version = 1)
public class TransactionDatabase extends androidx.room.RoomDatabase {
    private static TransactionDatabase INSTANCE;

    public TransactionDatabase(AddTransactionActivity addTransactionActivity) {

    }

    public TransactionDao transactionDao() {
        return null;
    }
    public static final String DATABASE_NAME = "app_database";
    public static final String TRANSACTIONS_TABLE = "transactions_table";

    public static TransactionDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TransactionDatabase.class, "transaction_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void clearAllTables() {

    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
        return null;
    }
}

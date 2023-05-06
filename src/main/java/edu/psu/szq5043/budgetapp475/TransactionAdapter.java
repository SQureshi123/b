package edu.psu.szq5043.budgetapp475;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.psu.szq5043.budgetapp475.database.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> mTransactions;

    public TransactionAdapter(List<Transaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();

    }
    private TransactionAdapter mTransactionAdapter;


    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction currentTransaction = mTransactions.get(position);
        holder.amountTextView.setText(String.valueOf(currentTransaction.getAmount()));
        holder.categoryTextView.setText(currentTransaction.getCategory());
        holder.dateTextView.setText((CharSequence) currentTransaction.getDate());
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    public void setTransactions(List<Transaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    public Transaction getTransactionAt(int position) {
        return mTransactions.get(position);
    }

    public void submitList(List<SurfaceControl.Transaction> transactions) {
        List<Transaction> newTransactions = null;
        mTransactions = null;
            notifyDataSetChanged();
        }

    public void setData(List<Transaction> transactions) {
        mTransactions = transactions;
        notifyDataSetChanged();
    }


    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView amountTextView;
        private TextView categoryTextView;
        private TextView dateTextView;

        private TransactionViewHolder(View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.text_view_amount);
            categoryTextView = itemView.findViewById(R.id.text_view_category);
            dateTextView = itemView.findViewById(R.id.text_view_date);
        }

    }
}

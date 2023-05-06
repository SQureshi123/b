package edu.psu.szq5043.budgetapp475;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TransactionViewHolder extends RecyclerView.ViewHolder {

    public TextView dateTextView;
    public TextView amountTextView;

    public TransactionViewHolder(View itemView) {
        super(itemView);

        dateTextView = itemView.findViewById(R.id.dateTextView);
        amountTextView = itemView.findViewById(R.id.amountTextView);
    }
}


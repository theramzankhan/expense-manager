package com.example.expensemanager.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.model.Expense;
import com.example.expensemanager.ui.DeleteExpenseActivity;
import com.example.expensemanager.ui.UpdateExpenseActivity;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.title.setText(expense.getTitle());
        holder.category.setText(expense.getCategory());
        holder.amount.setText("₹ " + expense.getAmount());
        holder.paymentMethod.setText(expense.getPaymentMethod());
        holder.expenseDate.setText(expense.getExpenseDate());
        holder.notes.setText(expense.getNotes());
        holder.currency.setText(expense.getCurrency());
        holder.location.setText(expense.getLocation());


        holder.buttonUpdate.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), UpdateExpenseActivity.class);
            intent.putExtra("expenseId", expense.getId());
            v.getContext().startActivity(intent);
        });

        holder.buttonDelete.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DeleteExpenseActivity.class);
            intent.putExtra("expenseId", expense.getId());
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, category, paymentMethod, expenseDate, notes, currency, location;
        View buttonUpdate, buttonDelete;

        ExpenseViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            amount = itemView.findViewById(R.id.textAmount);
            category = itemView.findViewById(R.id.textCategory);
            paymentMethod = itemView.findViewById(R.id.textPaymentMethod);
            expenseDate = itemView.findViewById(R.id.textExpenseDate);
            notes = itemView.findViewById(R.id.textNotes);
            currency = itemView.findViewById(R.id.textCurrency);
            location = itemView.findViewById(R.id.textLocation);

            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}

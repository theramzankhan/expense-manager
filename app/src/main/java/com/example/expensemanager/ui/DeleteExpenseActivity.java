package com.example.expensemanager.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.api.ApiClient;
import com.example.expensemanager.api.ExpenseApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteExpenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long expenseId = getIntent().getLongExtra("expenseId", -1);
        if (expenseId != -1)
            deleleExpense(expenseId);
        else {
            Toast.makeText(this, "Invalid expense ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void deleleExpense(long expenseId) {
        ExpenseApi api = ApiClient.getRetrofit().create(ExpenseApi.class);
        api.deleteExpense(expenseId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DeleteExpenseActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DeleteExpenseActivity.this, "Failed to delete", Toast.LENGTH_SHORT).show();
                }
                finish(); // Go back after operation
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DeleteExpenseActivity.this, "Error deleting expense", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

// old code in ExpenseAdapter
//        holder.buttonDelete.setOnClickListener(v -> {
//            ExpenseApi api = ApiClient.getRetrofit().create(ExpenseApi.class);
//            api.deleteExpense(expense.getId()).enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if (response.isSuccessful()) {
//                        expenseList.remove(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, expenseList.size());
//                        Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(v.getContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Toast.makeText(v.getContext(), "Error deleting expense", Toast.LENGTH_SHORT).show();
//                }
//            });
//        });
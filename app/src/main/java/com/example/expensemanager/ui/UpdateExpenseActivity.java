package com.example.expensemanager.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensemanager.R;
import com.example.expensemanager.api.ApiClient;
import com.example.expensemanager.api.ExpenseApi;
import com.example.expensemanager.model.Expense;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateExpenseActivity extends AppCompatActivity {

    private EditText title, amount, category, paymentMethod, expenseDate, notes, currency, location;
    private Button updateBtn;
    private long expenseId;
    private ExpenseApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense); // Reuse add layout for update

        title = findViewById(R.id.editTitle);
        amount = findViewById(R.id.editAmount);
        category = findViewById(R.id.editCategory);
        paymentMethod = findViewById(R.id.editPaymentMethod);
        expenseDate = findViewById(R.id.editExpenseDate);
        notes = findViewById(R.id.editNotes);
        currency = findViewById(R.id.editCurrency);
        location = findViewById(R.id.editLocation);
        updateBtn = findViewById(R.id.buttonSave);
        updateBtn.setText("Update"); // Change button label

        api = ApiClient.getRetrofit().create(ExpenseApi.class);
        expenseId = getIntent().getLongExtra("expenseId", -1);

        if (expenseId == -1) {
            Toast.makeText(this, "Invalid expense ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        fetchExpenseDetails(expenseId);

        updateBtn.setOnClickListener(v -> updateExpense());
    }

    private void fetchExpenseDetails(long id) {
        api.getExpenseById(id).enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if (response.isSuccessful() && response.body() != null) {
                    populateFields(response.body());
                } else {
                    Toast.makeText(UpdateExpenseActivity.this, "Failed to load expense", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable t) {
                Toast.makeText(UpdateExpenseActivity.this, "Error fetching expense", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void populateFields(Expense expense) {
        title.setText(expense.getTitle());
        amount.setText(String.valueOf(expense.getAmount()));
        expense.setCategory(expense.getCategory());
        expense.setPaymentMethod(expense.getPaymentMethod());
        expenseDate.setText(expense.getExpenseDate());
        notes.setText(expense.getNotes());
        currency.setText(expense.getCurrency());
        location.setText(expense.getLocation());
    }

    private void updateExpense() {
        Expense updatedExpense = new Expense();
        updatedExpense.setId(expenseId); // Set ID for update
        updatedExpense.setTitle(title.getText().toString());
        updatedExpense.setAmount(Double.parseDouble(amount.getText().toString()));
        updatedExpense.setCategory(category.getText().toString());
        updatedExpense.setPaymentMethod(paymentMethod.getText().toString());
        updatedExpense.setExpenseDate(expenseDate.getText().toString());
        updatedExpense.setNotes(notes.getText().toString());
        updatedExpense.setCurrency(currency.getText().toString());
        updatedExpense.setLocation(location.getText().toString());

        api.updateExpense(expenseId, updatedExpense).enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateExpenseActivity.this, "Expense updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateExpenseActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable t) {
                Toast.makeText(UpdateExpenseActivity.this, "Error updating expense", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

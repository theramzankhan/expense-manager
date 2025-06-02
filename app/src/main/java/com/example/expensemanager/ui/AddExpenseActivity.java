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

public class AddExpenseActivity extends AppCompatActivity {
    private EditText title, amount, category, paymentMethod, expenseDate, notes, currency, location;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        title = findViewById(R.id.editTitle);
        amount = findViewById(R.id.editAmount);
        category = findViewById(R.id.editCategory) ;
        paymentMethod = findViewById(R.id.editPaymentMethod);
        expenseDate = findViewById(R.id.editExpenseDate);
        notes = findViewById(R.id.editNotes);
        currency = findViewById(R.id.editCurrency);
        location = findViewById(R.id.editLocation);
        saveBtn = findViewById(R.id.buttonSave);

        saveBtn.setOnClickListener(v -> saveExpense());
    }

    private void saveExpense() {
        Expense expense = new Expense();
        expense.setTitle(title.getText().toString());
        expense.setAmount(Double.parseDouble(amount.getText().toString()));
        expense.setCategory(category.getText().toString());
        expense.setPaymentMethod(paymentMethod.getText().toString());
        expense.setExpenseDate(expenseDate.getText().toString());
        expense.setNotes(notes.getText().toString());
        expense.setCurrency(currency.getText().toString());
        expense.setLocation(location.getText().toString());

        ExpenseApi api = ApiClient.getRetrofit().create(ExpenseApi.class);
        api.createExpense(expense).enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddExpenseActivity.this, "Expense added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Expense> call, Throwable t) {
                Toast.makeText(AddExpenseActivity.this, "Error saving expense", Toast.LENGTH_SHORT).show();
            }
        });
    }

}




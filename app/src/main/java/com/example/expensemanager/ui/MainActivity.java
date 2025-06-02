package com.example.expensemanager.ui;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.adapter.ExpenseAdapter;
import com.example.expensemanager.api.ApiClient;
import com.example.expensemanager.api.ExpenseApi;
import com.example.expensemanager.model.Expense;
import com.example.expensemanager.util.CsvDownloader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExpenseAdapter expenseAdapter;
    private List<Expense> expenses;

    FloatingActionButton fabAdd, fabExport, fabShowActivity;

    private static final String PREFS_NAME = "settings";
    private static final String DARK_MODE_KEY = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load saved theme preference before calling super.onCreate
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean(DARK_MODE_KEY, false);
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        fabExport = findViewById(R.id.fabExport);
        fabShowActivity = findViewById(R.id.fabShowActivity);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenses = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(expenseAdapter);

        fetchExpenses();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
            startActivity(intent);
        });

        fabExport.setOnClickListener(v -> {
            String url = "https://expense-manager-production-f467.up.railway.app/api/expenses/export/csv"; // Replace with your actual URL
            CsvDownloader downloader = new CsvDownloader(this);
            downloader.downloadCsv(url);
        });

        fabShowActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchExpenses(); // Refresh list when returning from AddExpenseActivity
    }

    private void fetchExpenses() {
        ExpenseApi api = ApiClient.getRetrofit().create(ExpenseApi.class);
        api.getAllExpenses().enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if(response.isSuccessful()) {
                    expenses.clear();
                    expenses.addAll(response.body());
                    expenseAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error loading expenses", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Inflate the menu: add Dark Mode toggle item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // See next step for menu XML
        return true;
    }

    // Handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_toggle_theme) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            boolean isDarkMode = prefs.getBoolean(DARK_MODE_KEY, false);
            boolean newMode = !isDarkMode;

            prefs.edit().putBoolean(DARK_MODE_KEY, newMode).apply();

            AppCompatDelegate.setDefaultNightMode(
                    newMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );

            recreate(); // Restart activity to apply theme
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


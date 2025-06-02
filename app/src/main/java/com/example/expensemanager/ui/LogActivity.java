package com.example.expensemanager.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.R;
import com.example.expensemanager.adapter.ActivityLogAdapter;
import com.example.expensemanager.api.ApiClient;
import com.example.expensemanager.api.ExpenseApi;
import com.example.expensemanager.model.ActivityLog;
import com.example.expensemanager.model.Expense;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ActivityLogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        recyclerView = findViewById(R.id.activityRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchLogs();
    }

    private void fetchLogs() {
        ExpenseApi api = ApiClient.getRetrofit().create(ExpenseApi.class);
        api.getAllLogs().enqueue(new Callback<List<ActivityLog>>() {
            @Override
            public void onResponse(Call<List<ActivityLog>> call, Response<List<ActivityLog>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter = new ActivityLogAdapter(response.body());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(LogActivity.this, "Failed to load logs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ActivityLog>> call, Throwable t) {
                Toast.makeText(LogActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

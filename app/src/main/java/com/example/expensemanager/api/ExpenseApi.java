package com.example.expensemanager.api;

import com.example.expensemanager.model.ActivityLog;
import com.example.expensemanager.model.Expense;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ExpenseApi {
    @GET("api/expenses")
    Call<List<Expense>> getAllExpenses();

    @GET("api/expenses/{id}")
    Call<Expense> getExpenseById(@Path("id") Long id);

    @POST("api/expenses")
    Call<Expense> createExpense(@Body Expense expense);

    @DELETE("api/expenses/{id}")
    Call<Void> deleteExpense(@Path("id") Long id);

    @PUT("api/expenses/{id}")
    Call<Expense> updateExpense(@Path("id") Long id, @Body Expense expense);

    @GET("api/activity-logs")
    Call<List<ActivityLog>> getAllLogs();
}

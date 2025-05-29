package com.ramzankhan.expense_manager.service;

import com.ramzankhan.expense_manager.entity.Expense;
import com.ramzankhan.expense_manager.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExpenseExportService {

    private final ExpenseRepository expenseRepository;

    public ExpenseExportService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void exportExpensesToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=expenses.csv");

        List<Expense> expenses = expenseRepository.findAll();

        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,Title,Amount,Category,PaymentMethod,ExpenseDate,Notes,Currency,Location,CreatedAt,UpdatedAt");
            for (Expense expense : expenses) {
                writer.println(
                    expense.getId() + "," +
                    escapeCsv(expense.getTitle()) + "," +
                    (expense.getAmount() != null ? expense.getAmount() : "") + "," +
                    escapeCsv(expense.getCategory()) + "," +
                    escapeCsv(expense.getPaymentMethod()) + "," +
                    (expense.getExpenseDate() != null ? expense.getExpenseDate() : "") + "," +
                    escapeCsv(expense.getNotes()) + "," +
                    escapeCsv(expense.getCurrency()) + "," +
                    escapeCsv(expense.getLocation()) + "," +
                    (expense.getCreatedAt() != null ? expense.getCreatedAt() : "") + "," +
                    (expense.getUpdatedAt() != null ? expense.getUpdatedAt() : "")
                );
            }
        }
    }

    private String escapeCsv(String input) {
        if (input == null) return "";
        String escaped = input.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }
}

package com.ramzankhan.expense_manager.service;

import java.util.List;

import com.ramzankhan.expense_manager.dto.ExpenseDTO;
import com.ramzankhan.expense_manager.entity.Expense;

public interface ExpenseService {
	
	Expense createExpense(ExpenseDTO dto);
	List<Expense> getAllExpenses();
	Expense getExpenseById(Long id);
	void deleteExpense(Long id);
	
}

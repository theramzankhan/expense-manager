package com.ramzankhan.expense_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramzankhan.expense_manager.dto.ExpenseDTO;
import com.ramzankhan.expense_manager.entity.Expense;
import com.ramzankhan.expense_manager.service.ExpenseService;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;

	@PostMapping
	public ResponseEntity<Expense> createExpense(@RequestBody ExpenseDTO dto) {
		return new ResponseEntity<>(expenseService.createExpense(dto), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<Expense>> getAllExpenses() {
		return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
		return new ResponseEntity<>(expenseService.getExpenseById(id), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
		expenseService.deleteExpense(id);
		return ResponseEntity.noContent().build();
	}
}

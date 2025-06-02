package com.ramzankhan.expense_manager.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramzankhan.expense_manager.dto.ExpenseDTO;
import com.ramzankhan.expense_manager.entity.Expense;
import com.ramzankhan.expense_manager.service.ExpenseExportService;
import com.ramzankhan.expense_manager.service.ExpenseService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExpenseExportService expenseExportService;

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
	
	@PutMapping("/{id}")
	public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
		return new ResponseEntity<>(expenseService.updateExpense(id, updatedExpense), HttpStatus.OK);
	}
	
	@GetMapping("/export/csv")
	public ResponseEntity<Void> exportExpensesToCSV(HttpServletResponse response) throws IOException {
		expenseExportService.exportExpensesToCSV(response);
		return ResponseEntity.ok().build(); 
	}
}

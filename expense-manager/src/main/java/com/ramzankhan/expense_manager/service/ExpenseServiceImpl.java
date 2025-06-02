package com.ramzankhan.expense_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramzankhan.expense_manager.dto.ExpenseDTO;
import com.ramzankhan.expense_manager.entity.ActivityLog;
import com.ramzankhan.expense_manager.entity.Expense;
import com.ramzankhan.expense_manager.repository.ExpenseRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ActivityLogService activityLogService;

	@Override
	public Expense createExpense(ExpenseDTO dto) {
		// TODO Auto-generated method stub
		Expense expense = new Expense();
		BeanUtils.copyProperties(dto, expense);
		Expense saved = expenseRepository.save(expense);
		
		activityLogService.log("ADDED", "Added expense: " + saved.getTitle() + ", ₹" + saved.getAmount());
		return saved;
	}

	@Override
	public List<Expense> getAllExpenses() {
		// TODO Auto-generated method stub
		return expenseRepository.findAll();
	}

	@Override
	public Expense getExpenseById(Long id) {
		// TODO Auto-generated method stub
		return expenseRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Expense not found"));
	}

	@Override
	public void deleteExpense(Long id) {
		// TODO Auto-generated method stub
		expenseRepository.findById(id).ifPresent(expense -> {
			expenseRepository.deleteById(id);
			activityLogService.log("DELETED", "Deleted expense: " + expense.getTitle() + ", ₹" + expense.getAmount());
		});
		
	}

	@Override
	public Expense updateExpense(Long id, Expense updatedExpense) {
		// TODO Auto-generated method stub
		Optional<Expense> exisitingExpense = expenseRepository.findById(id);
		if(exisitingExpense.isPresent()) {
			updatedExpense.setId(id);
			Expense saved = expenseRepository.save(updatedExpense);
			
			activityLogService.log("UPDATED", "Updated expense: " + saved.getTitle() + ", ₹" + saved.getAmount());
			return saved;
		} else {
			return null;
		}
	}
	
}

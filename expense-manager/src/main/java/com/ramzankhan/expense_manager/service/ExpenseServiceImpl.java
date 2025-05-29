package com.ramzankhan.expense_manager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramzankhan.expense_manager.dto.ExpenseDTO;
import com.ramzankhan.expense_manager.entity.Expense;
import com.ramzankhan.expense_manager.repository.ExpenseRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;

@Service
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseRepository expenseRepository;

	@Override
	public Expense createExpense(ExpenseDTO dto) {
		// TODO Auto-generated method stub
		Expense expense = new Expense();
		BeanUtils.copyProperties(dto, expense);
		return expenseRepository.save(expense);
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
		expenseRepository.deleteById(id);
	}

	@Override
	public Expense updateExpense(Long id, Expense updatedExpense) {
		// TODO Auto-generated method stub
		Optional<Expense> exisitingExpense = expenseRepository.findById(id);
		if(exisitingExpense.isPresent()) {
			updatedExpense.setId(id);
			return expenseRepository.save(updatedExpense);
		} else {
			return null;
		}
	}
	
}

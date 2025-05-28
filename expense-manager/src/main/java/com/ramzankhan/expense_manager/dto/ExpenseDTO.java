package com.ramzankhan.expense_manager.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExpenseDTO {
	
	private String title;
    private BigDecimal amount;
    private String category;
    private String paymentMethod;
    private LocalDateTime expenseDate;
    private String notes;
    private String currency;
    private String location;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public LocalDateTime getExpenseDate() {
		return expenseDate;
	}
	public void setExpenseDate(LocalDateTime expenseDate) {
		this.expenseDate = expenseDate;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

}

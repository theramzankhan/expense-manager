package com.ramzankhan.expense_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramzankhan.expense_manager.entity.ActivityLog;
import com.ramzankhan.expense_manager.repository.ActivityLogRepository;

@Service
public class ActivityLogService {
	
	@Autowired
	private ActivityLogRepository repository;

	public void log(String action, String details) {
		ActivityLog log = new ActivityLog();
		log.setAction(action);
		log.setDetails(details);
		repository.save(log);
	}
	
	public Object getAllLogs() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	
}

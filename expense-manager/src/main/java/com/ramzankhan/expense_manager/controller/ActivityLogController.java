package com.ramzankhan.expense_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramzankhan.expense_manager.entity.ActivityLog;
import com.ramzankhan.expense_manager.service.ActivityLogService;

@RestController
@RequestMapping("/api/activity-logs")
public class ActivityLogController {
	
	@Autowired
	private ActivityLogService activityLogService;
	
	@GetMapping
	public ResponseEntity<List<ActivityLog>> getAllLogs() {
		List<ActivityLog> logs = activityLogService.getAllLogs();
		return ResponseEntity.ok(logs);
	}
}

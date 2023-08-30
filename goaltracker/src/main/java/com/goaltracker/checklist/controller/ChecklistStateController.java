package com.goaltracker.checklist.controller;

import org.springframework.http.ResponseEntity;

public interface ChecklistStateController {
    ResponseEntity<String> updateChecklistState(Long checklistStateId, boolean isCompleted);
}

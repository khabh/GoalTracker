package com.goaltracker.checklist.controller;

import com.goaltracker.checklist.service.ChecklistStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/goal-tracker/checklist-states")
public class ChecklistStateControllerImpl implements ChecklistStateController {

    private final ChecklistStateService checklistStateService;

    @Autowired
    public ChecklistStateControllerImpl(ChecklistStateService checklistStateService) {
        this.checklistStateService = checklistStateService;
    }

    @Override
    @PatchMapping("/{checklistStateId}")
    public ResponseEntity<String> updateChecklistState(@PathVariable Long checklistStateId, boolean isCompleted) {
        checklistStateService.updateChecklistState(checklistStateId, isCompleted);
        return ResponseEntity.ok("Checklist state updated successfully");
    }
}

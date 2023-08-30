package com.goaltracker.checklist.service;

import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.goal.domain.Goal;

import java.util.List;

public interface ChecklistStateService {
    void addChecklistStatesFrom(List<ChecklistHistory> checklistHistories, List<Goal> goals);
    void updateChecklistState(Long checklistStateId, boolean isCompleted);
}

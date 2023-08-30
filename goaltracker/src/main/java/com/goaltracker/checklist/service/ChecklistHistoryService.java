package com.goaltracker.checklist.service;

import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.goal.domain.Goal;

import java.util.List;

public interface ChecklistHistoryService {
    void addChecklistHistoryForGoals(List<Goal> goals);
    List<ChecklistHistoryViewDTO> getChecklistHistoriesFrom(Goal goal);
}

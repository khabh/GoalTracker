package com.goaltracker.checklist.service;

import com.goaltracker.checklist.domain.ChecklistHistory;

import java.util.List;

public interface ChecklistStateService {
    void saveChecklistStatesFromChecklistHistories(List<ChecklistHistory> checklistHistories);
}

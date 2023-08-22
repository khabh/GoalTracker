package com.goaltracker.checklist.service;

import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.repository.ChecklistHistoryRepository;
import com.goaltracker.checklist.util.ChecklistHistoryConverter;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChecklistHistoryServiceImpl implements ChecklistHistoryService {

    private final GoalService goalService;
    private final ChecklistStateService checklistStateService;
    private final ChecklistHistoryRepository checklistHistoryRepository;

    @Autowired
    public ChecklistHistoryServiceImpl(GoalService goalService, ChecklistStateService checklistStateService, ChecklistHistoryRepository checklistHistoryRepository) {
        this.goalService = goalService;
        this.checklistStateService = checklistStateService;
        this.checklistHistoryRepository = checklistHistoryRepository;
    }

    @Override
    public void addChecklistHistoryForAllGoals() {
        List<Goal> activeGoals = goalService.getGoalsWithDueDateNotPassed();
        List<ChecklistHistory> checklistHistories = ChecklistHistoryConverter.toChecklistHistories(LocalDate.now(), activeGoals.size());

        checklistHistoryRepository.saveAll(checklistHistories);
        checklistStateService.addChecklistStatesFrom(checklistHistories, activeGoals);
    }
}

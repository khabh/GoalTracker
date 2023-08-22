package com.goaltracker.checklist.service;

import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.dto.ChecklistHistoryContentStateDTO;
import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.checklist.repository.ChecklistHistoryRepository;
import com.goaltracker.checklist.util.ChecklistHistoryConverter;
import com.goaltracker.goal.domain.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChecklistHistoryServiceImpl implements ChecklistHistoryService {
    private final ChecklistStateService checklistStateService;
    private final ChecklistHistoryRepository checklistHistoryRepository;

    @Autowired
    public ChecklistHistoryServiceImpl(ChecklistStateService checklistStateService, ChecklistHistoryRepository checklistHistoryRepository) {
        this.checklistStateService = checklistStateService;
        this.checklistHistoryRepository = checklistHistoryRepository;
    }

    @Override
    public void addChecklistHistoryForGoals(List<Goal> goals) {
        List<ChecklistHistory> checklistHistories = ChecklistHistoryConverter.toChecklistHistories(LocalDate.now(), goals.size());

        checklistHistoryRepository.saveAll(checklistHistories);
        checklistStateService.addChecklistStatesFrom(checklistHistories, goals);
    }

    @Override
    public List<ChecklistHistoryViewDTO> getChecklistHistoriesFrom(Goal goal) {
        List<ChecklistHistoryContentStateDTO> contentStateDTOs = checklistHistoryRepository.findContentStateDTOsByGoal(goal);
        return ChecklistHistoryConverter.toViewDTOs(contentStateDTOs);
    }
}

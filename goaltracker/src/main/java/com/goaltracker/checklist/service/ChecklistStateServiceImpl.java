package com.goaltracker.checklist.service;

import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.repository.ChecklistStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChecklistStateServiceImpl implements ChecklistStateService {

    private final ChecklistStateRepository checklistStateRepository;

    @Autowired
    public ChecklistStateServiceImpl(ChecklistStateRepository checklistStateRepository) {
        this.checklistStateRepository = checklistStateRepository;
    }

    @Override
    public void saveChecklistStatesFromChecklistHistories(List<ChecklistHistory> checklistHistories) {
        List<ChecklistState> checklistStates = new ArrayList<>();

        checklistHistories.stream()
                .map(ChecklistHistory::getChecklistStates)
                .forEach(checklistStates::addAll);

        checklistStateRepository.saveAll(checklistStates);
    }
}

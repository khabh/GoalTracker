package com.goaltracker.checklist.service;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.domain.ChecklistHistory;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.repository.ChecklistStateRepository;
import com.goaltracker.checklist.util.ChecklistStateConverter;
import com.goaltracker.goal.domain.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ChecklistStateServiceImpl implements ChecklistStateService {

    private final ChecklistStateRepository checklistStateRepository;

    @Autowired
    public ChecklistStateServiceImpl(ChecklistStateRepository checklistStateRepository) {
        this.checklistStateRepository = checklistStateRepository;
    }

    @Override
    public void addChecklistStatesFrom(List<ChecklistHistory> checklistHistories, List<Goal> goals) {
        List<ChecklistState> checklistStates = IntStream.range(0, goals.size())
                .mapToObj(index -> {
                    List<Checklist> checklists = goals.get(index).getChecklists();
                    ChecklistHistory history = checklistHistories.get(index);
                    return ChecklistStateConverter.toChecklistStates(checklists, history);
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());

        checklistStateRepository.saveAll(checklistStates);
    }
}

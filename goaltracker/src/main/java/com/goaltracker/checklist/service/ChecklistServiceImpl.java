package com.goaltracker.checklist.service;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.checklist.dto.PopularCompletedChecklistDTO;
import com.goaltracker.checklist.repository.ChecklistRepository;
import com.goaltracker.checklist.util.ChecklistConverter;
import com.goaltracker.goal.domain.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository checklistRepository;

    @Autowired
    public ChecklistServiceImpl(ChecklistRepository checklistRepository) {
        this.checklistRepository = checklistRepository;
    }

    @Override
    public List<ChecklistState> getDailyChecklistStatesToActiveGoals() {
        return checklistRepository.findCurrentDateChecklistStatesByGoalDueDate();
    }

    @Override
    public void createChecklists(CreateChecklistsDTO createChecklistsDTO, Goal goal) {
        List<Checklist> checklists = ChecklistConverter.toChecklists(createChecklistsDTO, goal);
        checklistRepository.saveAll(checklists);
    }

    @Override
    public List<PopularCompletedChecklistDTO> getPopularCompletedChecklists(Goal goal) {
        return checklistRepository.findPopularCompletedChecklists(goal);
    }
}

package com.goaltracker.goal.service;

import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.checklist.service.ChecklistService;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.repository.GoalRepository;
import com.goaltracker.goal.util.GoalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final ChecklistService checklistService;

    @Autowired
    public GoalService(GoalRepository goalRepository, ChecklistService checklistService) {
        this.goalRepository = goalRepository;
        this.checklistService = checklistService;
    }

    public void createGoal(CreateGoalDTO createGoalDTO, CreateChecklistsDTO createChecklistsDTO) {
        Goal goal = GoalConverter.convertToEntity(createGoalDTO);
        goalRepository.save(goal);
        checklistService.createChecklists(createChecklistsDTO, goal);
    }
}

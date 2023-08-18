package com.goaltracker.goal.service;

import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.repository.ChecklistRepository;
import com.goaltracker.goal.repository.GoalRepository;
import com.goaltracker.goal.util.GoalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final ChecklistRepository checklistRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository, ChecklistRepository checklistRepository) {
        this.goalRepository = goalRepository;
        this.checklistRepository = checklistRepository;
    }

    public void createGoal(CreateGoalDTO createGoalDTO) {
        Goal goal = GoalConverter.convertToEntity(createGoalDTO);
        goalRepository.save(goal);
        checklistRepository.saveAll(goal.getChecklists());
    }
}

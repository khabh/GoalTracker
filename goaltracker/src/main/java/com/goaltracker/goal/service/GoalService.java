package com.goaltracker.goal.service;

import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.repository.GoalRepository;
import com.goaltracker.goal.util.GoalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public void createGoal(CreateGoalDTO createGoalDTO) {
        Goal goal = GoalConverter.convertToEntity(createGoalDTO);
        goalRepository.save(goal);
    }
}

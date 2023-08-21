package com.goaltracker.goal.service;

import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.CreateGoalDTO;

import java.util.List;

public interface GoalService {
    void createGoal(CreateGoalDTO createGoalDTO, CreateChecklistsDTO createChecklistsDTO);
    List<Goal> getGoalsWithDueDateNotPassed();
}

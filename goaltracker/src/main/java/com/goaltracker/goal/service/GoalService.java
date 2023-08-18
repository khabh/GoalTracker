package com.goaltracker.goal.service;

import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.goal.dto.CreateGoalDTO;

public interface GoalService {
    public void createGoal(CreateGoalDTO createGoalDTO, CreateChecklistsDTO createChecklistsDTO);
}

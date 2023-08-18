package com.goaltracker.goal.controller;

import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;

public interface GoalController {

    public String showCreateGoalForm();
    public String createGoal(CreateGoalDTO createGoalDTO, CreateChecklistsDTO createChecklistsDTO);
}

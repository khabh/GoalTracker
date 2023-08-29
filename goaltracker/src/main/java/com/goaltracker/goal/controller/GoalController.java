package com.goaltracker.goal.controller;

import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import org.springframework.ui.Model;

public interface GoalController {

    String showCreateGoalForm();
    String createGoal(CreateGoalDTO createGoalDTO, CreateChecklistsDTO createChecklistsDTO);
    String showGoalPerformance(Long goalId, Model model);
    String showActiveGoals(Long userId, Model model);
}

package com.goaltracker.goal.controller;

import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/goal-tracker/goals")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/create")
    public String showCreateGoalForm() {
        return "/goaltracker/createGoalForm.html";
    }

    @PostMapping
    public String createGoal(@ModelAttribute CreateGoalDTO createGoalDTO) {
        goalService.createGoal(createGoalDTO);
        return "redirect:/goal-tracker/goals/create";
    }


}

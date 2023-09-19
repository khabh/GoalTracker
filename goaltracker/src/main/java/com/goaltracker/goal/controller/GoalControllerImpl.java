package com.goaltracker.goal.controller;

import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.goal.dto.CreateGoalDTO;
import com.goaltracker.goal.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/goal-tracker/goals")
public class GoalControllerImpl implements GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalControllerImpl(GoalService goalService) {
        this.goalService = goalService;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('USER', 'ROLE_USER')")
    @GetMapping("/create")
    public String showCreateGoalForm() {
        return "goalTracker/createGoalForm";
    }

    @Override
    @PostMapping
    public String createGoal(@Valid CreateGoalDTO createGoalDTO, @Valid CreateChecklistsDTO createChecklistsDTO) {
        goalService.createGoal(createGoalDTO, createChecklistsDTO);
        return "redirect:/goal-tracker/goals/create";
    }

    @Override
    @GetMapping("/{goalId}/performance")
    public String showGoalPerformance(@PathVariable Long goalId, Model model) {
        model.addAttribute("goalPerformance", goalService.getGoalPerformance(goalId));
        return "goalTracker/goalPerformance";
    }
}

package com.goaltracker.goal.util;

import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.CreateGoalDTO;

public class GoalConverter {
    private GoalConverter() {} // 인스턴스화 방지

    public static Goal convertToEntity(CreateGoalDTO createGoalDTO) {
        Goal goal = new Goal();
        goal.setName(createGoalDTO.getName());
        goal.setDueDate(createGoalDTO.getDueDate());
        goal.setDescription(createGoalDTO.getDescription());
        goal.setChecklists(createGoalDTO.getChecklists(goal));

        return goal;
    }
}

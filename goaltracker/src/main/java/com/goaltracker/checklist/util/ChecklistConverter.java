package com.goaltracker.checklist.util;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.goal.domain.Goal;

import java.util.List;
import java.util.stream.Collectors;

public class ChecklistConverter {

    private ChecklistConverter() {}

    public static Checklist from(String content, Goal goal) {
        Checklist checklist = new Checklist();
        checklist.setContent(content);
        checklist.setGoal(goal);

        return checklist;
    }

    public static List<Checklist> toChecklists(CreateChecklistsDTO checklistsDTO, Goal goal) {
        return checklistsDTO.getChecklists()
                .stream()
                .filter(checklist -> checklist != null && !checklist.trim().isEmpty())
                .map(content -> ChecklistConverter.from(content, goal))
                .collect(Collectors.toList());
    }
}

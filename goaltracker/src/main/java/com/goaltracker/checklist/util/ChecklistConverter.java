package com.goaltracker.checklist.util;

import com.goaltracker.checklist.domain.Checklist;
import com.goaltracker.checklist.dto.CreateChecklistsDTO;
import com.goaltracker.goal.domain.Goal;

import java.util.List;

public class ChecklistConverter {

    private ChecklistConverter() {}

    public static List<Checklist> convertToEntities(CreateChecklistsDTO checklistsDTO, Goal goal) {
        return checklistsDTO.getChecklists()
                .stream()
                .filter(checklist -> checklist != null && !checklist.trim().isEmpty())
                .map(content -> Checklist.from(content, goal))
                .toList();
    }
}

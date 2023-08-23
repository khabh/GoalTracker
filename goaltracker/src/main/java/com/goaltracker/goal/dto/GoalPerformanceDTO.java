package com.goaltracker.goal.dto;

import com.goaltracker.checklist.dto.ChecklistHistoryViewDTO;
import com.goaltracker.checklist.dto.PopularCompletedChecklistDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder()
@Getter
public class GoalPerformanceDTO {
    private Long goalId;
    private String name;
    private String description;
    private float progress;

    List<ChecklistHistoryViewDTO> checklistHistories;
    List<PopularCompletedChecklistDTO> popularCompletedChecklists;
}

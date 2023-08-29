package com.goaltracker.goal.dto;

import com.goaltracker.checklist.dto.vo.ChecklistStateVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
public class ActiveGoalDTO {
    private Long id;
    private String name;
    private LocalDate dueDate;
    private float progress;
    private List<ChecklistStateVO> checklistStates;
}

package com.goaltracker.goal.util;

import com.goaltracker.checklist.domain.ChecklistState;
import com.goaltracker.checklist.dto.vo.ChecklistStateVO;
import com.goaltracker.goal.domain.Goal;
import com.goaltracker.goal.dto.ActiveGoalDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GoalConverter {

    private GoalConverter() {} // 인스턴스화 방지

    public static ActiveGoalDTO toActiveGoalDTO(Goal goal, List<ChecklistState> checklistStates) {
        List<ChecklistStateVO> checklistStateVOs = checklistStates.stream()
                .map(checklistState -> {
                    String content = checklistState.getChecklist().getContent();
                    return new ChecklistStateVO(checklistState.getId(), checklistState.isCompleted(), content);
                })
                .collect(Collectors.toList());

        return ActiveGoalDTO.builder()
                .id(goal.getId())
                .name(goal.getName())
                .progress(goal.getProgress())
                .dueDate(goal.getDueDate())
                .checklistStates(checklistStateVOs)
                .build();
    }
}

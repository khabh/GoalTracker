package com.goaltracker.checklist.dto;

import com.goaltracker.checklist.dto.vo.ChecklistStateVO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ChecklistHistoryViewDTO {
    private LocalDate date;
    private List<ChecklistStateVO> checklistStates;
}

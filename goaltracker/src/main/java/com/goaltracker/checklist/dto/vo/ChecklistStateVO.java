package com.goaltracker.checklist.dto.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChecklistStateVO {
    private Long id;
    private boolean isCompleted;
    private String content;
}

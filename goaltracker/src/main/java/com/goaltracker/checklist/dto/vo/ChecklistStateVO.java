package com.goaltracker.checklist.dto.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ChecklistStateVO {
    private Long id;
    private boolean isCompleted;
    private String content;

    public ChecklistStateVO(Long id, boolean isCompleted, String content) {
        this.id = id;
        this.isCompleted = isCompleted;
        this.content = content;
    }
}

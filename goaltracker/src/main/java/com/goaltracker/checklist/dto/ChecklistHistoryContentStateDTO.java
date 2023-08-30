package com.goaltracker.checklist.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ChecklistHistoryContentStateDTO {
    private final LocalDate date;
    private final Long id;
    private final boolean isCompleted;
    private final String content;

    public ChecklistHistoryContentStateDTO(LocalDate date, Long stateId, boolean isCompleted, String content) {
        this.date = date;
        this.id = stateId;
        this.isCompleted = isCompleted;
        this.content = content;
    }
}

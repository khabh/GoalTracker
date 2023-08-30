package com.goaltracker.checklist.dto;

import lombok.Getter;

@Getter
public class PopularCompletedChecklistDTO {
    private final String content;
    private final Long count;

    public PopularCompletedChecklistDTO(String content, Long count) {
        this.content = content;
        this.count = count;
    }
}
